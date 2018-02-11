package tech.iosd.benefit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class OnBoardingActivity extends AppCompatActivity
{
    //Login
    public String loginUsername = "";
    public String loginPassword = "";
    //Signup
    public String email = "";
    public String username = "";
    public String password = "";
    public Boolean isMale = false;
    public int height = 0;
    public int weight = 0;
    public life lifestyle = life.SEDENTARY;
    public Boolean isKgSelected = true;
    public Boolean isCmSelected = true;

    enum life {SEDENTARY, MODERATE, ACTIVE, VERY_ACTIVE};

    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_getstarted);
        ctx = this;

        ImageView motto_guy = findViewById(R.id.get_started_motto_logo);
        TextView motto = findViewById(R.id.get_started_motto);
        motto_guy.startAnimation(AnimationUtils.loadAnimation(this, R.anim.left_show));
        Animation motto_fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        motto_fade.setStartOffset(1000);
        motto.startAnimation(motto_fade);

        final ViewPager viewPager = findViewById(R.id.get_started_goals_pager);
        viewPager.setAdapter(new OnBoardingGoals(this));
        ImageView pg1 = findViewById(R.id.get_started_pager_indicator1);
        pg1.setColorFilter(Color.parseColor("#404040"));

        final Button startBtn = findViewById(R.id.get_started_startBtn);
        Animation start_fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        start_fade.setStartOffset(1200);
        startBtn.startAnimation(start_fade);
        startBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startBtn.setVisibility(View.INVISIBLE);
                LinearLayout layout = findViewById(R.id.get_started_goals);
                layout.setVisibility(View.VISIBLE);
                layout.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.bottom_up));
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                int selIndex = viewPager.getCurrentItem();
                ImageView pg1 = findViewById(R.id.get_started_pager_indicator1);
                ImageView pg2 = findViewById(R.id.get_started_pager_indicator2);
                ImageView pg3 = findViewById(R.id.get_started_pager_indicator3);

                switch (selIndex)
                {
                    case 0:
                    {
                        pg1.setColorFilter(Color.parseColor("#404040"));
                        pg2.setColorFilter(Color.parseColor("#919191"));
                        pg3.setColorFilter(Color.parseColor("#919191"));
                        break;
                    }
                    case 1:
                    {
                        pg1.setColorFilter(Color.parseColor("#919191"));
                        pg2.setColorFilter(Color.parseColor("#404040"));
                        pg3.setColorFilter(Color.parseColor("#919191"));
                        break;
                    }
                    case 2:
                    {
                        pg1.setColorFilter(Color.parseColor("#919191"));
                        pg2.setColorFilter(Color.parseColor("#919191"));
                        pg3.setColorFilter(Color.parseColor("#404040"));
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        findViewById(R.id.get_started_continue).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setContentView(R.layout.onboarding_login);
                onSetupProfile();
            }
        });
    }

    void onSetupProfile()
    {
        final LinearLayout setupLayout = findViewById(R.id.get_started_profile_setup);
        final ScrollView setupScrollLayout = findViewById(R.id.get_started_profile_setup_scroll);
        final LinearLayout signupLayout = findViewById(R.id.get_started_signup);
        setupScrollLayout.setVisibility(View.VISIBLE);

        final Button weightKg = findViewById(R.id.get_started_profile_setup_kg);
        final Button weightLbs = findViewById(R.id.get_started_profile_setup_lbs);
        final Button heightCm = findViewById(R.id.get_started_profile_setup_cm);
        final Button heightIn = findViewById(R.id.get_started_profile_setup_in);
        final Button setupNext = findViewById(R.id.get_started_profile_setup_next);
        setupNext.setAlpha(0.2f);
        setupNext.setEnabled(false);
        final RadioGroup lifestyleField = findViewById(R.id.get_started_profile_setup_lifestyle);
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

        final EditText weightField = findViewById(R.id.get_started_profile_setup_weight);
        final EditText heightField = findViewById(R.id.get_started_profile_setup_height);
        weightField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

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
            public void afterTextChanged(Editable editable)
            {

            }
        });
        heightField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

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
            public void afterTextChanged(Editable editable)
            {

            }
        });

        weightKg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!isKgSelected)
                {
                    isKgSelected = true;
                    weightKg.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                    weightLbs.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                }
            }
        });
        weightLbs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(isKgSelected)
                {
                    isKgSelected = false;
                    weightKg.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                    weightLbs.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                }
            }
        });
        heightCm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!isCmSelected)
                {
                    isCmSelected = true;
                    heightCm.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                    heightIn.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                }
            }
        });
        heightIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(isCmSelected)
                {
                    isCmSelected = false;
                    heightCm.setBackground(getResources().getDrawable(R.drawable.button_style_off));
                    heightIn.setBackground(getResources().getDrawable(R.drawable.button_style_on));
                }
            }
        });

        final FloatingActionButton btnMale = findViewById(R.id.get_started_profile_setup_male);
        final FloatingActionButton btnFemale = findViewById(R.id.get_started_profile_setup_female);
        btnMale.setColorFilter(Color.parseColor("#c8c8c8"));
        btnFemale.setColorFilter(Color.parseColor("#ffffff"));
        btnMale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!isMale)
                {
                    isMale = true;
                    btnMale.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#70cceb")));
                    btnFemale.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#dfdfdf")));
                    btnMale.setRippleColor(Color.parseColor("#dfdfdf"));
                    btnFemale.setRippleColor(Color.parseColor("#70cceb"));
                    btnMale.setColorFilter(Color.parseColor("#ffffff"));
                    btnFemale.setColorFilter(Color.parseColor("#c8c8c8"));

                    FloatingActionButton gender = findViewById(R.id.get_started_profile_setup_gender);
                    gender.setImageResource(R.drawable.male_img);
                }
            }
        });
        btnFemale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(isMale)
                {
                    isMale = false;
                    btnMale.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#dfdfdf")));
                    btnFemale.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#70cceb")));
                    btnMale.setRippleColor(Color.parseColor("#70cceb"));
                    btnFemale.setRippleColor(Color.parseColor("#dfdfdf"));
                    btnMale.setColorFilter(Color.parseColor("#c8c8c8"));
                    btnFemale.setColorFilter(Color.parseColor("#ffffff"));

                    FloatingActionButton gender = findViewById(R.id.get_started_profile_setup_gender);
                    gender.setImageResource(R.drawable.female_img);
                }
            }
        });

        setupNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO-After-Profile-Setup
                LinearLayout loginLayout = findViewById(R.id.get_started_login);
                setupLayout.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_out));
                setupScrollLayout.setVisibility(View.INVISIBLE);
                loginLayout.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_in));
                loginLayout.setVisibility(View.VISIBLE);
                onLogin();
            }
        });
    }

    void onLogin()
    {
        final Button signupNextBtn = findViewById(R.id.get_started_signup_next_btn);
        final TextView emailField = findViewById(R.id.get_started_signup_email);
        final TextView userField = findViewById(R.id.get_started_signup_username);
        final TextView passwordField = findViewById(R.id.get_started_signup_password);
        emailField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                email = emailField.getText().toString();
                if(!email.equals("") && !username.equals("") && !password.equals(""))
                {
                    signupNextBtn.setAlpha(1.0f);
                    signupNextBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        userField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                username = userField.getText().toString();
                if(!email.equals("") && !username.equals("") && !password.equals(""))
                {
                    signupNextBtn.setAlpha(1.0f);
                    signupNextBtn.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        passwordField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                password = passwordField.getText().toString();
                if(!email.equals("") && !username.equals("") && !password.equals(""))
                {
                    signupNextBtn.setAlpha(1.0f);
                    signupNextBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        signupNextBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TextView invalidEmail = findViewById(R.id.get_started_signup_email_invalid);
                TextView invalidUsername = findViewById(R.id.get_started_signup_username_invalid);
                TextView invalidPassword = findViewById(R.id.get_started_signup_password_invalid);

                //TODO-Sign-Up
            }
        });

        final Button loginBtn = findViewById(R.id.get_started_login_btn);
        final TextView signupBtn = findViewById(R.id.get_started_login_signup_btn);
        final TextView gobackBtn = findViewById(R.id.get_started_signup_login_btn);
        final TextView forgetPass = findViewById(R.id.get_started_login_forget_pass);
        final EditText usernameField = findViewById(R.id.get_started_login_username);
        final EditText passField = findViewById(R.id.get_started_login_pass);

        loginBtn.setAlpha(0.2f);
        loginBtn.setEnabled(false);
        signupNextBtn.setAlpha(0.2f);
        signupNextBtn.setEnabled(false);
        usernameField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

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
            public void afterTextChanged(Editable editable)
            {

            }
        });
        passField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

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
            public void afterTextChanged(Editable editable)
            {

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TextView invalidUsername = findViewById(R.id.get_started_login_invalid);
                TextView invalidPass = findViewById(R.id.get_started_pass_invalid);
                //TODO-Login

                Intent myIntent = new Intent(OnBoardingActivity.this, DashboardActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        findViewById(R.id.get_started_login_fb).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO-Facebook-Login
            }
        });

        findViewById(R.id.get_started_login_google).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO-Google-Login
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //TODO-Forget-Password
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LinearLayout loginLayout = findViewById(R.id.get_started_login);
                LinearLayout signupLayout = findViewById(R.id.get_started_signup);
                loginLayout.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_out));
                loginLayout.setVisibility(View.INVISIBLE);
                signupLayout.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_in));
                signupLayout.setVisibility(View.VISIBLE);
            }
        });

        gobackBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LinearLayout loginLayout = findViewById(R.id.get_started_login);
                LinearLayout signupLayout = findViewById(R.id.get_started_signup);
                loginLayout.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_in));
                loginLayout.setVisibility(View.VISIBLE);
                signupLayout.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.fade_out));
                signupLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
}