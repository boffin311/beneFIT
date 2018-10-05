package tech.iosd.benefit.DashboardFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import retrofit2.adapter.rxjava.HttpException;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.Adapters.DashboardWorkoutAdapter;
import tech.iosd.benefit.Model.DatabaseHandler;
import tech.iosd.benefit.Model.Exercise;
import tech.iosd.benefit.Model.ResponseForGetExcerciseVideoUrl;
import tech.iosd.benefit.Model.ResponseWorkoutFree;
import tech.iosd.benefit.Model.VideoPlayerItem;
import tech.iosd.benefit.Network.NetworkUtil;
import tech.iosd.benefit.R;
import tech.iosd.benefit.VideoPlayer.VideoPlayerActivity;

public class FreeWorkoutTraining extends Fragment implements DashboardWorkoutAdapter.onItemClickListener
{
    Context ctx;
    FragmentManager fm;
    private ProgressDialog progressDialog;
    private CompositeSubscription compositeSubscription, mcompositeSubscription;
    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private DashboardWorkoutAdapter adapter;
    private ArrayList<Exercise> exercises = new ArrayList<>();

    private ArrayList<Boolean>  secondPresent = new ArrayList<>();

    private String type;
    ProgressBar progressBar;
    private Button startWorkout;
    private ThinDownloadManager downloadManager;
    private int currentPosition =0;
    TextView description_free_workouts;
    private  AlertDialog.Builder mBuilder;
    private AlertDialog downloadDialog;
    private View mView;
    private TextView progressTV;
    private TextView numberOfCurrentVideo;
    private int noOfDiffId =0;
    private int noOfCurrentVideUser=0;
    boolean allVideoDownloaded = true;
    private int position;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            position = getArguments().getInt("POSITION");
        } else {
            Toast.makeText(getActivity(), "arguments is null " , Toast.LENGTH_LONG).show();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.free_workout_training, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();
        progressDialog =  new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("working..");

        type = "tutorial";

        compositeSubscription = new CompositeSubscription();
        mcompositeSubscription =  new CompositeSubscription();

        downloadManager =  new ThinDownloadManager();

        db = new DatabaseHandler(getContext());
        Log.d("token1",db.getUserToken());
        recyclerView =  rootView.findViewById(R.id.free_workout_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new DashboardWorkoutAdapter(exercises,getActivity(),this);

        mBuilder = new AlertDialog.Builder(getActivity());
        mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_download, null);
        exercises = new ArrayList<>();
        progressTV =  mView.findViewById(R.id.percentage_tv);
        progressBar = mView.findViewById(R.id.main_progressbar);
        numberOfCurrentVideo = mView.findViewById(R.id.currentfileDownload);
        mBuilder.setView(mView);
        downloadDialog = mBuilder.create();
        description_free_workouts=rootView.findViewById(R.id.free_workout_description);
        startWorkout = rootView.findViewById(R.id.dashboard_free_workouts_start_workout);
        startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadDialog.show();
                // downloadDialog.setCancelable(false);
                downloadFiles();
            }
        });
        getWorkoutData();
        return rootView;
    }

    private int getNumberOfDifferntId(){
        ArrayList <String> stringForCheck =  new ArrayList<>();
        int value =0;
        for (int i = 0; i<exercises.size();i++){
            String id = exercises.get(i).getExercise().get_id();
            value++;
            for(int j =0;j<stringForCheck.size();j++){
                if(stringForCheck.size() == 0){
                    stringForCheck.add(id);
                }else if(stringForCheck.get(j).equals(id)){
                    break;
                }else if(j== stringForCheck.size()-1){
                    stringForCheck.add(id);
                }
            }
        }

        return stringForCheck.size();
    }
    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_LONG).show();

        }
    }
    private void getWorkoutData(){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }

        Log.d("token",db.getUserToken());
        compositeSubscription.add(NetworkUtil.getRetrofit(db.getUserToken()).getWorkoutFree(db.getUserToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetMeal,this::handleErrorGetMeal));
    }
    int videoCount;
    private void handleResponseGetMeal(ResponseWorkoutFree responseWorkoutFree)
    {
        progressDialog.hide();
        if (!responseWorkoutFree.isSuccess())
        {
            adapter.notifyDataSetChanged();
            return;
            //Download completes here
        }
        videoCount = responseWorkoutFree.getData().get(position).getVideoCount();
        description_free_workouts.setText(responseWorkoutFree.getData().get(position).getDescription());
        Log.d("error77"," " +responseWorkoutFree.getData().get(position).getExercises().size());
        exercises = responseWorkoutFree.getData().get(position).getExercises();
        for (int i =0 ; i<exercises.size();i++){

        }
        adapter.setExercises(exercises);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        noOfDiffId = getNumberOfDifferntId();
        checkFiles();
    }

    private void handleErrorGetMeal(Throwable error) {
        progressDialog.hide();
//        pbar.setVisibility(View.GONE);
        Log.d("error77",error.getMessage());


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
//                Response response = gson.fromJson(errorBody,Response.class);
                //showSnackBarMessage(response.getMessage());
                // Log.d("error77",error.getMessage());

                fm.popBackStack();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Log.d("error77",error.getMessage());

            // showSnackBarMessage("Network Error !");
        }
    }

    private void getExcercise(String url,String type){
        mcompositeSubscription.add(NetworkUtil.getRetrofit(db.getUserToken()).getExerciseVideoUrl(url,db.getUserToken(),type)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseSendMealLog,this::handleError));
    }

    private void handleResponseSendMealLog(ResponseForGetExcerciseVideoUrl reponse)
    {
        String url = reponse.getData();
        Toast.makeText(getActivity().getApplicationContext(),"url fetch success",Toast.LENGTH_SHORT).show();
        getVideo(url);
    }
    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();
            //showSnackBarMessage("Network Error !");
            Log.d("error77",error.getMessage());

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                /*Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());*/

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("error77",error.getMessage());

            //showSnackBarMessage("Network Error !");
        }
    }
    private void downloadFiles() {
        Log.d("download","type: "+ type+"position: "+currentPosition);
        if(exercises.size()==0 || exercises==null)
        {
            downloadDialog.dismiss();
            Toast.makeText(ctx, "Workout Not Available", Toast.LENGTH_SHORT).show();
            return;
        }
        if (currentPosition>=exercises.size() && exercises.size()!=0){
            //  downloadDialog.hide();
            if(allVideoDownloaded){
                Toast.makeText(ctx, "First", Toast.LENGTH_SHORT).show();
                startWorkout.setText("START WORKOUT");
                startWorkout.setOnClickListener(startClickListener);

            }else {
                showSnackBarMessage("All files not downloaded.\nPlease try again.");
            }
            return;
        }
        else if(exercises.size()==0)
        {
            Toast.makeText(ctx, "Workout Videos Not Available", Toast.LENGTH_SHORT).show();
            downloadDialog.dismiss();
            return;
        }
        File file = null ;
        if (type.equals("a")){
            if (exercises.get(currentPosition).getExercise().isVideoA()){
                file = new File(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+"_a.mp4");
            }else {
                type = "b";
                downloadFiles();
                return;
            }

        }else if (type.equals("b")){
            if (exercises.get(currentPosition).getExercise().isVideoB()){
                file = new File(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+"_b.mp4");
            }else {
                type = "tutorial";
                currentPosition++;
                downloadFiles();
                return;
            }

        }else{
            file = new File(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+".mp4");

        }
        if(file.exists()){
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(),"file arleady presenet "+type+(currentPosition+1),Toast.LENGTH_SHORT).show();
            Log.d("files",getActivity().getFilesDir().toString()+"/videos/");
            if (type.equals("tutorial")){
                type = "a";
                downloadFiles();

            }else if (type.equals("a")){
                type="b";
                downloadFiles();


            }else if (type.equals("b"))
            {
                exercises.get(currentPosition).getExercise().isDownloaded=true;
                adapter.notifyItemChanged(currentPosition);
                type="tutorial";
                currentPosition++;
                downloadFiles();
            }
            //currentPosition++;
           /* if(currentPosition<exercises.size()){
                getExcercise(exercises.get(currentPosition).getExercise().get_id());
            }*/
        }
        else{
            if (type.equals("tutorial")){
                getExcercise(exercises.get(currentPosition).getExercise().get_id(),type);

                // type = "a";
            }else if (type.equals("a")){
                if(exercises.get(currentPosition).getExercise().isVideoA()){
                    getExcercise(exercises.get(currentPosition).getExercise().get_id(),type);
                }
                // type="b";

            }else if (type.equals("b")){
                if(exercises.get(currentPosition).getExercise().isVideoB()){
                    getExcercise(exercises.get(currentPosition).getExercise().get_id(),type);
                }
                // type="tutorial";


            }
            // getExcercise(exercises.get(currentPosition).getExercise().get_id(),type);
        }
    }

    private void checkFiles(){
        Boolean comp = true;
        for(Exercise e:exercises) {
            Boolean exComp = true;
            //get all exercise names
            File file ,filea ,fileb;
            file = new File(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+".mp4");
            filea = new File(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+"_a.mp4");
            fileb = new File(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+"_b.mp4");

            //check if files exist and put tick on those present
            if(!file.exists()){
                comp = false;
                exComp = false;
            } else if(e.getExercise().isVideoA() && !filea.exists()){
                comp = false;
                exComp = false;
            } else if(e.getExercise().isVideoB() && !fileb.exists()){
                comp = false;
                exComp = false;
            }

            if(exComp){
                //show tick on exercise
                e.getExercise().isDownloaded =true;
                adapter.notifyItemChanged(exercises.indexOf(e));
            }
        }
        if(comp && exercises.size()!=0){
            startWorkout.setText("START WORKOUT");
            startWorkout.setOnClickListener(startClickListener);
        }


    }

    private View.OnClickListener startClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Gson gson = new GsonBuilder().create();
            ArrayList<String> videoPlayerItemList = new ArrayList<>();
            for(int i =0 ; i<exercises.size();i++){
                videoPlayerItemList.add(gson.toJson(new VideoPlayerItem(exercises.get(i))));
            }
            Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
            intent.putExtra("videoItemList",videoPlayerItemList);
            startActivity(intent);
        }
    };

    public void getVideo(String data) {
        noOfCurrentVideUser++;
        boolean firtVideo =true;
        String url = data;
        Uri downloadUri = Uri.parse(url);
        Uri destinationUri =  null;
        if (type.equals("tutorial")){
            destinationUri = Uri.parse(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+".mp4");

        }else if (type.equals("a")){
            destinationUri = Uri.parse(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+"_a.mp4");

        }else if (type.equals("b")){
            destinationUri = Uri.parse(getActivity().getFilesDir().toString()+"/videos/"+exercises.get(currentPosition).getExercise().get_id()+"_b.mp4");

        }
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .setDownloadContext(getActivity().getApplicationContext())//Optional
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {

                        //currentPosition++;
                        Toast.makeText(getActivity().getApplicationContext(),"completed download"+(currentPosition+1)+"type"+type,Toast.LENGTH_SHORT).show();
                        allVideoDownloaded = allVideoDownloaded && true;
                        if (type.equals("tutorial")){
                            type="a";
                            downloadFiles();

                        }else if (type.equals("a")){
                            type="b";
                            downloadFiles();

                        }else  if (type.equals("b")){
                            type="tutorial";
                            downloadFiles();
                            exercises.get(currentPosition-1).getExercise().isDownloaded =  true;
                            currentPosition++;
                            adapter.notifyItemChanged(currentPosition-1);

                        }




                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        Toast.makeText(getActivity().getApplicationContext(),"failed error in logs TAG error77 ",Toast.LENGTH_SHORT).show();
                        Log.d("error77",errorMessage+"\n"+"of number"+((int)currentPosition+1)+"\nof id: "+exercises.get(currentPosition).getExercise().get_id());
                        currentPosition++;
                        allVideoDownloaded = false;

                        downloadFiles();

                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downlaodedBytes, int progress) {
                        double p = (double)downlaodedBytes/totalBytes*100;
                        //Toast.makeText(getActivity().getApplicationContext(),"total"+ totalBytes+"dnld "+downlaodedBytes+"progress "+p,Toast.LENGTH_SHORT).show();
                        progressBar.setProgress((int)p);
                        progressTV.setText(String.format("%.2f", (float)p));
                        numberOfCurrentVideo.setText(String.valueOf(noOfCurrentVideUser)+"/"+videoCount);
                        exercises.get(currentPosition).getExercise().isDownloading = true;
                        exercises.get(currentPosition).getExercise().progess = (int)p;

                    }
                });
        int downloadId = downloadManager.add(downloadRequest);

    }

    @Override
    public void onClick(int position)
    {
        if(exercises.get(position).getExercise().isDownloaded)
        {
            Gson gson = new GsonBuilder().create();
            ArrayList<String> videoPlayerItemList = new ArrayList<>();
            videoPlayerItemList.add(gson.toJson(new VideoPlayerItem(exercises.get(position))));
            Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
            intent.putExtra("videoItemList", videoPlayerItemList);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(ctx, "Download the Workout", Toast.LENGTH_SHORT).show();
        }
    }
}
