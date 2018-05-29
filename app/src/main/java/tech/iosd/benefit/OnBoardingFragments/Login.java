package tech.iosd.benefit.OnBoardingFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.DashboardActivity;
import tech.iosd.benefit.DashboardFragments.ChoosePlan;
import tech.iosd.benefit.Model.Response;
import tech.iosd.benefit.Model.UserDetails;
import tech.iosd.benefit.Model.UserForLogin;
import tech.iosd.benefit.Model.UserGoogleLogin;
import tech.iosd.benefit.Network.NetworkUtil;
import tech.iosd.benefit.R;
import tech.iosd.benefit.Utils.Constants;
import tech.iosd.benefit.Utils.Validation;

import static android.app.Activity.RESULT_CANCELED;
import static tech.iosd.benefit.Utils.Constants.RC_SIGN_IN;

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

    private CompositeSubscription mSubscriptions;
    private CompositeSubscription mSubscriptionsGoogle;

    private SharedPreferences mSharedPreferences;

    private GoogleSignInClient mGoogleSignInClient;

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


        mSubscriptions = new CompositeSubscription();
        mSubscriptionsGoogle = new CompositeSubscription();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


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

                if(Validation.validateFields(usernameField.getText().toString())&&Validation.validateFields(passField.getText().toString())){
                    loginProcess(new UserForLogin(usernameField.getText().toString(), passField.getText().toString()));
                    showSnackBarMessage("Connecting to server...");
                }else {
                    showSnackBarMessage("Enter valid details.");
                }




                break;
            }
            case R.id.get_started_login_signup_btn:
            {
                Signup signup = new Signup();
                signup.setArguments(getArguments());
                fm.beginTransaction().replace(R.id.onboarding_content, signup)
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
                googleSignIn();


                break;
            }
            case R.id.get_started_login_forget_pass:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_forget_password, null);
                Button dialogCancel = mView.findViewById(R.id.dialog_cancel);
                final Button dialogAccept = mView.findViewById(R.id.dialog_accept);
                dialogAccept.setAlpha(0.2f);
                dialogAccept.setEnabled(false);

                final EditText dialogEmail = mView.findViewById(R.id.dialog_email);
                TextView dialogEmailInvalid = mView.findViewById(R.id.dialog_email_invalid);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                dialogEmail.addTextChangedListener(new TextWatcher()
                {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
                    {
                        if(!dialogEmail.getText().toString().equals(""))
                        {
                            dialogAccept.setAlpha(1.0f);
                            dialogAccept.setEnabled(true);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) { }
                });
                dialogCancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });
                dialogAccept.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                        showForgetPassSuccessMessage();
                    }
                });
                break;

            }
        }
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (resultCode != RESULT_CANCELED && requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            checkForValidToken(account.getEmail(),account.getIdToken());

        } catch (ApiException e) {

            Toast.makeText(getActivity().getApplicationContext(), "signInResult:failed code=" + e.getStatusCode(),Toast.LENGTH_LONG).show();

        }
    }

    void showForgetPassSuccessMessage()
    {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_message, null);
        TextView dialogMsg = mView.findViewById(R.id.dialog_message);
        Button dialogDone = mView.findViewById(R.id.dialog_done);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        dialogMsg.setText("A password reset message was sent to your email address...");
        dialogDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });
    }

    private void loginProcess(UserForLogin userForLogin) {

        mSubscriptions.add(NetworkUtil.getRetrofit().login(userForLogin)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response) {


        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN,response.token.token);

        editor.apply();
        showSnackBarMessage("Getting user details..");

        getUserDetails(response.token.token);

        /*Activity activity = getActivity();
        if(activity != null)
        {
            Intent myIntent = new Intent(activity, DashboardActivity.class);
            myIntent.putExtra("height",userDetails.getHeight());
            myIntent.putExtra("weight",userDetails.getWeight());
            startActivity(myIntent);
            getActivity().finish();
        }*/

    }

    private void handleError(Throwable error) {


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                if(((HttpException) error).code()==401){
                    showSnackBarMessage("Sorry you don't have an account.\nCreate one and continue.");

                }
                else {
                    String errorBody = ((HttpException) error).response().errorBody().string();
                    Response response = gson.fromJson(errorBody,Response.class);
                    showSnackBarMessage(response.getMessage());
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }

    private void getUserDetails(String token) {

        mSubscriptions.add(NetworkUtil.getRetrofit(token).getProfile(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleDetailsResponse,this::handleDetailsError));
    }

    private void handleDetailsResponse(UserDetails userDetails) {

        Activity activity = getActivity();
        if(activity != null)
        {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(Constants.HEIGHT,userDetails.getHeight());
            editor.putInt(Constants.WEIGHT,userDetails.getWeight());
            editor.apply();

            Intent myIntent = new Intent(activity, DashboardActivity.class);

            startActivity(myIntent);
            getActivity().finish();
        }

    }

    private void handleDetailsError(Throwable error) {



        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {
                Toast.makeText(getActivity().getApplicationContext(),"You have been logged out.",Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(Constants.TOKEN,"");

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            //Log.d("errorabsd: ",error.getMessage());
            showSnackBarMessage("Network Error !");

        }
    }
    private void checkForValidToken(String email, String token){
        mSubscriptionsGoogle.add(NetworkUtil.getRetrofit().loginGoogle(new UserGoogleLogin(email,token))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_SHORT).show();
        }
    }
}
