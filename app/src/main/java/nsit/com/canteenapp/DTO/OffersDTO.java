package nsit.com.canteenapp.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OffersDTO {

    @Expose
    @SerializedName("_id")
    private String Id;
    @Expose
    @SerializedName("product_id")
    private String productId;
    @Expose
    @SerializedName("title1")
    private String title1;
    @Expose
    @SerializedName("title2")
    private String title2;
    @Expose
    @SerializedName("Category")
    private String category;
    @Expose
    @SerializedName("url")
    private String backgroundImageUrl;
    @Expose
    @SerializedName("canteen")
    private String canteenName;
    @Expose
    @SerializedName("itemName")
    private String itemName;
    @Expose
    @SerializedName("itemPrice")
    private String itemPrice;
    @Expose
    @SerializedName("itemQty")
    private String itemQuantity;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
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

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
