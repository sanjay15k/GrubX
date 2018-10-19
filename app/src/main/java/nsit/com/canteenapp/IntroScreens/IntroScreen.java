package nsit.com.canteenapp.IntroScreens;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import nsit.com.canteenapp.Adapters.IntroScreenFragmentAdapter;
import nsit.com.canteenapp.R;

public class IntroScreen extends AppCompatActivity {
    private ViewPager introViewPager;
    private TextView[] dots;
    private LinearLayout bottomPaginationLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        introViewPager = findViewById(R.id.introViewPager);
        bottomPaginationLayout = findViewById(R.id.bottomPaginationLayout);

        introViewPager.addOnPageChangeListener(pageChangeListener);

        introViewPager.setAdapter(new IntroScreenFragmentAdapter(getSupportFragmentManager()));
        showDots(0);
    }

    public void showDots(int position){

        dots = new TextView[5];
        bottomPaginationLayout.removeAllViews();

        for(int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(45);
            dots[i].setTextColor(Color.WHITE);
            bottomPaginationLayout.addView(dots[i]);

        }

        if(dots.length>0){
            dots[position].setTextColor(Color.parseColor("#1ebaf8"));
        }


    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            showDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
