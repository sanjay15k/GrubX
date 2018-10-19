package nsit.com.canteenapp;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import nsit.com.canteenapp.Adapters.OurProfileTeamViewPagerAdapter;
import nsit.com.canteenapp.DTO.OurTeamProfileDTO;

public class OurTeam extends AppCompatActivity {

    ViewPager viewPager;
    ArrayList<OurTeamProfileDTO> ourTeamProfileDTOArrayList;

    public static strictfp int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static strictfp int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_team);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#26a0da"));

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Our Team");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#26a0da")));

        ourTeamProfileDTOArrayList = new ArrayList<>();

        ourTeamProfileDTOArrayList.add(new OurTeamProfileDTO(R.drawable.profile,"Sudhanshu Sah","Head Graphic Designer","https://www.facebook.com/llThis.Is.Sidll","sudhanshu@grubx.in","7042029464","llThis.Is.Sidl"));
        ourTeamProfileDTOArrayList.add(new OurTeamProfileDTO(R.drawable.kartik_profle_pic,"Kartik Sharma","Chief Marketing Officer","https://m.facebook.com/kartik.verma686","kartik@grubx.in","8860240217","llThis.Is.Sidl"));
        ourTeamProfileDTOArrayList.add(new OurTeamProfileDTO(R.drawable.achintya_profile_image,"Achintya Shankhdhar","Chief Executive Officer","https://m.facebook.com/achintya.shankhdhar","achintya@grubx.in","8130969707","llThis.Is.Sidl"));
        ourTeamProfileDTOArrayList.add(new OurTeamProfileDTO(R.drawable.profile,"Sanjay Kumar","Vice President Technology","https://www.facebook.com/sanjay98k","sanjay@grubx.in","7678366446","llThis.Is.Sidl"));
        ourTeamProfileDTOArrayList.add(new OurTeamProfileDTO(R.drawable.devansh_profile_image,"Devansh Batra","Chief Technological Officer","https://www.facebook.com/devansh.batra.75","devanshbatra04@grubx.in","8930485111","llThis.Is.Sidl"));
        ourTeamProfileDTOArrayList.add(new OurTeamProfileDTO(R.drawable.arnav_profile_image,"Arnav Singhal","Chief Operating Officer","https://www.facebook.com/Arnav.singhal.807","arnav@grubx.in","9599139794","llThis.Is.Sidl"));
        ourTeamProfileDTOArrayList.add(new OurTeamProfileDTO(R.drawable.profile,"Syed Masoom Zaki","Content Head","https://www.facebook.com/masoom.9.zaki","Syed@grubx.in","9560465201","llThis.Is.Sidl"));


        viewPager = findViewById(R.id.viewPager);

        System.out.println(getScreenWidth());

        int paddingTopAndBottom = (int) (getScreenHeight()*0.05208);
        int paddingLeftAndRight = (int) (getScreenWidth()*0.07407);

        viewPager.setClipToPadding(false);
        viewPager.setPadding(paddingLeftAndRight,paddingTopAndBottom,paddingLeftAndRight,paddingTopAndBottom);
        viewPager.setPageMargin(paddingLeftAndRight/2);

        viewPager.setAdapter(new OurProfileTeamViewPagerAdapter(ourTeamProfileDTOArrayList,this));

        //Calling below method only after setting adapter
        viewPager.setCurrentItem(3);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home : onBackPressed();
            break;
        }
        return true;
    }
}
