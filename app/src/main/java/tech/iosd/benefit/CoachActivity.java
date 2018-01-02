package tech.iosd.benefit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import tech.iosd.benefit.Adapters.CoachActivityOptionAdapter;
import tech.iosd.benefit.ListItems.CoachActivityOptionList;

public class CoachActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout slide;
    private ShimmerRecyclerView shimmerRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach);
        slide = (SliderLayout) findViewById(R.id.slider);

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("pic1", R.drawable.pic1);
        file_maps.put("pic2", R.drawable.pic2);
        file_maps.put("pic3", R.drawable.pic3);
        file_maps.put("pic4", R.drawable.b1);


        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);

            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("These images will be updated soon", name);

            slide.addSlider(textSliderView);
        }

        slide.setPresetTransformer(SliderLayout.Transformer.Accordion);
        slide.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slide.setCustomAnimation(new DescriptionAnimation());
        slide.setDuration(4000);
        slide.addOnPageChangeListener(this);


        shimmerRecycler = (ShimmerRecyclerView) findViewById(R.id.shimmer_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CoachActivityOptionAdapter coachActivityOptionAdapter = new CoachActivityOptionAdapter(this, CoachActivityOptionList.getList());

        shimmerRecycler.setAdapter(coachActivityOptionAdapter);
        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.showShimmerAdapter();


       /* RecyclerView recyclerView=findViewById(R.id.coach_activity_option_recycler);

        CoachActivityOptionAdapter coachActivityOptionAdapter=new CoachActivityOptionAdapter(this, CoachActivityOptionList.getList());
recyclerView.setAdapter(coachActivityOptionAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);*/


        shimmerRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerRecycler.hideShimmerAdapter();
            }
        }, 3000);


    }


    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        slide.stopAutoCycle();
        super.onStop();
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
