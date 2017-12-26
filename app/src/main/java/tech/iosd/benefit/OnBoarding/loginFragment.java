package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

import agency.tango.materialintroscreen.SlideFragment;
import tech.iosd.benefit.Authentication.FacebookAuth;
import tech.iosd.benefit.Authentication.GoogleAuth;
import tech.iosd.benefit.R;

/**
 * Created by Anubhav on 26-12-2017.
 */

public class loginFragment extends SlideFragment {

    SignInButton mGoogleSignInButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.onboarding_login, container, false);

        mGoogleSignInButton = (SignInButton) v.findViewById(R.id.google_sign_in_button);


        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.auth_area, new GoogleAuth())
                        .commit();
            }
        });


        LoginButton mFacebookLogInButton = v.findViewById(R.id.facebook_sign_in_button);

        mFacebookLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.auth_area, new FacebookAuth())
                        .commit();
            }
        });

        return v;

    }

    @Override
    public int backgroundColor() {
        return R.color.colorAccent;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public boolean canMoveFurther() {
        return super.canMoveFurther();
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return super.cantMoveFurtherErrorMessage();
    }
}
