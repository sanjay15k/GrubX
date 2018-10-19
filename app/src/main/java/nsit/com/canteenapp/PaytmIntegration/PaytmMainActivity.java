package nsit.com.canteenapp.PaytmIntegration;

import android.content.Context;
import android.os.Bundle;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;

import nsit.com.canteenapp.PaytmIntegration.DTO.CheckSumHashDTO;
import nsit.com.canteenapp.PaytmIntegration.DTO.GetCheckSumDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by starhawk on 11/08/18.
 */

public class PaytmMainActivity implements PaytmPaymentTransactionCallback {

    private String totalPrice;
    private static String baseUrl = "http://authenticate.grubx.in";
    private Context context;

    public PaytmMainActivity(String totalPrice, Context context) {
        this.totalPrice = totalPrice;
        this.context = context;
        generateChecksum();
    }

    private void generateChecksum(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        final GetCheckSumDTO getCheckSumDTO = new GetCheckSumDTO(
                Constants.M_ID,
                Constants.CHANNEL_ID,
                totalPrice,
                Constants.WEBSITE,
                Constants.CALLBACK_URL,
                Constants.PAYTM_MERCHANT_KEY,
                Constants.INDUSTRY_TYPE_ID
        );

//        @Field("CHANNEL_ID") String channelId,
//        @Field("TXN_AMOUNT") String txnAmount,
//        @Field("WEBSITE") String website,
//        @Field("PAYTM_MERCHANT_KEY") String merchantKey,
//        @Field("CALLBACK_URL") String callbackUrl,
//        @Field("INDUSTRY_TYPE_ID") String industryTypeId

        Call<CheckSumHashDTO> checkSumHashDTOCall = apiInterface.getChecksum(
                Constants.M_ID,
                getCheckSumDTO.getOrderId(),
                getCheckSumDTO.getCustId(),
                Constants.CHANNEL_ID,
                getCheckSumDTO.getTxnAmount(),
                Constants.WEBSITE,
                Constants.PAYTM_MERCHANT_KEY,
                Constants.CALLBACK_URL,
                Constants.INDUSTRY_TYPE_ID
        );

        checkSumHashDTOCall.enqueue(new Callback<CheckSumHashDTO>() {

            @Override
            public void onResponse(Call<CheckSumHashDTO> call, Response<CheckSumHashDTO> response) {
                    initializePaymentTransaction(response.body().getChecksumHash(), getCheckSumDTO);
            }

            @Override
            public void onFailure(Call<CheckSumHashDTO> call, Throwable t) {

            }
        });

    }

    private void initializePaymentTransaction(String checksumHash, GetCheckSumDTO getCheckSumDTO){

        PaytmPGService paytmPGService = PaytmPGService.getStagingService();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", Constants.M_ID);
        paramMap.put("ORDER_ID", getCheckSumDTO.getOrderId());
        paramMap.put("CUST_ID", getCheckSumDTO.getCustId());
        paramMap.put("CHANNEL_ID", getCheckSumDTO.getChannelId());
        paramMap.put("TXN_AMOUNT", getCheckSumDTO.getTxnAmount());
        paramMap.put("WEBSITE", getCheckSumDTO.getWebsite());
        paramMap.put("CALLBACK_URL", getCheckSumDTO.getCallBackUrl());
        paramMap.put("CHECKSUMHASH", checksumHash);
        paramMap.put("INDUSTRY_TYPE_ID", getCheckSumDTO.getIndustryTypeId());

        PaytmOrder paytmOrder = new PaytmOrder(paramMap);

        paytmPGService.initialize(paytmOrder,null);

        paytmPGService.startPaymentTransaction(context,true,true,this);

    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        System.out.println("I m onTransactionResponse : ");
        System.out.println(bundle.toString());
    }

    @Override
    public void networkNotAvailable() {
        System.out.println("Network Unavaible : ");
    }

    @Override
    public void clientAuthenticationFailed(String s) {

    }

    @Override
    public void someUIErrorOccurred(String s) {

    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        System.out.println("Error loading WebPage : ");
    }

    @Override
    public void onBackPressedCancelTransaction() {
        System.out.println("I m onBackPressed : ");
        System.out.println("Transaction cancelling ");
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        System.out.println("I m onTransactionCancel : ");
        System.out.println("String s is : "+s);
        System.out.println("Bundle 2 is  : "+ bundle.toString());

    }
}
