package nsit.com.canteenapp.DTO;

public class MyOrdersDTO {

    private String imageUrl;
    private String itemName;
    private String canteenName;
    private String itemPrice;

    public MyOrdersDTO(String imageUrl, String itemName, String canteenName, String itemPrice) {
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.canteenName = canteenName;
        this.itemPrice = itemPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
