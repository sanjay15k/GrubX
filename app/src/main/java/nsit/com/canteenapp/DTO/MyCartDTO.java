package nsit.com.canteenapp.DTO;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by starhawk on 04/08/18.
 */

public class MyCartDTO {

    @Expose
    @SerializedName("canteen")
    private String canteenName;

    @Expose
    @SerializedName("cust_name")
    private String customerName;

    @Expose
    @SerializedName("contact")
    private String phoneNumber;

    @Expose
    @SerializedName("totals")
    private String totalPrice;

    @Expose
    @SerializedName("delivery_time")
    private String deliveryTime;

    @Expose
    @SerializedName("items")
    private JsonArray itemsJsonArray;

    @Expose
    @SerializedName("username")
    private String username;

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public JsonArray getItemsJsonArray() {
        return itemsJsonArray;
    }

    public void setItemsJsonArray(JsonArray itemsJsonArray) {
        this.itemsJsonArray = itemsJsonArray;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "MyCartDTO{" +
                "canteenName='" + canteenName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", itemsJsonArray=" + itemsJsonArray +
                ", username='" + username + '\'' +
                '}';
    }
}
