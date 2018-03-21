package tech.iosd.benefit.OnBoardingFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import tech.iosd.benefit.R;

public class Signup extends Fragment implements View.OnClickListener
{
    public String email = "";
    public String username = "";
    public String password = "";

    Button signupNextBtn;
    Button signupBtn;
    TextView emailField;
    TextView userField;
    TextView passwordField;

    Context ctx;
    FragmentManager fm;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.onboarding_signup, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        signupNextBtn = rootView.findViewById(R.id.get_started_signup_next_btn);
        emailField = rootView.findViewById(R.id.get_started_signup_email);
        userField = rootView.findViewById(R.id.get_started_signup_username);
        passwordField = rootView.findViewById(R.id.get_started_signup_password);

        emailField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                email = emailField.getText().toString();
                if(!email.equals("") && !username.equals("") && !password.equals(""))
                {
                    signupNextBtn.setAlpha(1.0f);
                    signupNextBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        userField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                username = userField.getText().toString();
                if(!email.equals("") && !username.equals("") && !password.equals(""))
                {
                    signupNextBtn.setAlpha(1.0f);
                    signupNextBtn.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        passwordField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                password = passwordField.getText().toString();
                if(!email.equals("") && !username.equals("") && !password.equals(""))
                {
                    signupNextBtn.setAlpha(1.0f);
                    signupNextBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        signupNextBtn.setOnClickListener(this);
        rootView.findViewById(R.id.get_started_signup_login_btn).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.get_started_signup_next_btn:
            {
                TextView invalidEmail = rootView.findViewById(R.id.get_started_signup_email_invalid);
                TextView invalidUsername = rootView.findViewById(R.id.get_started_signup_username_invalid);
                TextView invalidPassword = rootView.findViewById(R.id.get_started_signup_password_invalid);
                //TODO-Sign-Up
                break;
            }
            case R.id.get_started_signup_login_btn:
            {
                fm.popBackStack();
                break;
            }
        }
    }
}
