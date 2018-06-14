package tech.iosd.benefit.OnBoardingFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.DashboardActivity;
import tech.iosd.benefit.Model.DatabaseHandler;
import tech.iosd.benefit.Model.Response;
import tech.iosd.benefit.Model.UserDetails;
import tech.iosd.benefit.Network.NetworkUtil;
import tech.iosd.benefit.R;
import tech.iosd.benefit.Utils.Constants;

public class GetStarted extends Fragment implements View.OnClickListener
{
    Context ctx;
    FragmentManager fm;
    private CompositeSubscription mSubscriptions;
    private DatabaseHandler db;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.onboarding_get_started, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();


        db = new DatabaseHandler(getContext());



        ImageView motto_guy = rootView.findViewById(R.id.get_started_motto_logo);
        TextView motto = rootView.findViewById(R.id.get_started_motto);
        motto_guy.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.left_show));
        Animation motto_fade = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
        motto_fade.setStartOffset(1000);
        motto.startAnimation(motto_fade);

        final Button startBtn = rootView.findViewById(R.id.get_started_startBtn);
        Animation start_fade = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
        start_fade.setStartOffset(1200);
        startBtn.startAnimation(start_fade);
        startBtn.setOnClickListener(this);

        mSubscriptions = new CompositeSubscription();


        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.get_started_startBtn:
            {
                String token="";
                try {
                     token= db.getUserToken();

                }catch (Exception e){
                    Toast.makeText(getContext(),"error\nreinstall app or contact developer",Toast.LENGTH_SHORT).show();

                    Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();


                }

                if(token.compareTo("")==0 )
                {
                    fm.beginTransaction().replace(R.id.onboarding_content, new ChooseAGoal())
                            .addToBackStack(null)
                            .commit();
                }
                else {

                    showSnackBarMessage("Logging you in.\nPlease wait...");
                    getUserDetails(token);
                }

                break;
            }
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
                try {
                     db.userLogOut();

                }catch (Exception e){
                    Toast.makeText(getContext(),"error\nreinstall app or contact developer",Toast.LENGTH_SHORT).show();

                }


                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage().toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            //Log.d("errorabsd: ",error.getMessage());
            showSnackBarMessage("Network Error !");

        }
    }


    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message, Snackbar.LENGTH_SHORT).show();
        }
    }
}
