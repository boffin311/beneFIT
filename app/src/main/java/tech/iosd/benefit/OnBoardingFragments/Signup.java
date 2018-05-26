package tech.iosd.benefit.OnBoardingFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.Model.Response;
import tech.iosd.benefit.Model.User;
import tech.iosd.benefit.Network.NetworkUtil;
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

    private CompositeSubscription mSubscriptions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        mSubscriptions = new CompositeSubscription();

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

                User user = new User();
                user.setEmail(emailField.getText().toString());
                user.setUserName(userField.getText().toString());
                user.setPassword(passwordField.getText().toString());
                showSnackBarMessage("Registering..." );

                registerUser(user);

                break;
            }
            case R.id.get_started_signup_login_btn:
            {
                fm.popBackStack();
                break;
            }
        }
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_LONG).show();

        }
    }

    private void registerUser(User user) {

        mSubscriptions.add(NetworkUtil.getRetrofit().register(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {

        showSnackBarMessage(response.getMessage());
        String string = response.token.token;
        Toast.makeText(getActivity().getApplicationContext(),string, Toast.LENGTH_LONG).show();

    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //Log.d("error77",error.getMessage());

            showSnackBarMessage("Network Error !");
        }
    }

}
