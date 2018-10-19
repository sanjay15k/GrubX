package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.UserOrderDTO;
import nsit.com.canteenapp.R;

public class MyOrdersCancelledOrdersAdapter extends RecyclerView.Adapter<MyOrdersCancelledOrdersAdapter.ViewHolder> {

    private ArrayList<UserOrderDTO> userOrderDTOArrayList;
    private Context context;

    public MyOrdersCancelledOrdersAdapter(ArrayList<UserOrderDTO> userOrderDTOArrayList, Context context) {
        this.userOrderDTOArrayList = userOrderDTOArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrdersCancelledOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cancelled_orders_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersCancelledOrdersAdapter.ViewHolder holder, int position) {

        if (userOrderDTOArrayList.size() != 0 ){

            UserOrderDTO userOrderDTO = userOrderDTOArrayList.get(userOrderDTOArrayList.size()-position-1);

            CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.burger_category_items,holder.canteenImageView,context);
            holder.itemsTextView.setText(userOrderDTO.getItemsName().replaceAll("^\"|\"$", ""));
            holder.canteenNameTextView.setText(userOrderDTO.getCanteen().replaceAll("^\"|\"$", ""));
        }
    }

    @Override
    public int getItemCount() {
        if (userOrderDTOArrayList.size() > 8){
            return 7;
        }
        else{
            return userOrderDTOArrayList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView canteenImageView;
        TextView itemsTextView;
        TextView canteenNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            canteenImageView = itemView.findViewById(R.id.canteenImageView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            canteenNameTextView = itemView.findViewById(R.id.canteenNameTextView);

        }
    }
}
