package nsit.com.canteenapp.DTO;

import java.io.Serializable;

/**
 * Created by starhawk on 05/08/18.
 */

public class MyCartScreenDTO implements Serializable{

    private static final long serialVersionUID = 2L;

    private String itemName;
    private String itemPrice;
    private String itemImageUrl;
    private int itemQty;
    private String canteenName;

    public MyCartScreenDTO(){
        itemQty = 1;
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

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }
}
