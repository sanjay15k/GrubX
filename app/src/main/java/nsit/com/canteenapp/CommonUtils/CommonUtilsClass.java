package nsit.com.canteenapp.CommonUtils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Objects;

import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.R;

/**
 * Created by starhawk on 28/07/18.
 */

public class CommonUtilsClass {

    public static void updateStatusBarColor(String color,Window window){// Color must be in hexadecimal fromat
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor(color));
    }

    public static void loadingBackgroundImagesToLayout(int drawable, final View layout, Context context){
        Glide.with(context).load(drawable).apply(new RequestOptions().centerCrop()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
    }

    public static void loadingBackgroundImagesToLayoutWithUrl(String url, final View layout, Context context){
        Glide.with(context).load(url).apply(new RequestOptions().centerCrop()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
                layout.setBackground(resource);
            }
        });
    }

    public static void loadBackgroundImageToButton(int drawable, final Button button, Context context){
        Glide.with(context).load(drawable).apply(new RequestOptions().centerCrop()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                button.setBackground(resource);
            }
        });
    }

    public static void loadingBackgroundImagesToImageView(int drawable, ImageView imageView, Context context){
        Glide.with(context).load(drawable).apply(new RequestOptions().centerCrop()).into(imageView);
    }

    public static void loadingBackgroundImagesToImageViewWithUrl(String url, ImageView imageView, Context context){
        Glide.with(context).load(url).apply(new RequestOptions().centerCrop()).into(imageView);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static ProgressDialog createProgressDialog(Context context, String title, String message){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.create();
        return progressDialog;
    }

    public static File openFile(Context context, String fileName) throws IOException {
        return new File(context.getFilesDir(),fileName);
    }

    public static void writeToFile(File file, ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList) throws IOException {
        if(file != null) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(favouritesItemDTOArrayList);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();
        }
    }

    public static ArrayList<FavouritesItemDTO> readFromFile(File file) throws IOException, ClassNotFoundException {
            ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList;
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            favouritesItemDTOArrayList = (ArrayList<FavouritesItemDTO>) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        return favouritesItemDTOArrayList;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager != null && connectivityManager.getActiveNetworkInfo() != null;
    }

    public static void showImageDialogWithImageUrl(Context context, String imageUrl){
        Dialog imageDialog = new Dialog(context);
        Objects.requireNonNull(imageDialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.image_dailog_layout,null);
        ImageView imageDialogImageView  = view.findViewById(R.id.imageDailogImageView);
        if (imageUrl.equals("")){
            CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.default_food_item_image,imageDialogImageView,context);
        }
        else{
            CommonUtilsClass.loadingBackgroundImagesToImageViewWithUrl(imageUrl,imageDialogImageView,context);
        }
        imageDialog.setContentView(view);
        imageDialog.show();
    }

}
