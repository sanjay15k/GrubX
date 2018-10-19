package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import nsit.com.canteenapp.Adapters.OffersAdapter;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.DTO.OffersDTO;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetOffersCall extends AsyncTask<Void, Void, Void>{

    private ProgressDialog progressDialog;
    private Context context;
    private ArrayList<OffersDTO> offersDTOArrayList;
    private RecyclerView offersRecyclerView;
    private Toast toast;
    private NestedScrollView nestedScrollViewOffers;
    private LoginDTO loginDTO;

    public GetOffersCall(Context context, RecyclerView offersRecyclerView, NestedScrollView nestedScrollViewOffers, LoginDTO loginDTO) {
        progressDialog = CommonUtilsClass.createProgressDialog(context,"Please wait","Getting Available Offers");
        this.context = context;
        offersDTOArrayList = new ArrayList<>();
        this.offersRecyclerView = offersRecyclerView;
        this.nestedScrollViewOffers = nestedScrollViewOffers;
        this.loginDTO = loginDTO;
    }

    private void createRefreshBtn(){
        final Button refreshBtn = new Button(context);
        refreshBtn.setText("Refresh");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        refreshBtn.setLayoutParams(params);
        refreshBtn.setBackgroundColor(Color.parseColor("#455A64"));
        refreshBtn.setTextColor(Color.WHITE);
        params.addRule(RelativeLayout.BELOW, R.id.tapToConnectTextView);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        refreshBtn.setPadding(90,10,90,10);
        params.setMargins(0,20,0,0);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetOffersCall homeFeaturedItemsCall = new GetOffersCall(context,offersRecyclerView,nestedScrollViewOffers, loginDTO);
                homeFeaturedItemsCall.execute();
                nestedScrollViewOffers.removeView(refreshBtn);
            }
        });
        nestedScrollViewOffers.addView(refreshBtn);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Handler handler = new Handler(context.getMainLooper());
        Gson gson = new Gson();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.grubx.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<JsonArray> getOffersList = apiInterface.getOffers();

        try {
            JsonArray offersArrayList = getOffersList.execute().body();
            offersDTOArrayList = gson.fromJson(offersArrayList,new TypeToken<ArrayList<OffersDTO>>() {}.getType());
        }
        catch (SocketException e3){
            e3.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(context,"Internet not working. Please choose any other network",Toast.LENGTH_SHORT);
                    toast.cancel();
                    toast.show();
                    createRefreshBtn();
                }
            });
        }
        catch (SocketTimeoutException e2){
            e2.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(context,"Timeout occurred. Please try again",Toast.LENGTH_SHORT);
                    toast.cancel();
                    toast.show();
                    createRefreshBtn();
                }
            });
        }
        catch (UnknownHostException | NullPointerException e1){
            e1.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(context,"Error occured while getting details. Please try again",Toast.LENGTH_SHORT);
                    toast.cancel();
                    toast.show();
                    createRefreshBtn();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        OffersAdapter offersAdapter = new OffersAdapter(offersDTOArrayList,context,loginDTO);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        offersRecyclerView.setLayoutManager(linearLayoutManager);
        offersRecyclerView.setAdapter(offersAdapter);
        progressDialog.dismiss();
    }
}