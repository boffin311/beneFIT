package tech.iosd.benefit.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import agency.tango.materialintroscreen.SlideFragment;
import tech.iosd.benefit.Authentication.FacebookAuth;
import tech.iosd.benefit.Authentication.GoogleAuth;
import tech.iosd.benefit.Authentication.OtpActivity;
import tech.iosd.benefit.R;
import tech.iosd.benefit.SplashActivity;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Anubhav on 26-12-2017.
 */

public class LoginFragment extends SlideFragment {

//    Button mGoogleSignInButton;
    final int REQUEST_PHONE_VERIFICATION = 125;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.onboarding_login, container, false);


        Button mGoogleSignInButton = v.findViewById(R.id.google_login_custom);


        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.auth_area, new GoogleAuth())
                        .commit();
            }
        });


        Button mFacebookLogInButton = v.findViewById(R.id.fb_login_custom);

        mFacebookLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.auth_area, new FacebookAuth())
                        .commit();
            }
        });

        Button phoneLoginButton = v.findViewById(R.id.phone_sign_in_button);

        phoneLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getContext(), OtpActivity.class),REQUEST_PHONE_VERIFICATION);
            }
        });

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_PHONE_VERIFICATION){
            if(resultCode==RESULT_OK){

            }
            else if(resultCode== RESULT_CANCELED){

            }
        }
    }
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }



    @Override
    public int backgroundColor() {
        return R.color.welcomeStep3;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public boolean canMoveFurther() {
        return false;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "Please sign in to proceed";
    }
}
