package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.CommonUtils.MyCartOperations;
import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.DTO.HomeFeaturedItemsDTO;
import nsit.com.canteenapp.DTO.MyCartScreenDTO;
import nsit.com.canteenapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by starhawk on 02/08/18.
 */

public class HomeFeaturedItemsAdapter extends RecyclerView.Adapter<HomeFeaturedItemsAdapter.ViewHolder> {

    private ArrayList<HomeFeaturedItemsDTO> arrayList;
    private ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList;
    private FavouritesItemDTO favouritesItemDTO;
    private Context context;
    private Snackbar snackbar;
    private boolean isFavourite = false;
    private ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList = new ArrayList<>();
    private MyCartOperations myCartOperations;
    private RelativeLayout homeRelativeLayout;
    private boolean alreadyInCart = false;
    private int totalItems;
    private int totalPrice;
    private static boolean isAnimationFirstTime = true;

    public static void setIsAnimationFirstTime(boolean isAnimationFirstTime) {
        HomeFeaturedItemsAdapter.isAnimationFirstTime = isAnimationFirstTime;
    }

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
    }

    public HomeFeaturedItemsAdapter(ArrayList<HomeFeaturedItemsDTO> arrayList, Context context, ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList, RelativeLayout homeRelativeLayout){
        myCartOperations = new MyCartOperations(context);
        this.arrayList = arrayList;
        this.context =context;
        this.favouritesItemDTOArrayList = favouritesItemDTOArrayList;
        this.homeRelativeLayout = homeRelativeLayout;
        readItemsTotalsAndPrice();
        try {
            System.out.println("Hello : "+myCartScreenDTOArrayList.size());
            myCartScreenDTOArrayList = myCartOperations.readFromFile();
            System.out.println("Hello : "+myCartScreenDTOArrayList.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void designSnackBar(Snackbar snackbar){
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#ffffff"));
        snackBarView.setBackgroundColor(Color.parseColor("#E53935"));
    }

    @NonNull
    @Override
    public HomeFeaturedItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_featured_item_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeFeaturedItemsAdapter.ViewHolder holder, final int position) {
        final HomeFeaturedItemsDTO homeFeaturedItemsDTO = arrayList.get(position);
        Glide.with(context).load(homeFeaturedItemsDTO.getImageUrl()).apply(new RequestOptions().placeholder(R.drawable.default_food_item_image)).into(holder.itemImageView);
        holder.itemNameTextView.setText(homeFeaturedItemsDTO.getTitle());
        holder.itemPriceTextView.setText(homeFeaturedItemsDTO.getPrice());
        holder.canteenNameTextView.setText(homeFeaturedItemsDTO.getCanteenName());

        CommonUtilsClass.loadBackgroundImageToButton(R.drawable.fav_home_featured_false_icon, holder.addToFavBtn, context);

        if (isAnimationFirstTime){
            setSlideUpAnimation(holder.itemView);
        }

        if(favouritesItemDTOArrayList != null){
            for (int i=0; i<favouritesItemDTOArrayList.size(); i++){
                if (homeFeaturedItemsDTO.getTitle().equalsIgnoreCase(favouritesItemDTOArrayList.get(i).getItemName())){
                    CommonUtilsClass.loadBackgroundImageToButton(R.drawable.fav_home_featured_icon, holder.addToFavBtn, context);
                    homeFeaturedItemsDTO.setFavourite(true);
                }
            }
        }

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnimationFirstTime = false;
                readItemsTotalsAndPrice();
                try {
                    myCartScreenDTOArrayList = myCartOperations.readFromFile();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("isALready 1 is : "+alreadyInCart);
                for (MyCartScreenDTO myCartScreenDTO : myCartScreenDTOArrayList){
                    if (myCartScreenDTO.getItemName().equalsIgnoreCase(homeFeaturedItemsDTO.getTitle())){
                        alreadyInCart = true;
                    }
                }
                System.out.println("isALready 2 is : "+alreadyInCart);
                if (!alreadyInCart) {
                    MyCartScreenDTO myCartScreenDTO = new MyCartScreenDTO();
                    snackbar = Snackbar.make(homeRelativeLayout, homeFeaturedItemsDTO.getTitle() + " added to your cart", Snackbar.LENGTH_SHORT);
                    designSnackBar(snackbar);
                    snackbar.show();
                    myCartScreenDTO.setItemName(homeFeaturedItemsDTO.getTitle());
                    myCartScreenDTO.setItemPrice(String.valueOf(homeFeaturedItemsDTO.getPrice()));
                    System.out.println("Heyy   "+ homeFeaturedItemsDTO.getPrice());
                    myCartScreenDTO.setItemImageUrl(homeFeaturedItemsDTO.getImageUrl());
                    myCartScreenDTO.setCanteenName(homeFeaturedItemsDTO.getCanteenName());
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
                    snackbar = Snackbar.make(homeRelativeLayout,"Item is already in cart",Snackbar.LENGTH_SHORT);
                    designSnackBar(snackbar);
                    snackbar.show();
                }
                alreadyInCart = false;
            }
        });

        holder.addToFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAnimationFirstTime=false;
                FavouritesItemDTO favouritesItemDTO1 = null;

                for (FavouritesItemDTO favouritesItemDTO : favouritesItemDTOArrayList){
                    if (favouritesItemDTO.getItemName().equalsIgnoreCase(homeFeaturedItemsDTO.getTitle())){
                        favouritesItemDTO1 = favouritesItemDTO;
                        break;
                    }
                }

                if (homeFeaturedItemsDTO.isFavourite()) {
                    favouritesItemDTOArrayList.remove(favouritesItemDTO1);
                    snackbar = Snackbar.make(homeRelativeLayout,homeFeaturedItemsDTO.getTitle()+" removed from favourite list",Snackbar.LENGTH_SHORT);
                    homeFeaturedItemsDTO.setFavourite(false);
                }
                else {
                    for (FavouritesItemDTO favouritesItemDTO : favouritesItemDTOArrayList){
                        if (favouritesItemDTO.getItemName().equalsIgnoreCase(homeFeaturedItemsDTO.getTitle())){
                            favouritesItemDTOArrayList.remove(favouritesItemDTO);
                            snackbar = Snackbar.make(homeRelativeLayout,homeFeaturedItemsDTO.getTitle()+" removed from favourite list",Snackbar.LENGTH_SHORT);
                            isFavourite=true;
                            homeFeaturedItemsDTO.setFavourite(false);
                            break;
                        }
                    }
                    if (!isFavourite) {
                        favouritesItemDTO = new FavouritesItemDTO(homeFeaturedItemsDTO.getTitle(), homeFeaturedItemsDTO.getPrice(), homeFeaturedItemsDTO.getImageUrl());
                        favouritesItemDTOArrayList.add(favouritesItemDTO);
                        snackbar = Snackbar.make(homeRelativeLayout,homeFeaturedItemsDTO.getTitle() + " added to favourite list",Snackbar.LENGTH_SHORT);
                        homeFeaturedItemsDTO.setFavourite(true);
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
                CommonUtilsClass.showImageDialogWithImageUrl(context,homeFeaturedItemsDTO.getImageUrl());
            }
        });

    }

    private void setSlideUpAnimation(View view) {
        Animation slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up);// animation file
        view.startAnimation(slideUpAnimation);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView itemNameTextView;
        private TextView itemPriceTextView;
        private TextView canteenNameTextView;
        private ImageView itemImageView;
        private Button addToCartBtn;
        private Button addToFavBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemsTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemdTotalTextView);
            canteenNameTextView = itemView.findViewById(R.id.canteenNameTextView);
            itemImageView = itemView.findViewById(R.id.canteenImageView);
            addToCartBtn = itemView.findViewById(R.id.addToCartBtn);
            addToFavBtn = itemView.findViewById(R.id.addToFavBtn);
        }
    }
}
