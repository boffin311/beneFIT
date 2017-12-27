package tech.iosd.benefit.Authentication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tech.iosd.benefit.R;
import tech.iosd.benefit.SplashActivity;

/**
 * Created by Anubhav on 27-12-2017.
 */

public class GetOtpFragment extends Fragment {
    Button mLoginButton;
    String mVerificationId;
    EditText mPhoneEditText;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.get_otp_fragment, container, false);

        mPhoneEditText = v.findViewById(R.id.enter_otp_phone_number);

        mLoginButton = v.findViewById(R.id.get_otp_proceed);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyPhone();

            }
        });

        return v;
    }

    private void verifyPhone() {
        final AlertDialog alertDialog = new SpotsDialog(getContext(), "Detecting Otp");
        alertDialog.setCancelable(false);
        alertDialog.show();

        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                mLoginSharedPreferences.edit().putString(ApplicationConstants.phoneNumber, mPhoneEditText.getText().toString()).apply();
//                touchEnabled();
                startActivity(new Intent(getContext(), SplashActivity.class));
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

//                        if (mLoginSharedPreferences.getString(ApplicationConstants.phoneNumber, null) == null) {
//                            Intent i = new Intent(getContext(), OtpManual.class);
//                            startActivityForResult(i, OTP_REQUEST);
//                          }
//                        if(Firebase)
//                        getFragmentManager()
//                                .beginTransaction()
//                                .add(R.id.otp_auth_area, new EnterOtpFragment())
//                                .addToBackStack("Main")
//                                .commit();
//
                    }

                }.start();
            }

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
                    Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();

                }

//                touchEnabled();
                mLoginButton.setText("Try Again");
//                mProgressBar.setVisibility(View.INVISIBLE);
            }
        };

//        mAuth.useAppLanguage();
        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber
                        ("+91" + mPhoneEditText.getText().toString()
                                , 1
                                , TimeUnit.MINUTES
                                , getActivity()
                                , mCallbacks);
    }

//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            startActivity(new Intent(Login.this, GetDetailsActivity.class));
//                            mLoginSharedPreferences.edit().putString(ApplicationConstants.phoneNumber, mPhoneEditText.getText().toString()).apply();
//
////                            FirebaseUser user = task.getResult().getUser();
//                            // ...
//                        } else {
//                            touchEnabled();
//                            mLoginButton.setText("Try Again");
//                            // Sign in failed, display a message and update the UI
////                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                                touchEnabled();
//                                Snackbar.make(mLoginButton, "Invalid otp entered", Snackbar.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                });
//    }

}