package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import nsit.com.canteenapp.Adapters.MyOrdersAwaitingConfAdapter;
import nsit.com.canteenapp.Adapters.MyOrdersAwaitingPaymentAdapter;
import nsit.com.canteenapp.Adapters.MyOrdersCancelledOrdersAdapter;
import nsit.com.canteenapp.Adapters.MyOrdersInReadyProcessAdapter;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.DTO.UserOrderDTO;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRecentOrdersDetailsCall extends AsyncTask<Void, Void, Void>{

    private Context context;
    private WeakReference<Context> contextReference;

    private UserOrderDTO userOrderDTO;
    private ArrayList<UserOrderDTO.ItemsDTO> itemsDTOArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;

    private RecyclerView awaitingConfirmationRecyclerView;
    private RecyclerView makePaymentRecyclerView;
    private RecyclerView inReadyProcessRecyclerView;
    private RecyclerView cancelledOrdersRecyclerView;

    private MyOrdersAwaitingConfAdapter myOrdersAwaitingConfAdapter;
    private MyOrdersAwaitingPaymentAdapter myOrdersAwaitingPaymentAdapter;
    private MyOrdersInReadyProcessAdapter myOrdersInReadyProcessAdapter;
    private MyOrdersCancelledOrdersAdapter myOrdersCancelledOrdersAdapter;

    private int width;
    ArrayList<UserOrderDTO> awaitingConfOrdersArrayList = new ArrayList<>();
    private ArrayList<UserOrderDTO> makePaymentOrdersArrayList = new ArrayList<>();
    private ArrayList<UserOrderDTO> inReadyProcessOrdersArrayList = new ArrayList<>();
    private ArrayList<UserOrderDTO> cancelledOrdersArrayList = new ArrayList<>();

    private TextView totalPriceTextView;
    private SwipeRefreshLayout swipeRefreshLayoutAwaitingPayment;

    private LoginDTO loginDTO;


    public GetRecentOrdersDetailsCall(RecyclerView awaitingConfirmationRecyclerView, RecyclerView makePaymentRecyclerView, RecyclerView inReadyProcessRecyclerView, RecyclerView cancelledOrdersRecyclerView, TextView totalPriceTextView, SwipeRefreshLayout swipeRefreshLayoutAwaitingPayment,LoginDTO loginDTO, Context context) {
        this.awaitingConfirmationRecyclerView = awaitingConfirmationRecyclerView;
        this.makePaymentRecyclerView = makePaymentRecyclerView;
        this.inReadyProcessRecyclerView = inReadyProcessRecyclerView;
        this.cancelledOrdersRecyclerView = cancelledOrdersRecyclerView;
        this.context = context;
        progressDialog = CommonUtilsClass.createProgressDialog(context,"Getting Your Past Orders","Please Wait");
        this.totalPriceTextView = totalPriceTextView;
        this.swipeRefreshLayoutAwaitingPayment = swipeRefreshLayoutAwaitingPayment;
        contextReference = new WeakReference<>(context);
        this.loginDTO = loginDTO;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        width = CommonUtilsClass.getScreenWidth()/2;
        System.out.println("I m sending request to getOrderssDetails....");
        if (!CommonUtilsClass.isNetworkConnected(context)){
            System.out.println("I m in *******************************");
            final Snackbar snackbar = Snackbar.make(swipeRefreshLayoutAwaitingPayment,"Connect to internet and try again",Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.setActionTextColor(Color.parseColor("#3a6add"));
            snackbar.show();
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://authenticate.grubx.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<JsonArray> call = apiInterface.getMyOrders(loginDTO.getUsername());

        try {

            JsonArray jsonArray = call.execute().body();

            if (jsonArray != null) {
                for (int i=0;i<jsonArray.size();i++){
                    userOrderDTO = new UserOrderDTO();

                    String id = jsonArray.get(i).getAsJsonObject().get("_id").toString();
                    String canteenName = jsonArray.get(i).getAsJsonObject().get("canteen").toString();
                    String customerName = jsonArray.get(i).getAsJsonObject().get("cust_name").toString();
                    String phoneNumber = jsonArray.get(i).getAsJsonObject().get("contact").toString();
                    String totalPrice = jsonArray.get(i).getAsJsonObject().get("totals").toString();
                    String deliveryTime = jsonArray.get(i).getAsJsonObject().get("delivery_time").toString();
                    String status = jsonArray.get(i).getAsJsonObject().get("status").toString();

                    JsonArray jsonArray1 = jsonArray.get(i).getAsJsonObject().get("items").getAsJsonArray();


                    System.out.println("2*** "+totalPrice);

                    StringBuilder itemsName = new StringBuilder("");
                    int itemsPrice=0;

                    for (int j=0;j<jsonArray1.size();j++){

                        String itemName = jsonArray1.get(j).getAsJsonObject().get("name").toString();
                        String itemQty = jsonArray1.get(j).getAsJsonObject().get("qty").toString();
                        String itemPrice = jsonArray1.get(j).getAsJsonObject().get("price").toString();

                        if (j != 0){
                            itemsName.append("\n");
                        }
                        itemsName.append(itemQty.replaceAll("^\"|\"$", ""));
                        itemsName.append(" x ");
                        itemsName.append(itemName.replaceAll("^\"|\"$", ""));

                        itemsPrice += Integer.parseInt(itemPrice)*Integer.parseInt(itemQty.replaceAll("^\"|\"$", ""));

                    }

                    userOrderDTO.setItemsName(itemsName.toString());
                    userOrderDTO.setItemsTotals(String.valueOf(itemsPrice));


                    userOrderDTO.setId(id);
                    userOrderDTO.setCanteen(canteenName);
                    userOrderDTO.setCustomerName(customerName);
                    userOrderDTO.setPhoneNumber(phoneNumber);
                    userOrderDTO.setTotalPrice(totalPrice);
                    userOrderDTO.setDeliveryTime(deliveryTime);
                    userOrderDTO.setStatus(status);
                    userOrderDTO.setItemsDTOArrayList(itemsDTOArrayList);

                    if(status.contentEquals("0")){
                        System.out.println("3*** "+userOrderDTO.getTotalPrice());
                        awaitingConfOrdersArrayList.add(userOrderDTO);
                    }
                    else if (status.contentEquals("1")){
                        makePaymentOrdersArrayList.add(userOrderDTO);
                    }
                    else if (status.contentEquals("2")){
                        inReadyProcessOrdersArrayList.add(userOrderDTO);
                    }
                    else if (status.contentEquals("-1")){
                        cancelledOrdersArrayList.add(userOrderDTO);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        System.out.println("awaitingConfOrdersArrayList SIZE is : "+awaitingConfOrdersArrayList.size());
        System.out.println("makePaymentOrdersArrayList SIZE is : "+makePaymentOrdersArrayList.size());
        System.out.println("inReadyProcessOrdersArrayList SIZE is : "+inReadyProcessOrdersArrayList.size());
        System.out.println("cancelledOrdersArrayList SIZE is : "+cancelledOrdersArrayList.size());

        LinearLayout awaitingConfirmationLinearLayout = null;
        LinearLayout awaitingPaymentLinearLayout = null;
        LinearLayout inReadyProcessLinearLayout = null;
        LinearLayout cancelledOrdersLinearLayout = null;
        LinearLayout payNowAwaitingPaymentLayout = null;

        AppCompatActivity context = (AppCompatActivity) contextReference.get();

        if (awaitingConfOrdersArrayList.size() == 0){
            if (context != null){
                awaitingConfirmationLinearLayout = context.findViewById(R.id.awaitingConfirmationLinearLayout);
                awaitingConfirmationLinearLayout.removeViewAt(1);
                awaitingConfirmationLinearLayout.addView(emptyList("No Order remaining to confirm!"));
            }
            System.out.println("Size is : "+awaitingConfOrdersArrayList.size());
        }
        else{
            myOrdersAwaitingConfAdapter = new MyOrdersAwaitingConfAdapter(awaitingConfOrdersArrayList,context);
            awaitingConfirmationRecyclerView.setAdapter(myOrdersAwaitingConfAdapter);
            myOrdersAwaitingConfAdapter.notifyDataSetChanged();
        }

        if (makePaymentOrdersArrayList.size() == 0){
            if (context != null){
                awaitingPaymentLinearLayout = context.findViewById(R.id.awaitingPaymentLinearLayout);
                payNowAwaitingPaymentLayout = context.findViewById(R.id.payNowAwaitingPaymentLayout);

                LinearLayout.LayoutParams awaitingPaymentRootLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                awaitingPaymentLinearLayout.setLayoutParams(awaitingPaymentRootLayoutParams);
                awaitingPaymentLinearLayout.removeViewAt(0);
                awaitingPaymentLinearLayout.addView(emptyList("No Payment Remaining"));
                payNowAwaitingPaymentLayout.removeAllViews();
            }
        }
        else{
            myOrdersAwaitingPaymentAdapter = new MyOrdersAwaitingPaymentAdapter(makePaymentOrdersArrayList,totalPriceTextView,swipeRefreshLayoutAwaitingPayment,context,width);
            makePaymentRecyclerView.setAdapter(myOrdersAwaitingPaymentAdapter);
            myOrdersAwaitingPaymentAdapter.notifyDataSetChanged();
        }

        if (inReadyProcessOrdersArrayList.size() == 0){
            if (context != null){
                inReadyProcessLinearLayout = context.findViewById(R.id.inReadyProcessLinearLayout);
                inReadyProcessLinearLayout.removeViewAt(1);
                inReadyProcessLinearLayout.addView(emptyList("No Food in Process"));
            }
        }
        else{
            myOrdersInReadyProcessAdapter = new MyOrdersInReadyProcessAdapter(inReadyProcessOrdersArrayList,context);
            inReadyProcessRecyclerView.setAdapter(myOrdersInReadyProcessAdapter);
            myOrdersInReadyProcessAdapter.notifyDataSetChanged();
        }

        if (cancelledOrdersArrayList.size() == 0){
            if (context != null){
                cancelledOrdersLinearLayout = context.findViewById(R.id.cancelledOrdersLinearLayout);
                cancelledOrdersLinearLayout.removeViewAt(1);
                cancelledOrdersLinearLayout.addView(emptyList("No Items List Cancelled"));
            }
        }
        else{
            myOrdersCancelledOrdersAdapter = new MyOrdersCancelledOrdersAdapter(cancelledOrdersArrayList,context);
            cancelledOrdersRecyclerView.setAdapter(myOrdersCancelledOrdersAdapter);
            myOrdersCancelledOrdersAdapter.notifyDataSetChanged();
        }

        System.out.println("I m done with sending request to getOrdersDetails....");
        progressDialog.dismiss();
    }

    private TextView emptyList(String text){
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(Color.RED);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50,10,0,10);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

}
