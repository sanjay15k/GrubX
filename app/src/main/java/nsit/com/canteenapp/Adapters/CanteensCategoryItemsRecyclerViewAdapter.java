package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.CommonUtils.MyCartOperations;
import nsit.com.canteenapp.DTO.CanteensCategoryItemsDTO;
import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.DTO.MyCartScreenDTO;
import nsit.com.canteenapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by starhawk on 29/07/18.
 */

public class CanteensCategoryItemsRecyclerViewAdapter extends RecyclerView.Adapter<CanteensCategoryItemsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<CanteensCategoryItemsDTO> canteensCategoryItemsDTOArrayList = new ArrayList<>();
    private Context context;
    private int width;
    private int defaultHeight;
    private ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList;
    private ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList = new ArrayList<>();
    private FavouritesItemDTO favouritesItemDTO;
    private boolean alreadyInCart = false;
    private boolean isFavourite = false;
    private Snackbar snackbar;
    private ConstraintLayout canteenCategoryItemsLayout;
    MyCartOperations myCartOperations;
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

        System.out.println("TotalItems MyCartAdap is : "+totalItems);
        System.out.println("TotalPrice MyCartAdap is : "+totalPrice);
    }


    public CanteensCategoryItemsRecyclerViewAdapter(ArrayList<CanteensCategoryItemsDTO> canteensCategoryItemsDTOArrayList, Context context, int width, ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList, ConstraintLayout canteenCategoryItemsLayout){
        this.canteensCategoryItemsDTOArrayList = canteensCategoryItemsDTOArrayList;
        myCartOperations = new MyCartOperations(context);
        this.context = context;
        this.width = width;
        this.canteenCategoryItemsLayout = canteenCategoryItemsLayout;
        this.favouritesItemDTOArrayList = favouritesItemDTOArrayList;
        readItemsTotalsAndPrice();
    }

    private void designSnackBar(Snackbar snackbar){
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#ffffff"));
        snackBarView.setBackgroundColor(Color.parseColor("#e3ac12"));
    }

    @NonNull
    @Override
    public CanteensCategoryItemsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_items_single_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanteensCategoryItemsRecyclerViewAdapter.ViewHolder holder, final int position) {
        final CanteensCategoryItemsDTO canteensCategoryItemsDTO = canteensCategoryItemsDTOArrayList.get(position);
        System.out.println("Bg URL : "+canteensCategoryItemsDTO.getItemURL());
        if (canteensCategoryItemsDTO.getItemURL().equals("")){
            CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.default_food_item_image,holder.itemImageView,context);
        }
        else{
            CommonUtilsClass.loadingBackgroundImagesToImageViewWithUrl(canteensCategoryItemsDTO.getItemURL(),holder.itemImageView,context);
        }
//        CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.food1,holder.itemImageView,context);
        defaultHeight = holder.linearLayout.getLayoutParams().height;
        holder.linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width,defaultHeight));
        holder.itemName.setText(canteensCategoryItemsDTO.getItemName());

        System.out.println("Favourites Items size is : "+favouritesItemDTOArrayList.size());

        CommonUtilsClass.loadBackgroundImageToButton(R.drawable.fav_home_featured_false_icon, holder.favouriteBtn, context);

        if(favouritesItemDTOArrayList != null){
            for (int i=0; i<favouritesItemDTOArrayList.size(); i++){
                if (canteensCategoryItemsDTO.getItemName().equalsIgnoreCase(favouritesItemDTOArrayList.get(i).getItemName())){
                    CommonUtilsClass.loadBackgroundImageToButton(R.drawable.fav_home_featured_icon, holder.favouriteBtn, context);
                    canteensCategoryItemsDTO.setFavourite(true);
                }
            }
        }

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyInCart = false;
                readItemsTotalsAndPrice();
                try {
                    myCartScreenDTOArrayList = myCartOperations.readFromFile();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("isALready 1 is : "+alreadyInCart);
                for (MyCartScreenDTO myCartScreenDTO : myCartScreenDTOArrayList){
                    if (myCartScreenDTO.getItemName().equalsIgnoreCase(canteensCategoryItemsDTO.getItemName())){
                        alreadyInCart = true;
                        break;
                    }
                }
                System.out.println("isALready 2 is : "+alreadyInCart);
                if (!alreadyInCart) {
                    MyCartScreenDTO myCartScreenDTO = new MyCartScreenDTO();
                    snackbar = Snackbar.make(canteenCategoryItemsLayout, canteensCategoryItemsDTO.getItemName() + " added to your cart", Snackbar.LENGTH_SHORT);
                    designSnackBar(snackbar);
                    snackbar.show();
                    myCartScreenDTO.setItemName(canteensCategoryItemsDTO.getItemName());
                    myCartScreenDTO.setItemPrice(String.valueOf(canteensCategoryItemsDTO.getItemPrice()));
                    myCartScreenDTO.setItemImageUrl(canteensCategoryItemsDTO.getItemURL());
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
                    snackbar = Snackbar.make(canteenCategoryItemsLayout,"Item is already in cart",Snackbar.LENGTH_SHORT);
                    designSnackBar(snackbar);
                    snackbar.show();
                }
            }
        });

        holder.favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FavouritesItemDTO favouritesItemDTO1 = null;

                for (FavouritesItemDTO favouritesItemDTO : favouritesItemDTOArrayList){
                    if (favouritesItemDTO.getItemName().equalsIgnoreCase(canteensCategoryItemsDTO.getItemName())){
                        favouritesItemDTO1 = favouritesItemDTO;
                        break;
                    }
                }

                if (canteensCategoryItemsDTO.isFavourite()) {
                    System.out.println("I m in 1");
                    favouritesItemDTOArrayList.remove(favouritesItemDTO1);
                    snackbar = Snackbar.make(canteenCategoryItemsLayout,canteensCategoryItemsDTO.getItemName()+" removed from favourite list",Snackbar.LENGTH_SHORT);
                    canteensCategoryItemsDTO.setFavourite(false);
                }
                else {
                    System.out.println("I m in 2");
                    for (FavouritesItemDTO favouritesItemDTO : favouritesItemDTOArrayList){
                        if (favouritesItemDTO.getItemName().equalsIgnoreCase(canteensCategoryItemsDTO.getItemName())){
                            favouritesItemDTOArrayList.remove(favouritesItemDTO);
                            snackbar = Snackbar.make(canteenCategoryItemsLayout,canteensCategoryItemsDTO.getItemName()+" removed from favourite list",Snackbar.LENGTH_SHORT);
                            isFavourite=true;
                            canteensCategoryItemsDTO.setFavourite(false);
                            break;
                        }
                    }
                    if (!isFavourite) {
                        System.out.println("I m in 3");
                        favouritesItemDTO = new FavouritesItemDTO(canteensCategoryItemsDTO.getItemName(), canteensCategoryItemsDTO.getItemPrice(), canteensCategoryItemsDTO.getItemURL());
                        favouritesItemDTOArrayList.add(favouritesItemDTO);
                        snackbar = Snackbar.make(canteenCategoryItemsLayout,canteensCategoryItemsDTO.getItemName() + " added to favourite list",Snackbar.LENGTH_SHORT);
                        canteensCategoryItemsDTO.setFavourite(true);
                    }
                }

                designSnackBar(snackbar);
                snackbar.show();
                notifyItemChanged(position);
            }
        });

        holder.itemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtilsClass.showImageDialogWithImageUrl(context,canteensCategoryItemsDTO.getItemURL());
            }
        });


    }

    @Override
    public int getItemCount() {
        return canteensCategoryItemsDTOArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        Button favouriteBtn;
        Button addToCartBtn;
        LinearLayout linearLayout;
        ImageView itemImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            itemImageView = itemView.findViewById(R.id.canteenImageView);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn);
            itemName = itemView.findViewById(R.id.itemName);
            favouriteBtn = itemView.findViewById(R.id.favouriteBtn);
        }
    }
}
