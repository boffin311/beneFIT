package tech.iosd.benefit.OnBoardingFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

import tech.iosd.benefit.DashboardActivity;
import tech.iosd.benefit.R;

public class Login extends Fragment implements View.OnClickListener
{
    public String loginUsername = "";
    public String loginPassword = "";

    TextView signupBtn;
    TextView forgetPass;
    EditText usernameField;
    EditText passField;
    Button loginBtn;

    Context ctx;
    FragmentManager fm;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.onboarding_login, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        loginBtn = rootView.findViewById(R.id.get_started_login_btn);
        signupBtn = rootView.findViewById(R.id.get_started_login_signup_btn);
        forgetPass = rootView.findViewById(R.id.get_started_login_forget_pass);
        usernameField = rootView.findViewById(R.id.get_started_login_username);
        passField = rootView.findViewById(R.id.get_started_login_pass);

        loginBtn.setAlpha(0.2f);
        loginBtn.setEnabled(false);

        usernameField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                loginUsername = usernameField.getText().toString();
                if(!loginUsername.equals("") && !loginPassword.equals(""))
                {
                    loginBtn.setAlpha(1.0f);
                    loginBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        passField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                loginPassword = passField.getText().toString();
                if(!loginUsername.equals("") && !loginPassword.equals(""))
                {
                    loginBtn.setAlpha(1.0f);
                    loginBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        loginBtn.setOnClickListener(this);
        rootView.findViewById(R.id.get_started_login_fb).setOnClickListener(this);
        rootView.findViewById(R.id.get_started_login_google).setOnClickListener(this);
        forgetPass.setOnClickListener(this);
        signupBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.get_started_login_btn:
            {
                TextView invalidUsername = rootView.findViewById(R.id.get_started_login_invalid);
                TextView invalidPass = rootView.findViewById(R.id.get_started_pass_invalid);
                //TODO-Login

                Activity activity = getActivity();
                if(activity != null)
                {
                    Intent myIntent = new Intent(activity, DashboardActivity.class);
                    startActivity(myIntent);
                    getActivity().finish();
                }
                break;
            }
            case R.id.get_started_login_signup_btn:
            {
                fm.beginTransaction().replace(R.id.onboarding_content, new Signup())
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case R.id.get_started_login_fb:
            {
                //TODO-FB-Login
                break;
            }
            case R.id.get_started_login_google:
            {
                //TODO-Google-Login
                break;
            }
        }
    }
}
