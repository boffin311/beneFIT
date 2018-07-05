package tech.iosd.benefit.VideoPlayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import tech.iosd.benefit.Model.VideoPlayerItem;
import tech.iosd.benefit.R;

/**
 * Created by Prerak Mann on 28/06/18.
 */
public class VideoPlayerActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl, MediaPlayer.OnCompletionListener, TextToSpeech.OnInitListener {

    SurfaceView videoSurface;
    MediaPlayer player;
    VideoControllerView controller;
    TextView dura2, setsCounter, middleCount, restCounter, repsCounter;
    int screenTime;
    CountDownTimer countDownTimer;
    public static final String TAG = "chla";
    ProgressDialog progressDialog;
    Button skipIntroBtn, skipRestBtn;
    private TextToSpeech tts;
    CheckBox soundOn;
    Boolean isSoundOn = true;


    View restView;//for hiding layouts at different points
    private VideoPlayerItem videoItem;//has all details of items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        //adding dummy data to videoplayeritem fot testing.........
//        videoItem = new VideoPlayerItem();
//        videoItem.setType(VideoPlayerItem.TYPE_FOLLOW);
//        videoItem.setSets(4);
//        videoItem.setVideoName("StackPushUp Repetitive");
//        videoItem.setRestTimeSec(10);
//        videoItem.setTotalReps(5);
//        videoItem.setIntroVideo("android.resource://");
//        videoItem.setSingleRepVideo("android.resource://");
//        videoItem.setCurrentRep(0);
//        videoItem.setCurrentSet(0);

        String videoItemString = getIntent().getStringExtra("videoItem");
        if(videoItemString==null|| videoItemString.equals("")) {
            finish();
            return;
        }
        Gson gson = new Gson();
        videoItem = gson.fromJson(videoItemString,VideoPlayerItem.class);



        restView = (View) findViewById(R.id.restView);

        restCounter = (TextView) findViewById(R.id.restCounter);
        repsCounter = (TextView) findViewById(R.id.repsTextView);
        skipRestBtn = (Button) findViewById(R.id.skipRest);
        skipRestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countDownTimer!=null)
                    countDownTimer.cancel();//stopping rest counter
                videoItem.setResting(false);
                startNext();
            }
        });

        dura2 = findViewById(R.id.duration2);
        skipIntroBtn = findViewById(R.id.btn_skip_intro);
        skipIntroBtn.setOnClickListener(skipIntroListner);
        setsCounter = findViewById(R.id.no_of_sets);
        middleCount = findViewById(R.id.countInBetweenScreen);
        tts = new TextToSpeech(this, this);
        soundOn = findViewById(R.id.muteCheckBox);

        videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
        SurfaceHolder videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);

        soundOn.setChecked(isSoundOn);
        soundOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    player.setVolume(1, 1);
                    isSoundOn = true;
                } else {
                    player.setVolume(0, 0);
                    isSoundOn = false;
                }
            }
        });

        controller = new VideoControllerView(this);

        //player is initialised after surface is created (onSurfaceCreated method)

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hideNavAndStatus();
    }

    private void hideNavAndStatus() {
        //hides navigationbar and statusbar
        videoSurface.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void showRestScreen() {
        //cleaning up
        if (player != null) {
            player.stop();
        }
        NumberFormat f = new DecimalFormat("00");
        //hide other layouts
        controller.hide();
        controller.setEnabled(false); //controller disabled so that it doesn't show on touch
        hideAllViews();
        //show rest screen
        restView.setVisibility(View.VISIBLE);
        videoItem.setResting(true);
        //set timer to stop resting
        if(countDownTimer!=null)
            countDownTimer.cancel();
        countDownTimer = new CountDownTimer(videoItem.getRestTimeSec()*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int sec = (int)millisUntilFinished/1000;
                int min = (int)millisUntilFinished/60000;
                YoYo.with(Techniques.ZoomIn).duration(800).playOn(restCounter);
                restCounter.setText(f.format(min) + ":" + f.format(sec));
            }

            @Override
            public void onFinish() {//skip button
                videoItem.setResting(false);
                startNext();
            }
        }.start();
    }

    private void hideAllViews() {
        //introviews
        skipIntroBtn.setVisibility(View.GONE);
        //allTimeViews
        soundOn.setVisibility(View.GONE);
        dura2.setVisibility(View.GONE);
        setsCounter.setVisibility(View.GONE);
        //repsview
        middleCount.setVisibility(View.GONE);
        repsCounter.setVisibility(View.GONE);
        //restview
        restView.setVisibility(View.GONE);
    }

    private void showAllTimeViews(){
        soundOn.setVisibility(View.VISIBLE);
        dura2.setVisibility(View.VISIBLE);
    }
    private void showRepsViews(){
        middleCount.setVisibility(View.VISIBLE);
        repsCounter.setVisibility(View.VISIBLE);
        setsCounter.setVisibility(View.VISIBLE);
    }
    private void showIntroViews(){

        skipIntroBtn.setVisibility(View.VISIBLE);
    }
    private void showTutorialView(){

        setsCounter.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!videoItem.getResting()) {//touch controls only if user is not on rest screen
            controller.show();
            player.pause();
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }

            hideAllViews();
        }
        return false;
    }

    // Implement SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.w("pkmn", "surface created");
        player = new MediaPlayer();
        player.setDisplay(holder);
        startNext();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
    // End SurfaceHolder.Callback

    // Implement MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
        //show relevant views here
        hideAllViews();
        showAllTimeViews();

        NumberFormat f = new DecimalFormat("00");

        controller.setMediaPlayer(this);
        controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer), videoItem.getSets(), videoItem.getVideoName());
        player.start();
        if (progressDialog != null)
            progressDialog.dismiss();
        Log.d(TAG, "onPrepared: " + getDuration());
        int duration = getDuration();

        if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
            showTutorialView();
            duration = duration * videoItem.getSetsRemaining();
            setsCounter.setText("Sets: " + videoItem.getCurrentSet() + "/" + videoItem.getSets());
        } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
            if (videoItem.getIntroComp()) {//if it not intro-video
                duration = duration * videoItem.getTotalReps();
                showRepsViews();
                setsCounter.setText("Sets: " + videoItem.getCurrentSet() + "/" + videoItem.getSets());
            } else {
                showIntroViews();
            }
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(duration, 1000) {//geriye sayma

            public void onTick(long millisUntilFinished) {
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;

                if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
                    dura2.setText("Total Time Remaining : \n" + f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
                    if (videoItem.getIntroComp()) {//if intro is completed
                        repsCounter.setText("Reps: " + String.valueOf(videoItem.getCurrentRep()) + "/" + String.valueOf(videoItem.getTotalReps()));
                        dura2.setText("Total Time Remaining : \n" + f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    } else {//for intro video
                        dura2.setText(videoItem.getVideoName() + "\n" + "Starts in " + f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    }
                }
            }

            public void onFinish() {//keep empty
            }

        }.start();
    }
// End MediaPlayer.OnPreparedListener


    // Implement VideoMediaController.MediaPlayerControl
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void seekTo(int i) {
        player.seekTo(i);
    }

    @Override
    public boolean isFullScreen() {
        return true;
    }

    @Override
    public void toggleFullScreen() {
    }

    @Override
    public void pause() {
        hideAllViews();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        player.pause();
    }

    @Override
    public void start() {//resuming player here
        showAllTimeViews();

        if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
            if (!videoItem.getIntroComp()) {
                showTutorialView();
            }
        } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
            if (videoItem.getIntroComp()) {
                showRepsViews();
            } else {
                showIntroViews();
            }
        }

        NumberFormat f = new DecimalFormat("00");
        player.start();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(screenTime, 1000) {//resuming from screenTime                     //geriye sayma

            public void onTick(long millisUntilFinished) {
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;

                if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
                    dura2.setText("Total Time Remaining : \n" + f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
                    if (videoItem.getIntroComp()) {//if intro is completed
                        repsCounter.setText(String.valueOf(videoItem.getCurrentRep()) + "/" + String.valueOf(videoItem.getTotalReps()));
                        dura2.setText("Total Time Remaining : \n" + f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    } else {//for intro video
                        dura2.setText(videoItem.getVideoName() + "\n" + "Starts in " + f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
                    }
                }
            }

            public void onFinish() {//keep empty
            }

        }.start();
    }

    @Override
    public void setOnScreenTime(int time) {


        if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
            int tempTotalDuration = getDuration();
            int ku = tempTotalDuration - time;
            screenTime = tempTotalDuration * videoItem.getSetsRemaining() - ku + 1000;
        } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
            int tempTotalDuration = getDuration();
            int ku = tempTotalDuration - time;
            screenTime = tempTotalDuration * videoItem.getRepsRemaining() - ku + 1000;
        }

    }

    @Override
    public void nextVideo() {
        if (player != null) {
            if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
                Toast.makeText(VideoPlayerActivity.this, "Nothing to go forward to", Toast.LENGTH_SHORT).show();
            } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
                if (videoItem.getIntroComp()) {//if intro is completed
                    Toast.makeText(VideoPlayerActivity.this, "Nothing to go forward to", Toast.LENGTH_SHORT).show();
                } else {//if intro is not complete
                    hideAllViews();
                    videoItem.setIntroComp(true);
                    startNext();
                }
            }
        }
    }

    @Override
    public void prevVideo() {
        if (player != null) {
            if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
                Toast.makeText(VideoPlayerActivity.this, "Nothing to go back to", Toast.LENGTH_SHORT).show();
            } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
                if (videoItem.getIntroComp()) {//if intro is completed go back to intro video
                    hideAllViews();
                    showIntroViews();
                    videoItem.setIntroComp(false);
                    startNext();
                } else {
                    Toast.makeText(VideoPlayerActivity.this, "Nothing to go back to", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void Stop() {
        finish();
    }

    public void startNext() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Loading ... ");

            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    hideNavAndStatus();
                }
            });
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setOnPreparedListener(this);
            player.setOnCompletionListener(this);

        } catch (IllegalArgumentException | SecurityException | IllegalStateException e) {
            e.printStackTrace();
        }

        player.reset();//so that we can re-initialise player

        if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
            if (videoItem.getIntroComp()) {//show rest screen
                showRestScreen();
            } else {//show intro
                try {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.stackpushupsingle);//dummy for now
                    player.setDataSource(this, video);
                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
            if (videoItem.getIntroComp()) {//start single * reps mode here
                try {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.stackpushupsingle);//dummy for now
                    player.setDataSource(this, video);
                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//show intro
                try {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.stackpushupsingle);//dummy for now
                    player.setDataSource(this, video);
                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //mediaplayer implement methods
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (videoItem.getType() == VideoPlayerItem.TYPE_FOLLOW) {
            if (videoItem.incrementCurrentSet() < videoItem.getSets()) {
                //show rest screen
                showRestScreen();
            } else {
                //finish
                finish();
                Toast.makeText(VideoPlayerActivity.this, "End of sets", Toast.LENGTH_SHORT).show();
            }
        } else if (videoItem.getType() == VideoPlayerItem.TYPE_REPETITIVE) {
            if (videoItem.getIntroComp()) {//if intro is complete
                if (videoItem.incrementCurrentRep() < videoItem.getTotalReps()) {
                    player.seekTo(0);
                    player.start();

                    middleCount.setVisibility(View.VISIBLE);
                    middleCount.setText(String.valueOf(videoItem.getCurrentRep()));
                    if (isSoundOn) {
                        tts.speak(String.valueOf(videoItem.getCurrentRep()), TextToSpeech.QUEUE_FLUSH, null);
                    }
                    //zoom in , fadeout and then remove
                    YoYo.with(Techniques.ZoomIn).duration(1000).playOn(middleCount);
                    YoYo.with(Techniques.FadeOut).duration(1000).delay(1000).playOn(middleCount);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            middleCount.setVisibility(View.INVISIBLE);
                        }
                    }, 2000);
                } else {
                    tts.speak(String.valueOf(videoItem.getCurrentRep()), TextToSpeech.QUEUE_FLUSH, null);
                    if (videoItem.incrementCurrentSet() < videoItem.getSets()) {
                        showRestScreen();
                    } else {
                        finish();
                        Toast.makeText(VideoPlayerActivity.this, "sets finished", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {//for intro video
                videoItem.setIntroComp(true);
                startNext();
            }
        }
    }

    private View.OnClickListener skipIntroListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideAllViews();
            videoItem.setIntroComp(true);
            startNext();
        }
    };

    //text to speech listener
    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}



// End VideoMediaController.MediaPlayerControl


