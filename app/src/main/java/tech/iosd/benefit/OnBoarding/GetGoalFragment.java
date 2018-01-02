package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import agency.tango.materialintroscreen.SlideFragment;
import tech.iosd.benefit.R;
import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by Anubhav on 26-12-2017.
 */

public class GetGoalFragment extends SlideFragment {
    private boolean selectedSomething =false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onboarding_getgoal, container, false);
        final ExplosionField explosionField = ExplosionField.attach2Window(getActivity());

        final TextView fat = view.findViewById(R.id.fat_text);
        final TextView healthy = view.findViewById(R.id.healthy_text);
        final TextView muscle = view.findViewById(R.id.muscle_text);
        final ImageView fatImage = view.findViewById(R.id.fat_person_image);
        final ImageView healthyImage = view.findViewById(R.id.healthy_person_image);
        final ImageView muscleImage = view.findViewById(R.id.muscular_person_image);


        fat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explosionField.explode(view);
                explosionField.explode(fatImage);
                selectedSomething=true;
            }
        });
        healthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explosionField.explode(view);
                explosionField.explode(healthyImage);
                selectedSomething=true;
            }
        });
        muscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explosionField.explode(view);
                explosionField.explode(muscleImage);
                selectedSomething=true;
            }
        });


        fatImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explosionField.explode(view);
                explosionField.explode(fat);
                selectedSomething=true;
            }
        });

        healthyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explosionField.explode(view);
                explosionField.explode(healthy);
                selectedSomething=true;
            }
        });
        muscleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explosionField.explode(view);
                explosionField.explode(muscle);
                selectedSomething=true;
            }
        });

        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.welcomeStep1;
    }

    @Override
    public int buttonsColor() {
        return R.color.welcomeStep1Dark;
    }

    @Override
    public boolean canMoveFurther() {
        return true;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return super.cantMoveFurtherErrorMessage();
    }
}
