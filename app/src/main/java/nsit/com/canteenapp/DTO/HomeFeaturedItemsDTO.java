package nsit.com.canteenapp.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by starhawk on 02/08/18.
 */

public class HomeFeaturedItemsDTO {

    @SerializedName("product_id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("Print-Price")
    @Expose
    private String price;
    @SerializedName("url")
    @Expose
    private String imageUrl;
    @SerializedName("featured")
    @Expose
    private Boolean isfeatured;
    @SerializedName("canteen")
    @Expose
    private String canteenName;
    private boolean isFavourite=false;

    HomeFeaturedItemsDTO(String id, String title, String price,String imageUrl, boolean isfeatured){
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.isfeatured = isfeatured;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsFeatured() {
        return isfeatured;
    }

    public void setIsFeatured(Boolean isfeatured) {
        this.isfeatured = isfeatured;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
