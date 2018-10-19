package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import nsit.com.canteenapp.SmsOTPVerification;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResendOTPCall extends AsyncTask<Void, Void, Void> {

    private String username;
    private ProgressDialog progressDialog;
    private Context context;

    public ResendOTPCall(String username, Context context) {
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

        Call<JsonObject> resendOTPCall = apiInterface.resendOTP(username);

        try {
            JsonObject jsonObject = resendOTPCall.execute().body();
            System.out.println(jsonObject);
            if (jsonObject!=null){
                ((SmsOTPVerification)context).setOtpRecieved(jsonObject.get("OTP").getAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        Toast.makeText(context,"OTP Sent!",Toast.LENGTH_SHORT).show();
    }
}
