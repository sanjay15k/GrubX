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

public class MyOrdersInReadyProcessAdapter extends RecyclerView.Adapter<MyOrdersInReadyProcessAdapter.ViewHolder> {

    private ArrayList<UserOrderDTO> userOrderDTOArrayList;
    private Context context;

    public MyOrdersInReadyProcessAdapter(ArrayList<UserOrderDTO> userOrderDTOArrayList, Context context) {
        this.userOrderDTOArrayList = userOrderDTOArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrdersInReadyProcessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.in_ready_process_order_single_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersInReadyProcessAdapter.ViewHolder holder, int position) {

        if (userOrderDTOArrayList.size() != 0 ){

            System.out.println("Size is : "+userOrderDTOArrayList.size());
//            UserOrderDTO.ItemsDTO itemDto = itemsDTOArrayList.get(position);
            UserOrderDTO userOrderDTO = userOrderDTOArrayList.get(position);

//            for (int i=0; i<userOrderDTOArrayList.size(); i++){
//

            CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.burger_category_items,holder.canteenImageView,context);
            holder.itemsTextView.setText(userOrderDTO.getItemsName().replaceAll("^\"|\"$", ""));
            holder.canteenNameTextView.setText(userOrderDTO.getCanteen().replaceAll("^\"|\"$", ""));
            holder.itemdTotalTextView.setText(userOrderDTO.getItemsTotals());
            holder.itemDeliveryTimeTextView.setText(userOrderDTO.getDeliveryTime().replaceAll("^\"|\"$", ""));
//            }
        }
        else {
            System.out.println("I m Empty inReadyProcess Orders");
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

        public ViewHolder(View itemView) {
            super(itemView);
            canteenImageView = itemView.findViewById(R.id.canteenImageView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            itemdTotalTextView = itemView.findViewById(R.id.itemdTotalTextView);
            canteenNameTextView = itemView.findViewById(R.id.canteenNameTextView);
            itemDeliveryTimeTextView = itemView.findViewById(R.id.itemDeliveryTimeTextView);

        }
    }
}
