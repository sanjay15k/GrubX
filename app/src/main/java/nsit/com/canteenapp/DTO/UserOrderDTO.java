package nsit.com.canteenapp.DTO;

import java.util.ArrayList;

public class UserOrderDTO {

    private ArrayList<ItemsDTO> itemsDTOArrayList;
    private String id;
    private String canteen;
    private String customerName;
    private String phoneNumber;
    private String totalPrice;
    private String deliveryTime;
    private String status;

    private String itemsName;
    private String itemsTotals;

    public ArrayList<ItemsDTO> getItemsDTOArrayList() {
        return itemsDTOArrayList;
    }

    public void setItemsDTOArrayList(ArrayList<ItemsDTO> itemsDTOArrayList) {
        this.itemsDTOArrayList = itemsDTOArrayList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCanteen() {
        return canteen;
    }

    public void setCanteen(String canteen) {
        this.canteen = canteen;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemsName() {
        return itemsName;
    }

    public void setItemsName(String itemsName) {
        this.itemsName = itemsName;
    }

    public String getItemsTotals() {
        return itemsTotals;
    }

    public void setItemsTotals(String itemsTotals) {
        this.itemsTotals = itemsTotals;
    }

    public static class ItemsDTO{

        private String name;
        private String qty;
        private String price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ItemsDTO{" +
                    "name='" + name + '\'' +
                    ", qty='" + qty + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserOrderDTO{" +
                "itemsDTOArrayList=" + itemsDTOArrayList +
                ", id='" + id + '\'' +
                ", canteen='" + canteen + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", deliveryTime='" + deliveryTime + '\'' +
                ", status='" + status + '\'' +
                ", itemsName='" + itemsName + '\'' +
                ", itemsTotals='" + itemsTotals + '\'' +
                '}';
    }
}
