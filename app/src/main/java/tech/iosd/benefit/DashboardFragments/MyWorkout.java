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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.model.CalendarEvent;
import devs.mulham.horizontalcalendar.model.CalendarItemStyle;
import devs.mulham.horizontalcalendar.utils.CalendarEventsPredicate;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarPredicate;
import tech.iosd.benefit.R;

public class MyWorkout extends Fragment
{
    public Calendar selDate;

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    Context ctx;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_fragment_my_workouts, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

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

        final TextView lbl_year = rootView.findViewById(R.id.my_workout_calendar_year);
        final TextView lbl_month = rootView.findViewById(R.id.my_workout_calendar_month);

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener()
        {
            @Override
            public void onDateSelected(Calendar date, int position)
            {
                selDate = date;
                lbl_year.setText(String.valueOf(date.get(Calendar.YEAR)));
                lbl_month.setText(months[date.get(Calendar.MONTH)]);
            }
        });

        return rootView;
    }
}
