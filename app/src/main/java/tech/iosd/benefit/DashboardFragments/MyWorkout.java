package tech.iosd.benefit.DashboardFragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PipedReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import tech.iosd.benefit.Adapters.DashboardWorkoutAdapter;
import tech.iosd.benefit.Model.*;
import tech.iosd.benefit.Network.NetworkUtil;
import tech.iosd.benefit.R;

public class MyWorkout extends Fragment
{
    public Calendar selDate;

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    Context ctx;
    FragmentManager fm;

    private String selectedDate;
    private SimpleDateFormat dateFormat;
    private ProgressDialog progressDialog;
    private CompositeSubscription compositeSubscription;
    private DatabaseHandler db;
    private RecyclerView recyclerView;
    private DashboardWorkoutAdapter adapter;
    private ArrayList<Exercise>  exercises;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_my_workouts, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();
        progressDialog =  new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("working..");

        compositeSubscription = new CompositeSubscription();

        db = new DatabaseHandler(getContext());

        recyclerView =  rootView.findViewById(R.id.dashboard_my_workouts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new DashboardWorkoutAdapter(exercises,getActivity());

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.my_workout_calendar)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .mode(HorizontalCalendar.Mode.DAYS)
                .configure()
                .formatMiddleText("EEE\n").sizeMiddleText(12)
                .formatBottomText("dd").sizeBottomText(26)
                .showTopText(false)
                .end()
                .build();

        final TextView lbl_year = rootView.findViewById(R.id.my_workout_calendar_year);
        final TextView lbl_month = rootView.findViewById(R.id.my_workout_calendar_month);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        selectedDate = dateFormat.format(Calendar.getInstance().getTime());


        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                selDate = date;
                selectedDate = dateFormat.format(date.getTime());
                lbl_year.setText(String.valueOf(date.get(Calendar.YEAR)));
                lbl_month.setText(months[date.get(Calendar.MONTH)]);
                progressDialog.show();
                getWorkoutData(selectedDate);
            }
        });
        getWorkoutData(selectedDate);
        return rootView;
    }

    private void getWorkoutData(String date){
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }

        compositeSubscription.add(NetworkUtil.getRetrofit(db.getUserToken()).getWorkoutforDate(date,db.getUserToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetMeal,this::handleErrorGetMeal));
    }

    private void handleResponseGetMeal(ResponseForWorkoutForDate responseForWorkoutForDate) {
        progressDialog.hide();
        if (!responseForWorkoutForDate.isSuccess())
            return;
        Log.d("error77"," " +responseForWorkoutForDate.getData().get(0).getWorkout().getExercises().size());
        exercises = responseForWorkoutForDate.getData().get(0).getWorkout().getExercises();
        adapter.setExercises(exercises);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void handleErrorGetMeal(Throwable error) {
        progressDialog.hide();
        Log.d("error77",error.getMessage());


        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
//                Response response = gson.fromJson(errorBody,Response.class);
                //showSnackBarMessage(response.getMessage());
               // Log.d("error77",error.getMessage());

                fm.popBackStack();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
           // Log.d("error77",error.getMessage());

           // showSnackBarMessage("Network Error !");
        }
    }
}
