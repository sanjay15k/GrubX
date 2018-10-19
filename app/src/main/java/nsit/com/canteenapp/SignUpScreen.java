package nsit.com.canteenapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.SignUpDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.SignUpRetrofitCall;


public class SignUpScreen extends AppCompatActivity {

    private Button loginBtn;
    private TextInputEditText fullNameEditText;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText mobileNumberEditText;
    private TextInputEditText emailIdEditText;
    private Button signUpBtn;
    private SignUpDTO signUpDTO;
    private ImageView usernameImageView;
    private ImageView passwordImageView;
    private ImageView mobileNumberImageView;
    private ImageView emailIDImageView;
    private Intent intent;
    private LinearLayout signupScreenLayout;
    private ProgressDialog progressDialog;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);
        signupScreenLayout = findViewById(R.id.signupScreenLayout);
        loginBtn = findViewById(R.id.loginBtn);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        emailIdEditText = findViewById(R.id.emailIdEditText);
        signUpBtn = findViewById(R.id.signUpBtn);
        usernameImageView = findViewById(R.id.usernameImageView);
        passwordImageView =findViewById(R.id.passwordImageView);
        mobileNumberImageView = findViewById(R.id.mobileNumberImageView);
        emailIDImageView = findViewById(R.id.emailIDImageView);

        alertDialogBuilder = new AlertDialog.Builder(SignUpScreen.this);

        Glide.with(this).load(R.drawable.username_icon).into(usernameImageView);
        Glide.with(this).load(R.drawable.password_icon).into(passwordImageView);
        Glide.with(this).load(R.drawable.mobile_no_icon).into(mobileNumberImageView);
        Glide.with(this).load(R.drawable.email_id_icon).into(emailIDImageView);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),LogInScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    progressDialog = CommonUtilsClass.createProgressDialog(SignUpScreen.this,"Signing Up","Please wait for a while....");

                    signUpDTO = new SignUpDTO();
                    signUpDTO.setFullName(fullNameEditText.getText().toString());
                    signUpDTO.setUsername(usernameEditText.getText().toString());
                    signUpDTO.setPassword(passwordEditText.getText().toString());
                    signUpDTO.setMobileNumber(mobileNumberEditText.getText().toString());
                    signUpDTO.setEmailID(emailIdEditText.getText().toString());

                    SignUpRetrofitCall SignUpRetrofitCall = new SignUpRetrofitCall(signUpDTO,SignUpScreen.this,signupScreenLayout, progressDialog,alertDialogBuilder);
                    SignUpRetrofitCall.execute();

                    System.out.println("We are done");

            }
        });
    }
}
