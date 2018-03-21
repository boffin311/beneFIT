package tech.iosd.benefit.OnBoardingFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import tech.iosd.benefit.R;

public class SetupProfile extends Fragment implements View.OnClickListener
{
    public life lifestyle = life.SEDENTARY;
    public Boolean isKgSelected = true;
    public Boolean isFtSelected = true;
    public int height = 0;
    public int weight = 0;

    Context ctx;
    FragmentManager fm;
    Button weightKg;
    Button weightLbs;
    Button heightFt;
    Button heightIn;
    Button setupNext;
    RadioGroup lifestyleField;
    EditText weightField;
    EditText heightField;
    FloatingActionButton btnMale;
    FloatingActionButton btnFemale;
    FloatingActionButton genderSelector;

    enum life {SEDENTARY, MODERATE, ACTIVE, VERY_ACTIVE}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.onboarding_setup_profile, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        weightKg = rootView.findViewById(R.id.get_started_profile_setup_kg);
        weightLbs = rootView.findViewById(R.id.get_started_profile_setup_lbs);
        heightFt = rootView.findViewById(R.id.get_started_profile_setup_ft);
        heightIn = rootView.findViewById(R.id.get_started_profile_setup_in);
        setupNext = rootView.findViewById(R.id.get_started_profile_setup_next);
        setupNext.setAlpha(0.2f);
        setupNext.setEnabled(false);
        lifestyleField = rootView.findViewById(R.id.get_started_profile_setup_lifestyle);
        lifestyleField.check(R.id.get_started_profile_setup_lifestyle1);
        lifestyleField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case (R.id.get_started_profile_setup_lifestyle1):
                        lifestyle = life.SEDENTARY;
                        break;
                    case (R.id.get_started_profile_setup_lifestyle2):
                        lifestyle = life.MODERATE;
                        break;
                    case (R.id.get_started_profile_setup_lifestyle3):
                        lifestyle = life.ACTIVE;
                        break;
                    case (R.id.get_started_profile_setup_lifestyle4):
                        lifestyle = life.VERY_ACTIVE;
                        break;
                }
            }
        });

        weightField = rootView.findViewById(R.id.get_started_profile_setup_weight);
        heightField = rootView.findViewById(R.id.get_started_profile_setup_height);
        weightKg.setOnClickListener(this);
        weightLbs.setOnClickListener(this);
        heightFt.setOnClickListener(this);
        heightIn.setOnClickListener(this);
        btnMale = rootView.findViewById(R.id.get_started_profile_setup_male);
        btnFemale = rootView.findViewById(R.id.get_started_profile_setup_female);
        genderSelector = rootView.findViewById(R.id.get_started_profile_setup_gender);
        btnMale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGNotSelected));
        btnFemale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGSelected));
        btnMale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGSelected));
        btnFemale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGNotSelected));
        btnMale.setColorFilter(getResources().getColor(R.color.FABIndicatorSelected));
        btnFemale.setColorFilter(getResources().getColor(R.color.FABIndicatorNotSelected));
        btnMale.setOnClickListener(this);
        btnFemale.setOnClickListener(this);
        setupNext.setOnClickListener(this);

        weightField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                weight = Integer.parseInt(weightField.getText().toString());
                if(weight != 0 && height != 0)
                {
                    setupNext.setAlpha(1.0f);
                    setupNext.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        heightField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                height = Integer.parseInt(heightField.getText().toString());
                if(weight != 0 && height != 0)
                {
                    setupNext.setAlpha(1.0f);
                    setupNext.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });


        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.get_started_profile_setup_next:
            {
                fm.beginTransaction().replace(R.id.onboarding_content, new Login())
                        .addToBackStack(null)
                        .commit();
                break;
            }
            case R.id.get_started_profile_setup_female:
            {
                btnMale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGNotSelected));
                btnFemale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGSelected));
                btnMale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGSelected));
                btnFemale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGNotSelected));
                btnMale.setColorFilter(getResources().getColor(R.color.FABIndicatorSelected));
                btnFemale.setColorFilter(getResources().getColor(R.color.FABIndicatorNotSelected));
                genderSelector.setImageResource(R.drawable.female_img);
                break;
            }
            case R.id.get_started_profile_setup_male:
            {
                btnFemale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGNotSelected));
                btnMale.setBackgroundTintList(getResources().getColorStateList(R.color.FABIndicatorBGSelected));
                btnFemale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGSelected));
                btnMale.setRippleColor(getResources().getColor(R.color.FABIndicatorBGNotSelected));
                btnFemale.setColorFilter(getResources().getColor(R.color.FABIndicatorSelected));
                btnMale.setColorFilter(getResources().getColor(R.color.FABIndicatorNotSelected));
                genderSelector.setImageResource(R.drawable.male_img);
                break;
            }
            case R.id.get_started_profile_setup_in:
            {
                isFtSelected = false;
                heightFt.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                heightIn.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                break;
            }
            case R.id.get_started_profile_setup_ft:
            {
                isFtSelected = true;
                heightFt.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                heightIn.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                break;
            }
            case R.id.get_started_profile_setup_lbs:
            {
                isKgSelected = false;
                weightKg.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                weightLbs.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                break;
            }
            case R.id.get_started_profile_setup_kg:
            {
                isKgSelected = true;
                weightKg.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                weightLbs.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                break;
            }
        }
    }
}
