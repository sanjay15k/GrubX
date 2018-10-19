package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.MainActivity;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import nsit.com.canteenapp.SmsOTPVerification;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by starhawk on 01/08/18.
 */

public class LoginRetrofitCall extends AsyncTask<Void,Void,Void>{

    private LoginDTO loginDTO;
    private Activity loginActivity;
    private Context context;
    private ConstraintLayout loginScreenParentLayout;
    private ProgressDialog progressDialog;
    private LoginDTO loginDTOResponse;
    private Response<LoginDTO> response;
    private boolean isChecked;

    public void storeLoginDetails(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("LoginData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username",loginDTO.getUsername());
        editor.putString("password",loginDTO.getPassword());
        editor.commit();
    }

    public LoginRetrofitCall(LoginDTO loginDTO, Activity loginActivity, Context context, ProgressDialog progressDialog, boolean isChecked, ConstraintLayout loginScreenParentLayout){
        this.loginDTO = loginDTO;
        this.loginActivity = loginActivity;
        this.context = context;
        this.progressDialog = progressDialog;
        this.isChecked = isChecked;
        this.loginScreenParentLayout = loginScreenParentLayout;
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

        Call<LoginDTO> loginCall = apiInterface.loginUser(loginDTO.getUsername(), loginDTO.getPassword());

        try {
            response = loginCall.execute();
            loginDTOResponse = response.body();
            String message = response.toString();
            System.out.println("Message is "+message);
            if(loginDTOResponse != null) {
                System.out.println("Fullname is : " + loginDTOResponse.getFullName());
                System.out.println("Email is " + loginDTOResponse.getEmail());
                System.out.println("Mobile numebr is " + loginDTOResponse.getMobileNumber());
                System.out.println("Password is " + loginDTOResponse.getPassword());
                System.out.println("Username is " + loginDTOResponse.getUsername());
                System.out.println("IsMobileVerified is " + loginDTOResponse.isMobileVerified());
            }

            System.out.println("Code is : "+response.code());

        }
        catch (UnknownHostException e1){
            Snackbar.make(loginScreenParentLayout,"Please Connect to internet and retry",Snackbar.LENGTH_SHORT).show();
        }
        catch (SocketTimeoutException e2){
            Snackbar.make(loginScreenParentLayout,"Timeout Occured. Please check your internet or try again",Snackbar.LENGTH_SHORT).show();
        }
        catch (SocketException e3){
            Snackbar.make(loginScreenParentLayout,"Please Check your internet connection.",Snackbar.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        if(loginDTOResponse != null && response.code() == 200){
            if(!loginDTOResponse.isMobileVerified()){
                System.out.println(loginDTOResponse.isMobileVerified());

                final Snackbar snackbar = Snackbar.make(loginScreenParentLayout,"Your mobile number is not verified",Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Verify Now", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        new GetSMS().execute(loginDTOResponse.getUsername());
                    }
                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();

//                Toast.makeText(context,"Mobile Not Verified!",Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, SmsOTPVerification.class);
//                intent.putExtra("otpRecieved",response.body());
//                context.startActivity(intent);
            }
            else{
                if (isChecked) {
                    storeLoginDetails();
                }
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("LoginDTO",loginDTOResponse);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
        else if (response != null && (response.code() == 502 || response.code() == 403)){
            Snackbar.make(loginScreenParentLayout,"Some Server Issue Occurred! Please Try Again",Snackbar.LENGTH_SHORT).show();
        }
        else if (response != null && response.code() == 401){
            Snackbar.make(loginScreenParentLayout,"Incorrect username and / or password! Try Again",Snackbar.LENGTH_SHORT).show();
        }
        else if (response != null && response.code() == 400){
            Snackbar.make(loginScreenParentLayout,"Empty ID or password! Try Again",Snackbar.LENGTH_SHORT).show();
        }
    }

    public class GetSMS extends AsyncTask<String,Void,Void>{

        private ProgressDialog progressDialog;
        private JsonObject jsonObject;

        public GetSMS() {
            progressDialog = CommonUtilsClass.createProgressDialog(loginActivity,"Sending OTP","Please wait..");
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            System.out.println("***1");
        }

        @Override
        protected Void doInBackground(String... strings) {

            System.out.println("***2");
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://authenticate.grubx.in/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<JsonObject> getOTP = apiInterface.resendOTP(strings[0]);

            try {
                jsonObject = getOTP.execute().body();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            if (jsonObject != null) {
                Intent intent = new Intent(context, SmsOTPVerification.class);
                intent.putExtra("otpRecieved",jsonObject.get("OTP").getAsString());
                intent.putExtra("username",jsonObject.get("username").getAsString());
                intent.putExtra("phoneNumber",jsonObject.get("phoneNumber").getAsString());
                context.startActivity(intent);
            }
        }
    }

}
