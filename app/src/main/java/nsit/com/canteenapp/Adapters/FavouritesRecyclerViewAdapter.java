package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.CommonUtils.MyCartOperations;
import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.DTO.MyCartScreenDTO;
import nsit.com.canteenapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by starhawk on 30/07/18.
 */

public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList = new ArrayList<>();
    private Context context;
    private int width;
    private int defaultHeight;
    private boolean alreadyInCart = false;
    private ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList = new ArrayList<>();
    MyCartOperations myCartOperations;
    private Snackbar snackbar;
    private CoordinatorLayout favouritesCoordinatorLayout;
    private int totalItems;
    private int totalPrice;

    public void setItemsTotalAndPrice(int totalItems, int totalPrice){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyCartTotals",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("totalItems",totalItems);
        editor.putInt("totalPrice",totalPrice);
        editor.commit();
    }

    public void readItemsTotalsAndPrice(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyCartTotals",MODE_PRIVATE);
        totalItems = sharedPreferences.getInt("totalItems",0);
        totalPrice = sharedPreferences.getInt("totalPrice",0);
    }

    public FavouritesRecyclerViewAdapter(ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList, Context context, int width, CoordinatorLayout favouritesCoordinatorLayout){
        myCartOperations = new MyCartOperations(context);
        this.favouritesItemDTOArrayList = favouritesItemDTOArrayList;
        this.context = context;
        this.width = width;
        this.favouritesCoordinatorLayout = favouritesCoordinatorLayout;
        readItemsTotalsAndPrice();
    }

    public void designSnackBar(Snackbar snackbar){
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#ffffff"));
        snackBarView.setBackgroundColor(Color.parseColor("#5ec494"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourites_item_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FavouritesItemDTO favouritesItemDTO = favouritesItemDTOArrayList.get(position);
        defaultHeight = holder.favouritesFrameLayout.getLayoutParams().height;
        holder.favouritesFrameLayout.setLayoutParams(new FrameLayout.LayoutParams(width,defaultHeight));
        holder.itemName.setText(favouritesItemDTO.getItemName());
        holder.itemPrice.setText("Rs "+favouritesItemDTO.getItemPrice()+" /-");

        CommonUtilsClass.loadingBackgroundImagesToLayoutWithUrl(favouritesItemDTO.getFavouriteItemImageUrl(), holder.favouriteItemImageLayout,context);

        holder.addToCartFavouriteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readItemsTotalsAndPrice();
                try {
                    myCartScreenDTOArrayList = myCartOperations.readFromFile();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("isALready 1 is : "+alreadyInCart);
                for (MyCartScreenDTO myCartScreenDTO : myCartScreenDTOArrayList){
                    if (myCartScreenDTO.getItemName().equalsIgnoreCase(favouritesItemDTO.getItemName())){
                        alreadyInCart = true;
                    }
                }
                System.out.println("isALready 2 is : "+alreadyInCart);
                if (!alreadyInCart) {
                    MyCartScreenDTO myCartScreenDTO = new MyCartScreenDTO();
                    snackbar = Snackbar.make(favouritesCoordinatorLayout, favouritesItemDTO.getItemName() + " added to your cart", Snackbar.LENGTH_SHORT);
                    designSnackBar(snackbar);
                    snackbar.show();
                    myCartScreenDTO.setItemName(favouritesItemDTO.getItemName());
                    myCartScreenDTO.setItemPrice(favouritesItemDTO.getItemPrice());
                    myCartScreenDTO.setItemImageUrl(favouritesItemDTO.getFavouriteItemImageUrl());
                    myCartScreenDTOArrayList.add(myCartScreenDTO);
                    try {
                        myCartOperations.writeToFile(myCartScreenDTOArrayList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    totalItems++;
                    totalPrice += Integer.parseInt(myCartScreenDTO.getItemPrice());
                    setItemsTotalAndPrice(totalItems,totalPrice);
                }
                else {
                    snackbar = Snackbar.make(favouritesCoordinatorLayout,"Item is already in cart",Snackbar.LENGTH_SHORT);
                    designSnackBar(snackbar);
                    snackbar.show();
                }
                alreadyInCart = false;
            }
        });

        holder.favouriteItemImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtilsClass.showImageDialogWithImageUrl(context,favouritesItemDTO.getFavouriteItemImageUrl());
            }
        });

    }

    @Override
    public int getItemCount() {
        return favouritesItemDTOArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemPrice;
        FrameLayout favouritesFrameLayout;
        LinearLayout favouriteItemImageLayout;
        ImageView addToCartFavouriteItem;
        public RelativeLayout viewBackground, viewForeground;

        public ViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            favouritesFrameLayout = itemView.findViewById(R.id.favouritesFrameLayout);
            favouriteItemImageLayout = itemView.findViewById(R.id.favouriteItemImageLayout);
            addToCartFavouriteItem = itemView.findViewById(R.id.addToCartFavouriteItem);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }

    public void removeItem(int position) {
        favouritesItemDTOArrayList.remove(position);
        notifyItemRemoved(position);
    }

}
