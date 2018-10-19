package nsit.com.canteenapp.PaytmIntegration.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by starhawk on 11/08/18.
 */

public class CheckSumHashDTO {

    @SerializedName("CHECKSUMHASH")
    private String checksumHash;

    @SerializedName("ORDER_ID")
    private String orderId;

    @SerializedName("payt_STATUS")
    private String paytmStatus;

    @SerializedName("CUST_ID")
    private String customerId;

    @SerializedName("TXN_AMOUTN")
    private String transactionAmount;

    public CheckSumHashDTO(String checksumHash, String orderId, String paytmStatus, String customerId, String transactionAmount) {
        this.checksumHash = checksumHash;
        this.orderId = orderId;
        this.paytmStatus = paytmStatus;
        this.customerId = customerId;
        this.transactionAmount = transactionAmount;
    }

    public String getChecksumHash() {
        return checksumHash;
    }

    public void setChecksumHash(String checksumHash) {
        this.checksumHash = checksumHash;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaytmStatus() {
        return paytmStatus;
    }

    public void setPaytmStatus(String paytmStatus) {
        this.paytmStatus = paytmStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }



}
