package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.IOException;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import nsit.com.canteenapp.SmsOTPVerification;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResetPasswordOTPCall extends AsyncTask<Void,Void,Void> {

    private String username;
    private ProgressDialog progressDialog;
    private Context context;
    private String otpRecieved;

    public ResetPasswordOTPCall(String username, Context context) {
        this.username = username;
        this.context = context;
        progressDialog = CommonUtilsClass.createProgressDialog(context,"Sending OTP","Please wait...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://authenticate.grubx.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<JsonPrimitive> changePasswordOTPCall = apiInterface.changePasswordOTP(username);

        try {
            JsonPrimitive jsonPrimitive = changePasswordOTPCall.execute().body();
            if (jsonPrimitive != null) {
                otpRecieved = jsonPrimitive.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        Intent intent = new Intent(context,SmsOTPVerification.class);
        intent.putExtra("forgotPasswordActivity",true);
        intent.putExtra("otpRecieved",otpRecieved);
        intent.putExtra("username",username);
        context.startActivity(intent);
    }

}
