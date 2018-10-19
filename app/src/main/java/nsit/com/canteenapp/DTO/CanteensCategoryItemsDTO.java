package nsit.com.canteenapp.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by starhawk on 29/07/18.
 */

public class CanteensCategoryItemsDTO implements Parcelable {

    @Expose
    @SerializedName("_id")
    private String id;
    @Expose
    @SerializedName("product_id")
    private String productID;
    @Expose
    @SerializedName("title")
    private String itemName;
    @Expose
    @SerializedName("description")
    private String itemDescription;
    @Expose
    @SerializedName("Listed Price")
    private String listedPrice;
    @Expose
    @SerializedName("Cost Price")
    private String costPrice;
    @Expose
    @SerializedName("price")
    private String itemPrice;
    @Expose
    @SerializedName("Category")
    private String itemCategory;
    @Expose
    @SerializedName("url")
    private String itemURL;
    @Expose
    @SerializedName("rating")
    private String itemRating;
    @Expose
    @SerializedName("featured")
    private String itemFeatured;
    @Expose
    @SerializedName("Print-Price")
    private String printPrice;
    @Expose
    @SerializedName("canteen")
    private String canteenName;

    private boolean isFavourite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(String listedPrice) {
        this.listedPrice = listedPrice;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemURL() {
        return itemURL;
    }

    public void setItemURL(String itemURL) {
        this.itemURL = itemURL;
    }

    public String getItemRating() {
        return itemRating;
    }

    public void setItemRating(String itemRating) {
        this.itemRating = itemRating;
    }

    public String getItemFeatured() {
        return itemFeatured;
    }

    public void setItemFeatured(String itemFeatured) {
        this.itemFeatured = itemFeatured;
    }

    public String getPrintPrice() {
        return printPrice;
    }

    public void setPrintPrice(String printPrice) {
        this.printPrice = printPrice;
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

    @Override
    public String toString() {
        return "CanteensCategoryItemsDTO{" +
                "id='" + id + '\'' +
                ", productID='" + productID + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", listedPrice='" + listedPrice + '\'' +
                ", costPrice='" + costPrice + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemURL='" + itemURL + '\'' +
                ", itemRating='" + itemRating + '\'' +
                ", itemFeatured='" + itemFeatured + '\'' +
                ", printPrice='" + printPrice + '\'' +
                ", canteenName='" + canteenName + '\'' +
                '}';
    }

    private CanteensCategoryItemsDTO(Parcel in) {
        id = in.readString();
        productID = in.readString();
        itemName = in.readString();
        itemDescription = in.readString();
        listedPrice = in.readString();
        costPrice = in.readString();
        itemPrice = in.readString();
        itemCategory = in.readString();
        itemURL = in.readString();
        itemRating = in.readString();
        itemFeatured = in.readString();
        printPrice = in.readString();
        canteenName = in.readString();
    }

    public static final Creator<CanteensCategoryItemsDTO> CREATOR = new Creator<CanteensCategoryItemsDTO>() {
        @Override
        public CanteensCategoryItemsDTO createFromParcel(Parcel in) {
            return new CanteensCategoryItemsDTO(in);
        }

        @Override
        public CanteensCategoryItemsDTO[] newArray(int size) {
            return new CanteensCategoryItemsDTO[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(productID);
        dest.writeString(itemName);
        dest.writeString(itemDescription);
        dest.writeString(listedPrice);
        dest.writeString(costPrice);
        dest.writeString(itemPrice);
        dest.writeString(itemCategory);
        dest.writeString(itemURL);
        dest.writeString(itemRating);
        dest.writeString(itemFeatured);
        dest.writeString(printPrice);
        dest.writeString(canteenName);
    }
}
