package nsit.com.canteenapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    public static boolean isShown = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("i m Splash");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (isShown){
            Intent intent = new Intent(SplashScreen.this,LogInScreen.class);
            startActivity(intent);
            finish();
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isShown = true;
                    Intent intent = new Intent(SplashScreen.this,LogInScreen.class);
                    startActivity(intent);
                    finish();
                }
            },1500);
        }



    }
}
