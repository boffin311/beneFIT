package tech.iosd.benefit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatesActivity extends AppCompatActivity {

    private ImageButton btn;
    private TextView more;
    boolean IsVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        more = (TextView)findViewById(R.id.more_text);
        more.setVisibility(View.GONE); IsVisible = false;

        btn = (ImageButton)findViewById(R.id.more_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IsVisible){
                    btn.animate().rotation(180f).setDuration(200).start();
                    IsVisible = true;
                    more.setVisibility(View.VISIBLE);
                }
                else{
                    btn.animate().rotation(0f).setDuration(200).start();
                    IsVisible = false;
                    more.setVisibility(View.GONE);
                }
            }
        });

    }
}
