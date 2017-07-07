package tech.iosd.benefit;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView appName;
    private Button _login, _signup;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appName = (TextView)findViewById(R.id.splash_appname);
        _login = (Button)findViewById(R.id.splash_login);       _login.setOnClickListener(this);
        _signup = (Button)findViewById(R.id.splash_signup);     _signup.setOnClickListener(this);
        linearLayout = (LinearLayout)findViewById(R.id.layout_login);

        appName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Amarante-Regular.ttf"));
        Animation rotate = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.rotate);
        rotate.setDuration(1000);
        appName.setAnimation(rotate);
        rotate.start();

        appName.animate().translationYBy(-100f).setDuration(1000).setStartDelay(1000).start();
        linearLayout.animate().alpha(1.0f).setDuration(1000).setStartDelay(1000).start();
    }

    @Override
    public void onClick(View v) {
        if(v == _login){
            Toast.makeText(getApplicationContext(), "Feature Not Available", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
        if(v == _signup){
            Toast.makeText(getApplicationContext(), "Feature Not Available", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }
}
