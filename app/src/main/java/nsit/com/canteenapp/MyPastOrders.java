package nsit.com.canteenapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.Objects;

import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.GetPastOrdersDetailsCall;

public class MyPastOrders extends AppCompatActivity {

    private RecyclerView pastOrdersRecyclerView;
    private LoginDTO loginDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_past_orders);

        Intent intent = getIntent();
        loginDTO = intent.getParcelableExtra("LoginDTO");

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pastOrdersRecyclerView = findViewById(R.id.pastOrdersRecyclerView);

        LinearLayoutManager pastOrderLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);

        pastOrdersRecyclerView.setLayoutManager(pastOrderLayoutManager);

        GetPastOrdersDetailsCall getPastOrdersDetailsCall = new GetPastOrdersDetailsCall(pastOrdersRecyclerView,loginDTO,this);
        getPastOrdersDetailsCall.execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home :
                onBackPressed();
                break;
        }
        return true;
    }
}
