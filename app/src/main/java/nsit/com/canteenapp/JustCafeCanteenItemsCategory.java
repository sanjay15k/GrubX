package nsit.com.canteenapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Objects;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.GettingCanteenItemsCall;

public class JustCafeCanteenItemsCategory extends AppCompatActivity {

    private RelativeLayout justCafeSnacksRelativeLayout;
    private RelativeLayout justCafeChineeseRelativeLayout;
    private RelativeLayout justCafeIndianRelativeLayout;
    private LinearLayout justCafeSnacksLinearLayout;
    private LinearLayout justCafeChineeseLinearLayout;
    private LinearLayout justCafeIndianLinearLayout;
    private Intent intent;
    private LoginDTO loginDTO;
    private static final int CART_ID =30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_cafe_canteen_items_category);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginDTO = getIntent().getParcelableExtra("LoginDTO");

        justCafeSnacksRelativeLayout = findViewById(R.id.justCafeSnacksRelativeLayout);
        justCafeChineeseRelativeLayout = findViewById(R.id.justCafeChineeseRelativeLayout);
        justCafeIndianRelativeLayout = findViewById(R.id.justCafeIndianRelativeLayout);

        justCafeSnacksLinearLayout = findViewById(R.id.justCafeSnacksLinearLayout);
        justCafeChineeseLinearLayout = findViewById(R.id.justCafeChineeseLinearLayout);
        justCafeIndianLinearLayout = findViewById(R.id.justCafeIndianLinearLayout);

        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mc_cain_snacks_background, justCafeSnacksRelativeLayout,this);
        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mc_cain_drinks_background, justCafeChineeseRelativeLayout,this);
        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mc_cain_south_indian_background, justCafeIndianRelativeLayout,this);

        justCafeSnacksLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtilsClass.isNetworkConnected(JustCafeCanteenItemsCategory.this)){
                    GettingCanteenItemsCall gettingCanteenItemsCall = new GettingCanteenItemsCall(JustCafeCanteenItemsCategory.this,loginDTO,"JC","snacks");
                    gettingCanteenItemsCall.execute();
                }
                else{
                    Snackbar.make(findViewById(R.id.parentLayout),"No Internet Connection Available",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        justCafeChineeseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtilsClass.isNetworkConnected(JustCafeCanteenItemsCategory.this)){
                    GettingCanteenItemsCall gettingCanteenItemsCall = new GettingCanteenItemsCall(JustCafeCanteenItemsCategory.this,loginDTO,"JC","chineese");
                    gettingCanteenItemsCall.execute();
                }
                else{
                    Snackbar.make(findViewById(R.id.parentLayout),"No Internet Connection Available",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        justCafeIndianLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtilsClass.isNetworkConnected(JustCafeCanteenItemsCategory.this)){
                    GettingCanteenItemsCall gettingCanteenItemsCall = new GettingCanteenItemsCall(JustCafeCanteenItemsCategory.this,loginDTO,"JC","indian");
                    gettingCanteenItemsCall.execute();
                }
                else{
                    Snackbar.make(findViewById(R.id.parentLayout),"No Internet Connection Available",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(1,CART_ID,100,"Cart").setIcon(R.drawable.cart_top_bar_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case CART_ID :
                Intent intent = new Intent(this,MyCart.class);
                intent.putExtra("LoginDTO",loginDTO);
                startActivity(intent);
                break;
            case android.R.id.home :
                System.out.println("I m home up");
                onBackPressed();
                break;
        }
        return true;
    }

}
