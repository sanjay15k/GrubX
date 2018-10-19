package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import nsit.com.canteenapp.CategoryItemsList;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.CanteensCategoryItemsDTO;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GettingCanteenItemsCall extends AsyncTask<Void, Void, Void> {

    private ProgressDialog progressDialog;
    private Context context;
    private Toast toast;
    private LoginDTO loginDTO;
    private static String BASE_URL;
    private ArrayList<CanteensCategoryItemsDTO> canteensCategoryItemsDTOArrayList;
    private String category;

    public GettingCanteenItemsCall(Context context, LoginDTO loginDTO, String canteen, String category) {
        progressDialog = CommonUtilsClass.createProgressDialog(context,"Please wait","Getting items list, please wait");
        this.context = context;
        this.loginDTO = loginDTO;
        this.category = category;
        BASE_URL = "http://app.grubx.in/api/canteen/"+canteen+"/";
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

            }
        });
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Handler handler = new Handler(context.getMainLooper());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ArrayList<CanteensCategoryItemsDTO>> getCanteensCategoryItemsList = apiInterface.getCanteensCategoryItems(category);

        try {
            canteensCategoryItemsDTOArrayList = getCanteensCategoryItemsList.execute().body();
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
        progressDialog.dismiss();
        Intent intent = new Intent(context,CategoryItemsList.class);
        intent.putExtra("Category",category);
        intent.putExtra("LoginDTO",loginDTO);
        intent.putParcelableArrayListExtra("canteenItemsArrayList",canteensCategoryItemsDTOArrayList);
        context.startActivity(intent);
    }
}
