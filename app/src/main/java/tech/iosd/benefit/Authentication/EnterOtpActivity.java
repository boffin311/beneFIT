package tech.iosd.benefit.Authentication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import tech.iosd.benefit.Constants.IntentConstants;
import tech.iosd.benefit.R;

public class EnterOtpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final Button submitButton = findViewById(R.id.enter_otp_submit);
        final EditText enterOtpEditText = findViewById(R.id.enter_otp_pin);

        final Intent i = getIntent();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enterOtpEditText.getText().toString().isEmpty()) {
                    enterOtpEditText.setError("Enter a valid code");
                } else {
                    i.putExtra(IntentConstants.otpRequest, enterOtpEditText.getText().toString());
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }
}
