package nsit.com.canteenapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.CommonUtils.MyCartOperations;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.DTO.MyCartScreenDTO;
import nsit.com.canteenapp.DTO.OffersDTO;
import nsit.com.canteenapp.MyCart;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.GetOffersCall;

import static android.content.Context.MODE_PRIVATE;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private ArrayList<OffersDTO> offersDTOArrayList;
    private Context context;
    private boolean alreadyInCart = false;
    private ArrayList<MyCartScreenDTO> myCartScreenDTOArrayList = new ArrayList<>();
    MyCartOperations myCartOperations;
    private int totalItems;
    private int totalPrice;
    private LoginDTO loginDTO;

    public OffersAdapter(ArrayList<OffersDTO> offersDTOArrayList, Context context, LoginDTO loginDTO) {
        myCartOperations = new MyCartOperations(context);
        this.offersDTOArrayList = offersDTOArrayList;
        this.context = context;
        this.loginDTO = loginDTO;
        readItemsTotalsAndPrice();
        try {
            System.out.println("Hello : "+myCartScreenDTOArrayList.size());
            myCartScreenDTOArrayList = myCartOperations.readFromFile();
            System.out.println("Hello : "+myCartScreenDTOArrayList.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readItemsTotalsAndPrice(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyCartTotals",MODE_PRIVATE);
        totalItems = sharedPreferences.getInt("totalItems",0);
        totalPrice = sharedPreferences.getInt("totalPrice",0);
    }

    public void setItemsTotalAndPrice(int totalItems, int totalPrice){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyCartTotals",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("totalItems",totalItems);
        editor.putInt("totalPrice",totalPrice);
        editor.commit();
    }

    public void designSnackBar(Snackbar snackbar){
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#ffffff"));
        snackBarView.setBackgroundColor(Color.parseColor("#E53935"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_item_single_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final OffersDTO offersDTO = offersDTOArrayList.get(position);

        CommonUtilsClass.loadingBackgroundImagesToImageViewWithUrl(offersDTO.getBackgroundImageUrl(),holder.offerBackgroundImageView,context);
        holder.offerTitle1TextView.setText(offersDTO.getTitle1());
        holder.offerTitle2TextView.setText(offersDTO.getTitle2());

        System.out.println("Holder "+position+"\t"+offersDTO.getItemName());

        holder.offerParentLayout.setOnClickListener(new View.OnClickListener() {
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
                    if (myCartScreenDTO.getItemName().equalsIgnoreCase(offersDTO.getItemName())){
                        alreadyInCart = true;
                    }
                }
                System.out.println("isALready 2 is : "+alreadyInCart);
                if (!alreadyInCart) {
                    MyCartScreenDTO myCartScreenDTO = new MyCartScreenDTO();
                    myCartScreenDTO.setItemName(offersDTO.getItemName());
                    myCartScreenDTO.setItemPrice(String.valueOf(offersDTO.getItemPrice()));
                    myCartScreenDTO.setItemImageUrl(offersDTO.getBackgroundImageUrl());
                    myCartScreenDTO.setCanteenName(offersDTO.getCanteenName());
                    myCartScreenDTOArrayList.add(myCartScreenDTO);
                    try {
                        myCartOperations.writeToFile(myCartScreenDTOArrayList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    totalItems++;
                    totalPrice += Integer.parseInt(myCartScreenDTO.getItemPrice());
                    setItemsTotalAndPrice(totalItems,totalPrice);

                    Intent intent = new Intent(context, MyCart.class);
                    intent.putExtra("LoginDTO",loginDTO);
                    context.startActivity(intent);

                }
                else {
                    Intent intent = new Intent(context, MyCart.class);
                    intent.putExtra("LoginDTO",loginDTO);
                    context.startActivity(intent);
                }
                alreadyInCart = false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return offersDTOArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout offerParentLayout;
        TextView offerTitle1TextView;
        TextView offerTitle2TextView;
        ImageView offerBackgroundImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            offerParentLayout = itemView.findViewById(R.id.offerParentLayout);
            offerBackgroundImageView = itemView.findViewById(R.id.offerBackgroundImageView);
            offerTitle1TextView = itemView.findViewById(R.id.offerTitle1TextView);
            offerTitle2TextView = itemView.findViewById(R.id.offerTitle2TextView);
        }
    }

}
