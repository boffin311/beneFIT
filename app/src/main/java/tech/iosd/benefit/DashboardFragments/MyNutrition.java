package tech.iosd.benefit.DashboardFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import tech.iosd.benefit.R;

public class MyNutrition extends Fragment implements View.OnClickListener
{
    public Calendar selDate;

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    Context ctx;
    View rootView;
    FragmentManager fm;

    Button breakfast_save;
    EditText breakfast_edit_txt;
    Button mid_morning_save;
    EditText mid_morning_edit_txt;
    Button lunch_save;
    EditText lunch_edit_txt;
    Button snacks_save;
    EditText snacks_edit_txt;
    Button dinner_save;
    EditText dinner_edit_txt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.dashboard_fragment_my_nutrition, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(rootView, R.id.my_nutrition_calendar)
                .range(startDate, endDate)
                .datesNumberOnScreen(7)
                .mode(HorizontalCalendar.Mode.DAYS)
                .configure()
                .formatMiddleText("EEE\n").sizeMiddleText(12)
                .formatBottomText("dd").sizeBottomText(26)
                .showTopText(false)
                .end()
                .addEvents(new CalendarEventsPredicate()
                {
                    Random rnd = new Random();
                    @Override
                    public List<CalendarEvent> events(Calendar date)
                    {
                        List<CalendarEvent> events = new ArrayList<>();
                        events.add(new CalendarEvent(Color.TRANSPARENT, "event"));
                        if(rnd.nextBoolean()) events.add(new CalendarEvent(Color.RED, "event"));
                        return events;
                    }
                })
                .build();

        breakfast_save = rootView.findViewById(R.id.my_nutrition_breakfast);
        breakfast_edit_txt = rootView.findViewById(R.id.my_nutrition_breakfast_edit);
        mid_morning_save = rootView.findViewById(R.id.my_nutrition_mid_morning);
        mid_morning_edit_txt = rootView.findViewById(R.id.my_nutrition_mid_morning_edit);
        lunch_save = rootView.findViewById(R.id.my_nutrition_lunch);
        lunch_edit_txt = rootView.findViewById(R.id.my_nutrition_lunch_edit);
        snacks_save = rootView.findViewById(R.id.my_nutrition_snacks);
        snacks_edit_txt = rootView.findViewById(R.id.my_nutrition_snacks_edit);
        dinner_save = rootView.findViewById(R.id.my_nutrition_dinner);
        dinner_edit_txt = rootView.findViewById(R.id.my_nutrition_dinner_edit);

        breakfast_save.setOnClickListener(this);
        mid_morning_save.setOnClickListener(this);
        lunch_save.setOnClickListener(this);
        snacks_save.setOnClickListener(this);
        dinner_save.setOnClickListener(this);

        final TextView lbl_year = rootView.findViewById(R.id.my_nutrition_calendar_year);
        final TextView lbl_month = rootView.findViewById(R.id.my_nutrition_calendar_month);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                selDate = date;
                lbl_year.setText(String.valueOf(selDate.get(Calendar.YEAR)));
                lbl_month.setText(months[selDate.get(Calendar.MONTH)]);
            }
        });

        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.my_nutrition_breakfast:
                rootView.findViewById(R.id.my_nutrition_breakfast_saved).setVisibility(View.VISIBLE);
                break;
            case R.id.my_nutrition_mid_morning:
                rootView.findViewById(R.id.my_nutrition_mid_morning_saved).setVisibility(View.VISIBLE);
                break;
            case R.id.my_nutrition_lunch:
                rootView.findViewById(R.id.my_nutrition_lunch_saved).setVisibility(View.VISIBLE);
                break;
            case R.id.my_nutrition_snacks:
                rootView.findViewById(R.id.my_nutrition_snacks_saved).setVisibility(View.VISIBLE);
                break;
            case R.id.my_nutrition_dinner:
                rootView.findViewById(R.id.my_nutrition_dinner_saved).setVisibility(View.VISIBLE);
                break;
        }
    }
}
