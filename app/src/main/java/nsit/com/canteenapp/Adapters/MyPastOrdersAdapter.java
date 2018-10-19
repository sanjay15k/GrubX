package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.UserOrderDTO;
import nsit.com.canteenapp.R;

public class MyPastOrdersAdapter extends RecyclerView.Adapter<MyPastOrdersAdapter.ViewHolder> {

    ArrayList<UserOrderDTO> userOrderDTOArrayList;
    private Context context;

    public MyPastOrdersAdapter(ArrayList<UserOrderDTO> userOrderDTOArrayList, Context context) {
        this.userOrderDTOArrayList = userOrderDTOArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyPastOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.past_order_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPastOrdersAdapter.ViewHolder holder, int position) {
        System.out.println("I m in 1");
        if (userOrderDTOArrayList.size() != 0 ){

            UserOrderDTO userOrderDTO = userOrderDTOArrayList.get(position);

            CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.burger_category_items,holder.canteenImageView,context);
            holder.itemsTextView.setText(userOrderDTO.getItemsName().replaceAll("^\"|\"$", ""));
            holder.canteenNameTextView.setText(userOrderDTO.getCanteen().replaceAll("^\"|\"$", ""));
            holder.itemdTotalTextView.setText(userOrderDTO.getItemsTotals());
//            }

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

        public ViewHolder(View itemView) {
            super(itemView);
            canteenImageView = itemView.findViewById(R.id.canteenImageView);
            itemsTextView = itemView.findViewById(R.id.itemsTextView);
            itemdTotalTextView = itemView.findViewById(R.id.itemdTotalTextView);
            canteenNameTextView = itemView.findViewById(R.id.canteenNameTextView);

        }
    }
}
