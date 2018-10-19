package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;

import java.io.IOException;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.DTO.MyCartDTO;
import nsit.com.canteenapp.MyRecentOrders;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by starhawk on 06/08/18.
 */

public class AddToCartCall extends AsyncTask<Void, Void, Void>{

    private MyCartDTO myCartDTO;
    private ProgressDialog progressDialog;
    private Context context;
    private RelativeLayout myCartRelativeLayout;
    private LoginDTO loginDTO;

    public AddToCartCall(MyCartDTO myCartDTO, Context context, RelativeLayout myCartRelativeLayout, LoginDTO loginDTO){
        this.myCartDTO = myCartDTO;
        this.context = context;
        progressDialog = CommonUtilsClass.createProgressDialog(context,"Placing Order...","Just wait a moment!");
        this.myCartRelativeLayout = myCartRelativeLayout;
        this.loginDTO = loginDTO;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://authenticate.grubx.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.placeOrder(myCartDTO);
        System.out.println("1 "+myCartDTO.getTotalPrice());

        try {

            call.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        final Snackbar snackbar = Snackbar.make(myCartRelativeLayout,"Order has been placed.",Snackbar.LENGTH_LONG);
        snackbar.setAction("My Orders", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyRecentOrders.class);
                intent.putExtra("LoginDTO",loginDTO);
                context.startActivity(intent);
            }
        });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }
}
