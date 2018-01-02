package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import agency.tango.materialintroscreen.SlideFragment;
import tech.iosd.benefit.R;

/**
 * Created by Anubhav on 26-12-2017.
 */

public class BasicDetailFragment extends SlideFragment {
    TextView genderTextView;
    RadioButton activeRadioButton,sedentaryRadioButton,veryActiveRadioButton,moderateRadioButton,kgsRadioButton,lbsRadioButton,cmsRadioButton,feetRadioButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onboarding_getbasicdetails,container,false);

        /*
        final TextView maleTextView = (TextView) view.findViewById(R.id.male_checked_textView);

        maleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genderTextView.setText(maleTextView.getText().toString());
            }

        });

        final TextView femaleTextView = (TextView)view.findViewById(R.id.female_checkedtextView);

        femaleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                genderTextView.setText(femaleTextView.getText().toString());
            }
        });
        genderTextView = (TextView)view.findViewById(R.id.set_gender_text_view_onboarding);
        */

        activeRadioButton =(RadioButton) view.findViewById(R.id.active_radio_button);
        activeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sedentaryRadioButton.isChecked()||veryActiveRadioButton.isChecked()||moderateRadioButton.isChecked()) {
                    sedentaryRadioButton.setChecked(false);
                    veryActiveRadioButton.setChecked(false);
                    moderateRadioButton.setChecked(false);
                }

            }
        });
        sedentaryRadioButton = (RadioButton) view.findViewById(R.id.sedentary_radio_button);
        sedentaryRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activeRadioButton.isChecked()||veryActiveRadioButton.isChecked()||moderateRadioButton.isChecked()) {
                    activeRadioButton.setChecked(false);
                    veryActiveRadioButton.setChecked(false);
                    moderateRadioButton.setChecked(false);
                }
            }
        });
        veryActiveRadioButton =(RadioButton) view.findViewById(R.id.very_active_radio_button);
        veryActiveRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sedentaryRadioButton.isChecked()||activeRadioButton.isChecked()||moderateRadioButton.isChecked()) {
                    sedentaryRadioButton.setChecked(false);
                    activeRadioButton.setChecked(false);
                    moderateRadioButton.setChecked(false);
                }
            }
        });
        moderateRadioButton =(RadioButton) view.findViewById(R.id.moderate_radio_button);
        moderateRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sedentaryRadioButton.isChecked()||veryActiveRadioButton.isChecked()||activeRadioButton.isChecked()) {
                    sedentaryRadioButton.setChecked(false);
                    veryActiveRadioButton.setChecked(false);
                    activeRadioButton.setChecked(false);
                }
            }
        });
        kgsRadioButton = (RadioButton)view.findViewById(R.id.kgs_radio_button);
        kgsRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lbsRadioButton.isChecked()) {
                    lbsRadioButton.setChecked(false);
                }
            }
        });
        lbsRadioButton = (RadioButton)view.findViewById(R.id.lbs_radio_button);
        lbsRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kgsRadioButton.isChecked()) {
                    kgsRadioButton.setChecked(false);
                }
            }
        });
        cmsRadioButton = (RadioButton)view.findViewById(R.id.cms_radio_button);
        cmsRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feetRadioButton.isChecked()) {
                    feetRadioButton.setChecked(false);
                }
            }
        });
        feetRadioButton =(RadioButton) view.findViewById(R.id.feet_radio_button);
        feetRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cmsRadioButton.isChecked()) {
                    cmsRadioButton.setChecked(false);
                }
            }
        });


        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.welcomeStep2;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorPrimary;
    }


    @Override
    public boolean canMoveFurther() {
        return true;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return "NO BRO";
    }
}
