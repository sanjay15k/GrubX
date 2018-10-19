package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.MyCartScreenDTO;
import nsit.com.canteenapp.MyCart;
import nsit.com.canteenapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by starhawk on 05/08/18.
 */

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    private ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList = new ArrayList<>();
    private Context context;
    private ImageView emptyCartImageView;
    private int totalItems;
    private int totalPrice;

    private void setItemsTotalAndPrice(int totalItems, int totalPrice){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyCartTotals",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("totalItems",totalItems);
        editor.putInt("totalPrice",totalPrice);
        editor.commit();
    }

    private void readItemsTotalsAndPrice(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyCartTotals",MODE_PRIVATE);
        totalItems = sharedPreferences.getInt("totalItems",0);
        totalPrice = sharedPreferences.getInt("totalPrice",0);

        System.out.println("TotalItems MyCartAdap is : "+totalItems);
        System.out.println("TotalPrice MyCartAdap is : "+totalPrice);
    }

    public MyCartAdapter(ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList, Context context, ImageView emptyCartImageView){
        this.myCartScreenDTOArrayList = myCartScreenDTOArrayList;
        this.context = context;
        this.emptyCartImageView = emptyCartImageView;
        readItemsTotalsAndPrice();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cart_single_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MyCartScreenDTO myCartScreenDTO = myCartScreenDTOArrayList.get(position);
        holder.itemNameTextView.setText(myCartScreenDTO.getItemName());
        holder.itemPriceTextView.setText("Rs "+myCartScreenDTO.getItemPrice()+"/-");
        CommonUtilsClass.loadingBackgroundImagesToLayoutWithUrl(myCartScreenDTO.getItemImageUrl(),holder.itemImageView,context);
        holder.itemQty.setText(String.valueOf(myCartScreenDTO.getItemQty()));

        holder.plusImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCartScreenDTO.setItemQty(myCartScreenDTO.getItemQty()+1);
                totalPrice += Integer.parseInt(myCartScreenDTO.getItemPrice());
                totalItems ++;
                setItemsTotalAndPrice(totalItems,totalPrice);
                ((MyCart)context).onRefresh();
                notifyItemChanged(holder.getAdapterPosition());
                System.out.println("After plus clicked");
            }
        });

        holder.minusImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPrice -= Integer.parseInt(myCartScreenDTO.getItemPrice());
                totalItems --;
                setItemsTotalAndPrice(totalItems,totalPrice);
                ((MyCart)context).onRefresh();
                if (Integer.parseInt(holder.itemQty.getText().toString()) == 1){
                    myCartScreenDTOArrayList.remove(holder.getAdapterPosition());
                    setEmptyCartImageView();
                    notifyDataSetChanged();
                }
                else {
                    myCartScreenDTO.setItemQty(myCartScreenDTO.getItemQty()-1);
                    notifyItemChanged(holder.getAdapterPosition());
                }
            }
        });

        holder.cartItemRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalItems -= Integer.parseInt(holder.itemQty.getText().toString());
                int indexOfSlash = holder.itemPriceTextView.getText().toString().lastIndexOf("/");
                totalPrice -= (Integer.parseInt(holder.itemPriceTextView.getText().toString().substring(3,indexOfSlash))*Integer.parseInt(holder.itemQty.getText().toString()));
                System.out.println("Price new is : "+totalPrice);
                setItemsTotalAndPrice(totalItems,totalPrice);
                ((MyCart)context).onRefresh();
                myCartScreenDTOArrayList.remove(position);
                setEmptyCartImageView();
                notifyDataSetChanged();
            }
        });

        holder.itemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtilsClass.showImageDialogWithImageUrl(context,myCartScreenDTO.getItemImageUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCartScreenDTOArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemQty;
        ImageButton plusImageBtn;
        ImageButton minusImageBtn;
        Button cartItemRemoveBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.canteenImageView);
            itemNameTextView = itemView.findViewById(R.id.itemsTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemdTotalTextView);
            itemQty = itemView.findViewById(R.id.itemQty);
            plusImageBtn = itemView.findViewById(R.id.plusImageBtn);
            minusImageBtn = itemView.findViewById(R.id.minusImageBtn);
            cartItemRemoveBtn = itemView.findViewById(R.id.cartItemRemoveBtn);
        }
    }

    private void setEmptyCartImageView(){
        if (myCartScreenDTOArrayList.size() == 0){
            emptyCartImageView.setImageResource(R.drawable.empty_cart_icon);
        }
    }

}
