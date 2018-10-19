package nsit.com.canteenapp.DTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by starhawk on 30/07/18.
 */

public class FavouritesItemDTO implements Serializable{

    private static final long serialVersionUID = 6509541571362141772L;

    private String itemName;
    private String itemPrice;
    private String favouriteItemImageUrl;
    private ArrayList<HomeFeaturedItemsDTO> arrayList;
    private boolean isFavourite =true;

    public FavouritesItemDTO(String itemName, String itemPrice, String favouriteItemImageUrl){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.favouriteItemImageUrl = favouriteItemImageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getFavouriteItemImageUrl() {
        return favouriteItemImageUrl;
    }

    public void setFavouriteItemImageUrl(String favouriteItemImageUrl) {
        this.favouriteItemImageUrl = favouriteItemImageUrl;
    }

    public ArrayList<HomeFeaturedItemsDTO> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<HomeFeaturedItemsDTO> arrayList) {
        this.arrayList = arrayList;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
