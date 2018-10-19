package nsit.com.canteenapp;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;

/**
 * Created by starhawk on 28/07/18.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigation;
    private AlertDialog.Builder alertDailog;
    private LoginDTO loginDTO;

    private void createAlertDailog(){
        alertDailog = new AlertDialog.Builder(this);
        alertDailog.setTitle("Message");
        alertDailog.setCancelable(false);
        alertDailog.setMessage("Are you sure you want to exit?");
        alertDailog.setNegativeButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
            }
        });
        alertDailog.setPositiveButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public LoginDTO accountDetails(){
        return loginDTO;
    }

    @Override
    public void onBackPressed() {
        createAlertDailog();
        alertDailog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent =  getIntent();
        loginDTO = intent.getParcelableExtra("LoginDTO");

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.home);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Window window = getWindow();
        int id = item.getItemId();
        switch (id){
            case R.id.canteens :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new CanteensFragment()).commit();
                CommonUtilsClass.updateStatusBarColor("#FF9800",window);
                break;
            case R.id.offers :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new OffersFragment()).commit();
                CommonUtilsClass.updateStatusBarColor("#4D00B5",window);
                break;
            case R.id.home :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new HomeFragment()).commit();
                CommonUtilsClass.updateStatusBarColor("#ff0000",window);
                break;
            case R.id.favourites :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new FavouritesFragment()).commit();
                CommonUtilsClass.updateStatusBarColor("#388E3C",window);
                break;
            case R.id.account :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new AccountFragment()).commit();
                CommonUtilsClass.updateStatusBarColor("#2196F3",window);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.team :
                startActivity(new Intent(this,OurTeam.class));
                break;
            case R.id.rateUs :
                rateUs();
                break;
            case R.id.otherApps :
                startActivity(new Intent(MainActivity.this,OtherApps.class));
                break;
            case R.id.cart :
                Intent intent = new Intent(this,MyCart.class);
                intent.putExtra("LoginDTO",loginDTO);
                startActivity(intent);
                break;
        }
        return true;
    }

    public void rateUs(){
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

}

