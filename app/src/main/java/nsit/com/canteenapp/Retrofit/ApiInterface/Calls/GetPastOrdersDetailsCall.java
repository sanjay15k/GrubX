package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.UnknownHostException;
import java.util.ArrayList;
import nsit.com.canteenapp.Adapters.MyPastOrdersAdapter;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.DTO.UserOrderDTO;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetPastOrdersDetailsCall extends AsyncTask<Void, Void, Void>{

    private Context context;
    private UserOrderDTO userOrderDTO;
    private ArrayList<UserOrderDTO.ItemsDTO> itemsDTOArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private JsonArray jsonArray;
    RecyclerView pastOrdersRecyclerView;
    MyPastOrdersAdapter myPastOrdersAdapter;
    ArrayList<UserOrderDTO> pastOrdersArrayList = new ArrayList<>();

    private WeakReference<Context> contextReference;

    private LoginDTO loginDTO;

    public GetPastOrdersDetailsCall(RecyclerView pastOrdersRecyclerView,LoginDTO loginDTO, Context context) {
        this.pastOrdersRecyclerView = pastOrdersRecyclerView;
        this.context = context;
        progressDialog = CommonUtilsClass.createProgressDialog(context,"Getting Your Past Orders","Please Wait");
        this.loginDTO = loginDTO;
        contextReference = new WeakReference<>(context);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        System.out.println("I m sending request to getOrderssDetails....");
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

            jsonArray = call.execute().body();

            System.out.println("JsonArray of PastOrders is : "+jsonArray);

            if (jsonArray != null ) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    userOrderDTO = new UserOrderDTO();

                    String id = jsonArray.get(i).getAsJsonObject().get("_id").toString();
                    String canteenName = jsonArray.get(i).getAsJsonObject().get("canteen").toString();
                    String customerName = jsonArray.get(i).getAsJsonObject().get("cust_name").toString();
                    String phoneNumber = jsonArray.get(i).getAsJsonObject().get("contact").toString();
                    String totalPrice = jsonArray.get(i).getAsJsonObject().get("totals").toString();
                    String deliveryTime = jsonArray.get(i).getAsJsonObject().get("delivery_time").toString();
                    String status = jsonArray.get(i).getAsJsonObject().get("status").toString();

                    JsonArray jsonArray1 = jsonArray.get(i).getAsJsonObject().get("items").getAsJsonArray();

                    StringBuilder itemsName = new StringBuilder("");
                    int itemsPrice = 0;

                    for (int j = 0; j < jsonArray1.size(); j++) {

                        String itemName = jsonArray1.get(j).getAsJsonObject().get("name").toString();
                        String itemQty = jsonArray1.get(j).getAsJsonObject().get("qty").toString();
                        String itemPrice = jsonArray1.get(j).getAsJsonObject().get("price").toString();

                        if (j != 0) {
                            itemsName.append("\n");
                        }
                        itemsName.append(itemQty.replaceAll("^\"|\"$", ""));
                        itemsName.append(" x ");
                        itemsName.append(itemName.replaceAll("^\"|\"$", ""));

                        itemsPrice += Integer.parseInt(itemPrice.replaceAll("^\"|\"$", ""));

                    }

                    System.out.println("ItemsName is1 : " + itemsName);
                    System.out.println("ItemsPrice is1 : " + itemsPrice);

                    userOrderDTO.setItemsName(itemsName.toString());
                    userOrderDTO.setItemsTotals(String.valueOf(itemsPrice));

                    //                    System.out.println("1 "+itemsDTOArrayList.get(0).getName());
                    //                    System.out.println("2 " +itemsDTOArrayList.get(1).getName());


                    userOrderDTO.setId(id);
                    userOrderDTO.setCanteen(canteenName);
                    userOrderDTO.setCustomerName(customerName);
                    userOrderDTO.setPhoneNumber(phoneNumber);
                    userOrderDTO.setTotalPrice(totalPrice);
                    userOrderDTO.setDeliveryTime(deliveryTime);
                    userOrderDTO.setStatus(status);
                    userOrderDTO.setItemsDTOArrayList(itemsDTOArrayList);

                    if (status.contentEquals("100")) {
                        pastOrdersArrayList.add(userOrderDTO);
                    }

                }
            }
        }
        catch (UnknownHostException e1){
            Toast.makeText(context,"Server Problem, Please try again",Toast.LENGTH_SHORT).show();
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        System.out.println("Json Array is : "+jsonArray);
        if (jsonArray != null){
            if (pastOrdersArrayList.size() ==0){

                AppCompatActivity context = (AppCompatActivity) contextReference.get();
                RelativeLayout pastOrderRootLayout = context.findViewById(R.id.pastOrderRootLayout);

                pastOrderRootLayout.addView(createNoPastOrdersTextView());
            }
            else{
                myPastOrdersAdapter = new MyPastOrdersAdapter(pastOrdersArrayList,context);
                pastOrdersRecyclerView.setAdapter(myPastOrdersAdapter);
            }
        }
        System.out.println("I m done with sending request to getOrdersDetails....");
        progressDialog.dismiss();
    }

    private TextView createNoPastOrdersTextView(){
        TextView textView = new TextView(context);
        textView.setText("No Orders to show");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        textView.setTextColor(Color.parseColor("#78909C"));
        textView.setLetterSpacing(0.1f);
        textView.setPadding(30,30,30,30);

        GradientDrawable gd = new GradientDrawable();
        gd.setStroke(2,Color.parseColor("#00B0FF"));
        textView.setBackground(gd);

        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);

        textView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)textView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);textView.setLayoutParams(layoutParams);
        return textView;
    }

}
