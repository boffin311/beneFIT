package tech.iosd.benefit.Authentication;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import tech.iosd.benefit.Constants.IntentConstants;
import tech.iosd.benefit.R;
import tech.iosd.benefit.SplashActivity;

public class OtpActivity extends AppCompatActivity {
    private static final int OTP_REQUEST = 99;
    String mVerificationId;
    Button mLoginButton;
    EditText mPhoneEditText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mPhoneEditText = findViewById(R.id.enter_otp_phone_number);

        mLoginButton = findViewById(R.id.get_otp_proceed);

        mAuth = FirebaseAuth.getInstance();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyPhone();

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
//                    if (mLoginSharedPreferences.getBoolean(ApplicationConstants.firstLogin, true) == true) {
                        startActivity(new Intent(OtpActivity.this, SplashActivity.class));
//                    }
//                    else {
//                        goToMainAcivity();
//                    }
                } else {
                    Snackbar.make(mLoginButton, "Sign in to continue", Snackbar.LENGTH_LONG).show();
                }

            }
        };

    }

    private void verifyPhone() {
        final AlertDialog alertDialog = new SpotsDialog(this, "Detecting Otp");
        alertDialog.setCancelable(false);
        alertDialog.show();

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                mLoginSharedPreferences.edit().putString(ApplicationConstants.phoneNumber, mPhoneEditText.getText().toString()).apply();
//                touchEnabled();
//                startActivity(new Intent(OtpActivity.this, SplashActivity.class));
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container_for_user_details,new EnterUserDetailsFragment())
                        .addToBackStack("Main")
                        .commit();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationId = s;
                mResendToken = forceResendingToken;
                mLoginButton.setEnabled(false);

                new CountDownTimer(10000, 1000) {

                    @Override
                    public void onTick(long l) {

                    }

                    public void onFinish() {
                        alertDialog.dismiss();
                        Intent i = new Intent(OtpActivity.this, EnterOtpActivity.class);
                        startActivityForResult(i, OTP_REQUEST);
                    }

                }.start();
            }

//            @Override
//            public void onCodeAutoRetrievalTimeOut(String s) {
//                super.onCodeAutoRetrievalTimeOut(s);
//                alertDialog.dismiss();
//                Intent i = new Intent(OtpActivity.this, EnterOtpActivity.class);
//                startActivityForResult(i, OTP_REQUEST);
//            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                alertDialog.dismiss();
                try {
                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                        mLoginButton.setText("Try Again");
                        mLoginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Snackbar.make(mLoginButton, "Incorrect Phone Number ", Snackbar.LENGTH_SHORT).show();
                    } else if (e instanceof FirebaseTooManyRequestsException) {
                        mLoginButton.setText("Try Again");
                        mLoginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Snackbar.make(mLoginButton, "Can't authenticate", Snackbar.LENGTH_LONG);
                    }
                } catch (Exception ex) {
                    Toast.makeText(OtpActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

                }

//                touchEnabled();
                mLoginButton.setText("Try Again");
//                mProgressBar.setVisibility(View.INVISIBLE);
            }
        };

        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber
                        ("+91" + mPhoneEditText.getText().toString()
                                , 1
                                , TimeUnit.MINUTES
                                , this
                                , mCallbacks);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .add(R.id.container_for_user_details, new EnterUserDetailsFragment())
                                    .addToBackStack("Main")
                                    .commit();

                        else {
                            mLoginButton.setText("Try Again");

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Snackbar.make(mLoginButton, "Invalid otp entered", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OTP_REQUEST && RESULT_OK == resultCode) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, IntentConstants.otpRequest);
            signInWithPhoneAuthCredential(credential);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()!=0)
            getFragmentManager().popBackStackImmediate();

        else
        super.onBackPressed();
    }
}
