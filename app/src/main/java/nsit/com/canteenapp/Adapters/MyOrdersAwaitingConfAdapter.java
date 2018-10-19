package nsit.com.canteenapp.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

public class MyOrdersAwaitingConfAdapter extends RecyclerView.Adapter<MyOrdersAwaitingConfAdapter.ViewHolder>{

    ArrayList<UserOrderDTO> userOrderDTOArrayList;
    private Context context;

    public MyOrdersAwaitingConfAdapter(ArrayList<UserOrderDTO> userOrderDTOArrayList, Context context) {
        this.userOrderDTOArrayList = userOrderDTOArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrdersAwaitingConfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.awaiting_confirmation_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAwaitingConfAdapter.ViewHolder holder, int position) {

        System.out.println("Awaiting Conf Size is : "+userOrderDTOArrayList.size());

        if (userOrderDTOArrayList.size() != 0 ){

            System.out.println("Size is : "+userOrderDTOArrayList.size());
            final UserOrderDTO userOrderDTO = userOrderDTOArrayList.get(position);

            CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.mini_zayca_canteens_background, holder.canteenImageView, context);
            holder.itemsTextView.setText(userOrderDTO.getItemsName().replaceAll("^\"|\"$", ""));
            holder.canteenNameTextView.setText(userOrderDTO.getCanteen().replaceAll("^\"|\"$", ""));
            holder.itemdTotalTextView.setText(userOrderDTO.getItemsTotals());
            holder.itemDeliveryTimeTextView.setText(userOrderDTO.getDeliveryTime().replaceAll("^\"|\"$", ""));

            final String id = userOrderDTO.getId();
            holder.onConfItemRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("I m clicked");
                    new CancelMyOrder().execute(id);
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

        ImageView canteenImageView;
        TextView itemsTextView;
        TextView canteenNameTextView;
        TextView itemdTotalTextView;
        TextView itemDeliveryTimeTextView;
        Button onConfItemRemoveBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            canteenImageView = itemView.findViewById(R.id.canteenImageView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            itemdTotalTextView = itemView.findViewById(R.id.itemdTotalTextView);
            canteenNameTextView = itemView.findViewById(R.id.canteenNameTextView);
            itemDeliveryTimeTextView = itemView.findViewById(R.id.itemDeliveryTimeTextView);
            onConfItemRemoveBtn = itemView.findViewById(R.id.onConfItemRemoveBtn);
        }
    }

    public class CancelMyOrder extends AsyncTask<String, Void, Void> {

        private JsonObject cancelJsonObject;
        private WeakReference<Context> contextReference = new WeakReference<>(context);
        private ProgressDialog cancelItemProgressDialogue = CommonUtilsClass.createProgressDialog(context,"","Please wait while we cancel your order.");

        @Override
        protected void onPreExecute() {
            cancelItemProgressDialogue.show();
        }

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
            cancelItemProgressDialogue.dismiss();
            String status= " ";
            if (cancelJsonObject != null){
                status = cancelJsonObject.get("status").getAsString();
            }
            if (status.contentEquals("-1")){
                ((MyRecentOrders)context).onRefresh();
            }
            else{
                AppCompatActivity context = (AppCompatActivity) contextReference.get();
                Snackbar.make(context.findViewById(R.id.swipeRefreshLayoutRecentOrders),"Some problem occurred, Please try cancelling again!",Snackbar.LENGTH_SHORT).show();
            }

        }
    }

}
