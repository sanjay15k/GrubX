package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.UserOrderDTO;
import nsit.com.canteenapp.MyRecentOrders;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyOrdersAwaitingPaymentAdapter extends RecyclerView.Adapter<MyOrdersAwaitingPaymentAdapter.ViewHolder> {

    private ArrayList<UserOrderDTO> userOrderDTOArrayList;
    private ArrayList<UserOrderDTO.ItemsDTO> itemsDTOArrayList;
    private Context context;
    private int width;
    private int defaultHeight;
    private SwipeRefreshLayout swipeRefreshLayoutAwaitingPayment;
    private TextView totalPriceTextView;
    private int totalPrice;

    public MyOrdersAwaitingPaymentAdapter(ArrayList<UserOrderDTO> userOrderDTOArrayList,TextView totalPriceTextView,SwipeRefreshLayout swipeRefreshLayoutAwaitingPayment, Context context, int width) {
        this.userOrderDTOArrayList = userOrderDTOArrayList;
        for (int i=0; i< userOrderDTOArrayList.size(); i++){
            this.itemsDTOArrayList = userOrderDTOArrayList.get(i).getItemsDTOArrayList();
        }
        this.context = context;
        this.width = width;
        this.totalPriceTextView = totalPriceTextView;
        this.swipeRefreshLayoutAwaitingPayment = swipeRefreshLayoutAwaitingPayment;
    }

    @NonNull
    @Override
    public MyOrdersAwaitingPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.awaiting_payment_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAwaitingPaymentAdapter.ViewHolder holder, int position) {

        if (userOrderDTOArrayList.size() != 0 ){
            totalPrice = 0;
            for (int i=0; i<userOrderDTOArrayList.size(); i++){
                System.out.println("Count "+i+" "+totalPrice);
                totalPrice += Integer.parseInt(userOrderDTOArrayList.get(i).getItemsTotals());
            }
            totalPriceTextView.setText(String.valueOf(totalPrice));
            final UserOrderDTO userOrderDTO = userOrderDTOArrayList.get(position);

            defaultHeight = holder.awaitingPaymentCardView.getLayoutParams().height;
            holder.awaitingPaymentCardView.setLayoutParams(new LinearLayout.LayoutParams(width,defaultHeight));

            if (position%2 ==0){
                holder.rootLinearLayout.setBackgroundResource(R.drawable.awaiting_payment_cardview_border);
            }

            CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.my_orders_image_layer_design,holder.canteenImageView,context);

                holder.itemsTextView.setText(userOrderDTO.getItemsName().replaceAll("^\"|\"$", ""));
                holder.canteenNameTextView.setText(userOrderDTO.getCanteen().replaceAll("^\"|\"$", ""));
                holder.itemdTotalTextView.setText(userOrderDTO.getItemsTotals());
                holder.itemDeliveryTimeTextView.setText(userOrderDTO.getDeliveryTime().replaceAll("^\"|\"$", ""));

            holder.onAwaitingPaymentItemRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CancelMyOrder().execute(userOrderDTO.getId());
                }
            });

        }
        else {
            System.out.println("I m Empty Cancelled Orders");
        }

    }

    @Override
    public int getItemCount() {
        return userOrderDTOArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView awaitingPaymentCardView;
        ImageView canteenImageView;
        TextView itemsTextView;
        TextView canteenNameTextView;
        TextView itemdTotalTextView;
        TextView itemDeliveryTimeTextView;
        Button onAwaitingPaymentItemRemoveBtn;
        LinearLayout rootLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            awaitingPaymentCardView = itemView.findViewById(R.id.awaitingPaymentCardView);
            canteenImageView = itemView.findViewById(R.id.canteenImageView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            itemdTotalTextView = itemView.findViewById(R.id.itemdTotalTextView);
            canteenNameTextView = itemView.findViewById(R.id.canteenNameTextView);
            itemDeliveryTimeTextView = itemView.findViewById(R.id.itemDeliveryTimeTextView);
            onAwaitingPaymentItemRemoveBtn = itemView.findViewById(R.id.onAwaitingPaymentItemRemoveBtn);
            rootLinearLayout = itemView.findViewById(R.id.rootLinearLayout);
        }
    }

    public class CancelMyOrder extends AsyncTask<String, Void, Void> {

        private JsonObject cancelJsonObject;
        private WeakReference<Context> contextReference = new WeakReference<>(context);

        @Override
        protected Void doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://vendor.grubx.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<JsonObject> call = apiInterface.cancelMyOrder(strings[0].replaceAll("^\"|\"$", ""));

            try {

                cancelJsonObject = call.execute().body();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (cancelJsonObject.get("status").getAsString().contentEquals("-1")){
                ((MyRecentOrders)context).onRefresh();
            }
            else{
                AppCompatActivity context = (AppCompatActivity) contextReference.get();
                Snackbar.make(context.findViewById(R.id.swipeRefreshLayoutRecentOrders),"Some problem occurred, Please try cancelling again!",Snackbar.LENGTH_SHORT).show();
            }

        }
    }

}
