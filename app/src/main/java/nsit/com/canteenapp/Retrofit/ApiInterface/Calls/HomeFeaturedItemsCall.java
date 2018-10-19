package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import nsit.com.canteenapp.Adapters.HomeFeaturedItemsAdapter;
import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.DTO.HomeFeaturedItemsDTO;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by starhawk on 02/08/18.
 */

public class HomeFeaturedItemsCall extends AsyncTask<Void,Void,Void>{

    private RecyclerView homeItemsRecyclerView;
    private Context context;
    private ProgressDialog progressDialog;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<HomeFeaturedItemsDTO> itemsArrayList;
    private ArrayList<HomeFeaturedItemsDTO> featuredItemsArrayList;
    private ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList;
    private RelativeLayout homeRelativeLayout;
    private HomeFeaturedItemsAdapter homeFeaturedItemsAdapter;
    private Toast toast;

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
                HomeFeaturedItemsCall homeFeaturedItemsCall = new HomeFeaturedItemsCall(homeItemsRecyclerView,context,progressDialog,favouritesItemDTOArrayList,homeRelativeLayout);
                homeFeaturedItemsCall.execute();
                homeRelativeLayout.removeView(refreshBtn);
            }
        });
        homeRelativeLayout.addView(refreshBtn);
    }

    public HomeFeaturedItemsCall(RecyclerView homeItemsRecyclerView, Context context, ProgressDialog progressDialog, ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList, RelativeLayout homeRelativeLayout){
        this.homeItemsRecyclerView =homeItemsRecyclerView;
        this.context = context;
        this.progressDialog = progressDialog;
        this.favouritesItemDTOArrayList = favouritesItemDTOArrayList;
        this.homeRelativeLayout = homeRelativeLayout;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Handler handler = new Handler(context.getMainLooper());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://app.grubx.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ArrayList<HomeFeaturedItemsDTO>> call = apiInterface.getFeaturedItems();

        try {
            itemsArrayList = call.execute().body();
            featuredItemsArrayList = new ArrayList<>();
            if (itemsArrayList.size() != 0) {

                for (int i = 0; i < itemsArrayList.size(); i++) {
                    if (itemsArrayList.get(i).getIsFeatured()) {
                        featuredItemsArrayList.add(itemsArrayList.get(i));
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (itemsArrayList != null) {
                            layoutManager = new LinearLayoutManager(context);
                            homeFeaturedItemsAdapter = new HomeFeaturedItemsAdapter(featuredItemsArrayList, context, favouritesItemDTOArrayList, homeRelativeLayout);
                            homeItemsRecyclerView.setLayoutManager(layoutManager);
                            homeItemsRecyclerView.setAdapter(homeFeaturedItemsAdapter);
                        }
                    }
                });
            }
        }
        catch (SocketException e3){
            e3.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(context,"Internet not working. Please choose any other network",Toast.LENGTH_SHORT);
                    if (toast == null) {
                        toast.show();
                    }
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
                    if (toast == null) {
                        toast.show();
                    }
                    createRefreshBtn();
                }
            });
        }
        catch (UnknownHostException e1){
            e1.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(context,"Error occured while getting details. Please try again",Toast.LENGTH_SHORT);
                    if (toast == null) {
                        toast.show();
                    }
                    createRefreshBtn();
                }
            });
        }
        catch (NullPointerException e3){
            e3.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(context,"Error occured while getting details. Please try again",Toast.LENGTH_SHORT);
                    if (toast == null) {
                        toast.show();
                    }
                    createRefreshBtn();
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
    }

}
