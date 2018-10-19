package nsit.com.canteenapp.Retrofit.ApiInterface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import org.json.JSONArray;

import java.util.ArrayList;

import nsit.com.canteenapp.DTO.CanteensCategoryItemsDTO;
import nsit.com.canteenapp.DTO.HomeFeaturedItemsDTO;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.DTO.MyCartDTO;
import nsit.com.canteenapp.DTO.SignUpDTO;
import nsit.com.canteenapp.PaytmIntegration.DTO.CheckSumHashDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by starhawk on 31/07/18.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register")
    Call<SignUpDTO> registerUser(@Field("username") String username, @Field("email")
            String email, @Field("name") String name, @Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("login")
    Call<LoginDTO> loginUser(@Field("username") String username, @Field("password") String password);

    @GET("items")
    Call<ArrayList<HomeFeaturedItemsDTO>> getFeaturedItems();

    @POST("newOrder")
    Call<JsonObject> placeOrder(@Body MyCartDTO myCartDTO);

    @FormUrlEncoded
    @POST("/checksumcreate-android")
    Call<CheckSumHashDTO> getChecksum(
            @Field("MID") String mId,
            @Field("ORDER_ID") String orderId,
            @Field("CUST_ID") String custId,
            @Field("CHANNEL_ID") String channelId,
            @Field("TXN_AMOUNT") String txnAmount,
            @Field("WEBSITE") String website,
            @Field("PAYTM_MERCHANT_KEY") String merchantKey,
            @Field("CALLBACK_URL") String callbackUrl,
            @Field("INDUSTRY_TYPE_ID") String industryTypeId
    );

    @FormUrlEncoded
    @POST("showOrders")
    Call<JsonArray> getMyOrders(@Field("username") String username);

    @FormUrlEncoded
    @POST("cancel")
    Call<JsonObject> cancelMyOrder(@Field("id") String id);

    @FormUrlEncoded
    @POST("activate")
    Call<ResponseBody> activateUser(@Field("username") String username);

    @FormUrlEncoded
    @POST("resendOtp")
    Call<JsonObject> resendOTP(@Field("username") String username);

    @GET("offers")
    Call<JsonArray> getOffers();

    @FormUrlEncoded
    @POST("resetOTP")
    Call<JsonPrimitive> changePasswordOTP(@Field("username") String username);

    @FormUrlEncoded
    @POST("resetPassword")
    Call<ResponseBody> newPassword(@Field("username") String username, @Field("newPassword") String newPassword);

    @GET(".")
    Call<ArrayList<CanteensCategoryItemsDTO>> getCanteensCategoryItems(@Query("category") String category);

//    @GET("MiniZayca")
//    Call<JsonArray> getMiniZaycaItems(@Query("category") String category);
//
//    @GET("JC")
//    Call<JsonArray> getJustCafeItems(@Query("category") String category);
//
//    @GET("Amul")
//    Call<JsonArray> getAmulItems(@Query("category") String category);

}
