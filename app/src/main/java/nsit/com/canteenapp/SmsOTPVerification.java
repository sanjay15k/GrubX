package nsit.com.canteenapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.ResendOTPCall;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SmsOTPVerification extends AppCompatActivity {

    private ImageView submitOTPImageView;
    private EditText otpEnteredEditText;
    private TextView textView2;
    private TextView resendOTPTextView;
    private String otpRecieved;
    private TextView headingTextView;
    private boolean isForgotPasswordActivity;

    public void setOtpRecieved(String otpRecieved) {
        this.otpRecieved = otpRecieved;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_otpverification);

        submitOTPImageView = findViewById(R.id.submitOTPImageView);
        otpEnteredEditText = findViewById(R.id.otpEnteredEditText);
        resendOTPTextView = findViewById(R.id.resendOTPTextView);
        textView2 = findViewById(R.id.textView2);
        headingTextView = findViewById(R.id.headingTextView);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        otpRecieved = intent.getStringExtra("otpRecieved");
        String phoneNumber = intent.getStringExtra("phoneNumber");
        isForgotPasswordActivity = intent.getBooleanExtra("forgotPasswordActivity",false);

        String phoneTextView = "We have sent you an OTP via SMS on +91-" + phoneNumber;

        if (isForgotPasswordActivity){
            headingTextView.setText("Forgot Password");
            textView2.setText("We have sent you an OTP via SMS on registered mobile number");
        }
        else{
            headingTextView.setText("Mobile Number Verification");
            textView2.setText(phoneTextView);
        }

        submitOTPImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEnteredOTP = String.valueOf(otpEnteredEditText.getText());
                if (isForgotPasswordActivity){
                    if (userEnteredOTP.contentEquals(otpRecieved)){
                        Intent intent = new Intent(SmsOTPVerification.this, NewPassword.class);
                        intent.putExtra("username",username);
                        startActivity(intent);
                        otpEnteredEditText.setText("");
                    }
                    else{
                        Snackbar.make(findViewById(R.id.layout_root),"OTP Didn't matched.Try entering again",Snackbar.LENGTH_SHORT).show();
                    }
                }
                else{
                    if (userEnteredOTP.contentEquals(otpRecieved)){
                        new SetUserVerified().execute(username);
                    }
                    else{
                        Snackbar.make(findViewById(R.id.layout_root),"OTP Didn't matched.Try entering again",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        resendOTPTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendOTPCall resendOTPCall = new ResendOTPCall(username,SmsOTPVerification.this);
                resendOTPCall.execute();
            }
        });
    }

    public class SetUserVerified extends AsyncTask<String,Void,Void>{

        ProgressDialog progressDialog = CommonUtilsClass.createProgressDialog(SmsOTPVerification.this,"Mobile Verification","Please wait while we verify your mobile number");

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            String username = strings[0];

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://authenticate.grubx.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);

            Call<ResponseBody> makeUserVerified = apiInterface.activateUser(username);

            try {
                makeUserVerified.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            Intent intent = new Intent(SmsOTPVerification.this, LogInScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
