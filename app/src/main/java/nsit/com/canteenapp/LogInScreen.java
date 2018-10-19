package nsit.com.canteenapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Objects;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.IntroScreens.IntroScreen;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.LoginRetrofitCall;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.ResetPasswordOTPCall;

public class LogInScreen extends AppCompatActivity {

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private String username;
    private String password;
    private ConstraintLayout loginScreenParentLayout;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        loginScreenParentLayout = findViewById(R.id.loginScreenParentLayout);
        ImageView usernameImageView = findViewById(R.id.usernameImageView);
        ImageView passwordImageView = findViewById(R.id.passwordImageView);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        TextView forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        Button loginBtn = findViewById(R.id.loginBtn);
        Button signUpBtn = findViewById(R.id.signUpBtn);


        Glide.with(this).load(R.drawable.username_icon).into(usernameImageView);
        Glide.with(this).load(R.drawable.password_icon).into(passwordImageView);

        System.out.println("I m login screen");

        SharedPreferences sharedPreferences = this.getSharedPreferences("OnBoardPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        System.out.println("Value is : "+ sharedPreferences.getBoolean("isFirstTime",false));

        if (sharedPreferences.getBoolean("isFirstTime",true)){
            editor.remove("isFirstTime");
            editor.putBoolean("isFirstTime",false);
            editor.commit();
            startActivity(new Intent(this, IntroScreen.class));
        }

        SharedPreferences loginSharedPreference = this.getSharedPreferences("LoginData",MODE_PRIVATE);
        if (loginSharedPreference.contains("username")){
            username = loginSharedPreference.getString("username",null);
            password = loginSharedPreference.getString("password",null);

            usernameEditText.setText(username);
            passwordEditText.setText(password);

            logUserIn();
        }

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Objects.requireNonNull(usernameEditText.getText()).toString().trim();
                if (username.isEmpty()){
                    Toast toast = Toast.makeText(LogInScreen.this,"Enter valid username to reset password",Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    if (CommonUtilsClass.isNetworkConnected(LogInScreen.this)){
                        ResetPasswordOTPCall resetPasswordOTPCall = new ResetPasswordOTPCall(username,LogInScreen.this);
                        resetPasswordOTPCall.execute();
                    }
                    else{
                        Snackbar.make(loginScreenParentLayout,"No Internet Connection!",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = Objects.requireNonNull(usernameEditText.getText()).toString().intern();
                password = Objects.requireNonNull(passwordEditText.getText()).toString();

                if (!username.isEmpty() && !password.isEmpty()){
                    logUserIn();
                }
                else{
                    Snackbar.make(loginScreenParentLayout,"Field cannot be empty!",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public void logUserIn(){
        ProgressDialog progressDialog = CommonUtilsClass.createProgressDialog(LogInScreen.this, "Logging In", "Please wait for a while....");
        LoginDTO loginDTO = new LoginDTO(username.trim(),password.trim());

        LoginRetrofitCall loginRetrofitCall = new LoginRetrofitCall(loginDTO,LogInScreen.this,getApplicationContext(), progressDialog, rememberMeCheckBox.isChecked(), loginScreenParentLayout);
        loginRetrofitCall.execute();
    }

}
