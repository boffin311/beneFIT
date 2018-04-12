package tech.iosd.benefit.OnBoardingFragments;

import android.app.AlertDialog;
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
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.R;

public class SetupProfile extends Fragment implements View.OnClickListener
{
    public life lifestyle = life.SEDENTARY;
    public Boolean isKgSelected = true;
    public Boolean isFtSelected = true;

    Context ctx;
    FragmentManager fm;
    Button weightKg;
    Button weightLbs;
    Button heightFt;
    Button heightCm;
    Button setupNext;
    RadioGroup lifestyleField;
    EditText ageField;
    TextView weightField;
    TextView heightField;
    FloatingActionButton btnMale;
    FloatingActionButton btnFemale;
    FloatingActionButton genderSelector;
    int weightPickerPos = 0;
    int heightPickerPos = 0;
    List<String> heightsCM;
    List<String> heightsFT;
    List<String> weightsKG;
    List<String> weightsLBS;

    enum life {SEDENTARY, MODERATE, ACTIVE, VERY_ACTIVE}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.onboarding_setup_profile, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        heightsCM = new ArrayList<>();
        heightsFT = new ArrayList<>();
        for (int i = 120; i <= 220; i++)
        {
            heightsCM.add(Integer.toString(i));
            double inches = (i / 2.54);
            int feet = (int) inches / 12;
            inches = round(inches - (feet * 12), 1);
            heightsFT.add(feet + "’ " + inches + "”");
        }

        weightsKG = new ArrayList<>();
        weightsLBS = new ArrayList<>();
        for (int i = 20; i <= 200; i++)
        {
            weightsKG.add(Integer.toString(i));
            double lbs = round( i * 2.20462262185, 1);
            weightsLBS.add(Double.toString(lbs));
        }

        weightKg = rootView.findViewById(R.id.get_started_profile_setup_kg);
        weightLbs = rootView.findViewById(R.id.get_started_profile_setup_lbs);
        heightFt = rootView.findViewById(R.id.get_started_profile_setup_ft);
        heightCm = rootView.findViewById(R.id.get_started_profile_setup_cm);
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

        ageField = rootView.findViewById(R.id.get_started_profile_setup_age);
        weightField = rootView.findViewById(R.id.get_started_profile_setup_weight);
        heightField = rootView.findViewById(R.id.get_started_profile_setup_height);
        weightKg.setOnClickListener(this);
        weightLbs.setOnClickListener(this);
        heightFt.setOnClickListener(this);
        heightCm.setOnClickListener(this);
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
        heightField.setOnClickListener(this);
        weightField.setOnClickListener(this);

        ageField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        weightField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                checkFields();
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
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });


        return rootView;
    }

    void checkFields()
    {
        String weight = weightField.getText().toString();
        String height = heightField.getText().toString();
        String age = ageField.getText().toString();
        if(!age.equals("") && !height.equals("") && !weight.equals(""))
        {
            setupNext.setAlpha(1.0f);
            setupNext.setEnabled(true);
        }
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
            case R.id.get_started_profile_setup_cm:
            {
                isFtSelected = false;
                heightFt.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                heightFt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                heightCm.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                heightCm.setTextColor(getResources().getColor(R.color.white));
                heightField.setText(heightsCM.get(heightPickerPos));
                break;
            }
            case R.id.get_started_profile_setup_ft:
            {
                isFtSelected = true;
                heightFt.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                heightFt.setTextColor(getResources().getColor(R.color.white));
                heightCm.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                heightCm.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                heightField.setText(heightsFT.get(heightPickerPos));
                break;
            }
            case R.id.get_started_profile_setup_lbs:
            {
                isKgSelected = false;
                weightKg.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                weightKg.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                weightLbs.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                weightLbs.setTextColor(getResources().getColor(R.color.white));
                weightField.setText(weightsLBS.get(weightPickerPos));
                break;
            }
            case R.id.get_started_profile_setup_kg:
            {
                isKgSelected = true;
                weightKg.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                weightKg.setTextColor(getResources().getColor(R.color.white));
                weightLbs.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                weightLbs.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                weightField.setText(weightsKG.get(weightPickerPos));
                break;
            }
            case R.id.get_started_profile_setup_height:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_height, null);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                final WheelPicker wheelPickerHeight = mView.findViewById(R.id.dialog_picker_height);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                wheelPickerHeight.setData( isFtSelected ? heightsFT : heightsCM);
                wheelPickerHeight.setSelectedItemPosition(heightPickerPos);

                dialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                        heightPickerPos = wheelPickerHeight.getCurrentItemPosition();
                        heightField.setText(wheelPickerHeight.getData().get(heightPickerPos).toString());
                    }
                });
                break;
            }
            case R.id.get_started_profile_setup_weight:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_weight, null);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                final WheelPicker wheelPickerWeight = mView.findViewById(R.id.dialog_picker_weight);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                wheelPickerWeight.setData(isKgSelected ? weightsKG : weightsLBS);
                wheelPickerWeight.setSelectedItemPosition(weightPickerPos);

                dialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                        weightPickerPos = wheelPickerWeight.getCurrentItemPosition();
                        weightField.setText(wheelPickerWeight.getData().get(weightPickerPos).toString());
                    }
                });
                break;
            }
        }
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
