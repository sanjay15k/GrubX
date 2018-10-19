package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import nsit.com.canteenapp.DTO.SignUpDTO;
import nsit.com.canteenapp.LogInScreen;
import nsit.com.canteenapp.R;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import nsit.com.canteenapp.SignUpScreen;
import nsit.com.canteenapp.SmsOTPVerification;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by starhawk on 31/07/18.
 */

public class SignUpRetrofitCall extends AsyncTask<Void,Void,Void>{

    private String username;
    private String email;
    private String name;
    private String phone;
    private String password;
    private Context context;
    private LinearLayout linearLayout;
    private retrofit2.Response<SignUpDTO> response;
    private Snackbar snackbar;
    private Activity signUpScreenActivity;
    private ProgressDialog progressDialog;
    private Handler handler;
    private AlertDialog.Builder alertDialogBuilder;


    public SignUpRetrofitCall(SignUpDTO signUpDTO, Activity signUpScreenActivity, LinearLayout linearLayout, ProgressDialog progressDialog, AlertDialog.Builder alertDialogBuilder){
        username = signUpDTO.getUsername();
        email = signUpDTO.getEmailID();
        name = signUpDTO.getFullName();
        phone = signUpDTO.getMobileNumber();
        password = signUpDTO.getPassword();
        this.signUpScreenActivity = signUpScreenActivity;
        this.context = signUpScreenActivity.getApplicationContext();
        this.linearLayout = linearLayout;
        this.progressDialog = progressDialog;
        this.alertDialogBuilder = alertDialogBuilder;
    }

    public void designSnackBar(String message, int length){
        snackbar = Snackbar.make(linearLayout,message,length);

        snackbar.setActionTextColor(Color.RED);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        handler = new Handler(context.getMainLooper());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://authenticate.grubx.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);


        Call<SignUpDTO> makeCall = apiInterface.registerUser(username,email,name,phone,password);

        try {
            response = makeCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"No Internet Connection",Toast.LENGTH_LONG).show();
                }
            });

        }
        System.out.println("Response is :"+response);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {


        progressDialog.dismiss();
        if(response != null) {

            if (response.code() == 200 && response.body() != null) {

                Intent intent = new Intent(context, SmsOTPVerification.class);
                intent.putExtra("otpRecieved",response.body().getOtp());
                intent.putExtra("username",response.body().getUsername());
                intent.putExtra("phoneNumber",response.body().getMobileNumber());
                context.startActivity(intent);
                EditText fullName = signUpScreenActivity.findViewById(R.id.fullNameEditText);
                EditText username = signUpScreenActivity.findViewById(R.id.usernameEditText);
                EditText password = signUpScreenActivity.findViewById(R.id.passwordEditText);
                EditText mobileNumber = signUpScreenActivity.findViewById(R.id.mobileNumberEditText);
                EditText emailID = signUpScreenActivity.findViewById(R.id.emailIdEditText);
                fullName.setText("");
                username.setText("");
                password.setText("");
                mobileNumber.setText("");
                emailID.setText("");
                fullName.requestFocus();
            }

            if (response.code() == 400) {
                try {
                    if (response.errorBody() != null) {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        designSnackBar("Choose another username\n" + jsonObject.getString("message"), Snackbar.LENGTH_LONG);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                snackbar.show();
            }
        }
    }
}
