package tech.iosd.benefit.Authentication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import tech.iosd.benefit.R;
import tech.iosd.benefit.SplashActivity;

/**
 * Created by Anubhav on 27-12-2017.
 */

public class EnterUserDetailsFragment extends Fragment {
    Button datePickerButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.enter_user_details,container,false);

        datePickerButton = v.findViewById(R.id.enter_userdob);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = Calendar.getInstance();
                Log.i("Calendar . instance", newCalendar + "");
                int initialmonth = newCalendar.get(java.util.Calendar.MONTH);
                int initialyear = newCalendar.get(java.util.Calendar.YEAR);

                showDatePicker(getContext(), initialyear, initialmonth, 1);
            }
        });

        final EditText usernameEditText = v.findViewById(R.id.enter_username);

        Button submitButton = v.findViewById(R.id.enter_details_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEditText.getText().toString().isEmpty() || datePickerButton.getText().toString().contains("Enter your Date of birth"))
                    Snackbar.make(view, "Please enter valid details", Snackbar.LENGTH_SHORT);

                else {
                    // TODO : ADD SHARED PREFS AND DB CODE HERE FOR SAVING DETAILS OF THE USER AND EXECUTE THE CODE BELOW IN ONCOMPLETE METHOD

                    startActivity(new Intent(getContext(), SplashActivity.class));
                }
            }
        });


        return v;
    }

    public void showDatePicker(Context context, int year, int month, int defaultdate) {
        final DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                datePickerButton.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
            }
        }, year, month, 1);
        datePickerDialog.show();
    }
}
