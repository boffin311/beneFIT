package tech.iosd.benefit.DashboardFragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import tech.iosd.benefit.R;

public class BodyCompositionAnalysis extends Fragment implements AdapterView.OnItemClickListener
{
    public String name = "";
    public String email = "";
    public String phone = "";
    public int timing = 0;

    private String[] call_timings = { "9 AM to 12 PM" , "12 PM to 3 PM", "3 PM to 6 PM" , "6 PM to 9 PM" };
    private String[] cities = { "New Delhi", "Mumbai", "Pune", "Bangalore", "Hyderabad" };

    Context ctx;
    FragmentManager fm;
    TextView nameField;
    TextView emailField;
    TextView phoneField;
    Button callButton;
    ListView timingsListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_body_composition_analysis, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        timingsListView = rootView.findViewById(R.id.dashboard_measurement_call_time);
        final ArrayAdapter<String> timingsAdapter = new ArrayAdapter<>(ctx, R.layout.listview_text, call_timings);
        timingsListView.setOnItemClickListener(this);
        timingsListView.setAdapter(timingsAdapter);
        ((TextView) timingsListView.getAdapter().getView(0, null, timingsListView)).setTypeface(null, Typeface.BOLD);

        nameField = rootView.findViewById(R.id.dashboard_measurement_data_name);
        emailField = rootView.findViewById(R.id.dashboard_measurement_data_email);
        phoneField = rootView.findViewById(R.id.dashboard_measurement_data_phone);
        callButton = rootView.findViewById(R.id.dashboard_measurement_data_call);

        callButton.setAlpha(0.3f);
        callButton.setEnabled(false);

        nameField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                name = nameField.getText().toString();
                if(!email.equals("") && !name.equals("") && !phone.equals(""))
                {
                    callButton.setAlpha(1.0f);
                    callButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        emailField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                email = emailField.getText().toString();
                if(!email.equals("") && !name.equals("") && !phone.equals(""))
                {
                    callButton.setAlpha(1.0f);
                    callButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        phoneField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                phone = phoneField.getText().toString();
                if(!email.equals("") && !name.equals("") && !phone.equals(""))
                {
                    callButton.setAlpha(1.0f);
                    callButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        Spinner citySpinner = rootView.findViewById(R.id.dashboard_measurement_data_city);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_item, cities);
        citySpinner.setAdapter(cityAdapter);

        rootView.findViewById(R.id.dashboard_measurement_data_call).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        TextView t = (TextView) timingsListView.getAdapter().getView(timing, null, timingsListView);
        t.setTypeface(null, Typeface.NORMAL);
        timing = i;
        t = (TextView) timingsListView.getAdapter().getView(timing, null, timingsListView);
        t.setTypeface(null, Typeface.BOLD);
    }
}
