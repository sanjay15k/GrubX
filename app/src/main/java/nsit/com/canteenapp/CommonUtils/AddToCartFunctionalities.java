package nsit.com.canteenapp.CommonUtils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.DTO.MyCartScreenDTO;

/**
 * Created by starhawk on 05/08/18.
 */

public class AddToCartFunctionalities {

    private ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList = new ArrayList<>();
    MyCartOperations myCartOperations;
    private boolean alreadyInCart = false;
    private Snackbar snackbar;

    public AddToCartFunctionalities(Context context){
        myCartOperations = new MyCartOperations(context);
    }

    public void designSnackBar(Snackbar snackbar){
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#6bf56d"));
        snackBarView.setBackgroundColor(Color.parseColor("#093c69"));
    }

    public void addToCart(RelativeLayout relativeLayout, FavouritesItemDTO favouritesItemDTO){
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
            snackbar = Snackbar.make(relativeLayout, favouritesItemDTO.getItemName() + " added to your cart", Snackbar.LENGTH_SHORT);
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
        }
        else {
            snackbar = Snackbar.make(relativeLayout,"Item is already in cart",Snackbar.LENGTH_SHORT);
            designSnackBar(snackbar);
            snackbar.show();
        }
    }

}
