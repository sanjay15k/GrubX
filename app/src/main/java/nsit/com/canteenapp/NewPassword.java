package nsit.com.canteenapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Objects;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.ChangePasswordFinalCall;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.ResetPasswordOTPCall;

public class NewPassword extends AppCompatActivity {

    private TextInputEditText newPasswordTextInputEditText;
    private TextInputEditText confirmPasswordTextInputEditText;
    private Button changePasswordButton;
    private String username;
    private LinearLayout newPasswordRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = getIntent().getStringExtra("username");

        newPasswordTextInputEditText = findViewById(R.id.newPasswordTextInputEditText);
        confirmPasswordTextInputEditText = findViewById(R.id.confirmPasswordTextInputEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        newPasswordRootLayout = findViewById(R.id.newPasswordRootLayout);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordTextInputEditText.getText().toString();
                String confirmPassword = confirmPasswordTextInputEditText.getText().toString();

                if (newPassword.equals(confirmPassword)){
                    if (CommonUtilsClass.isNetworkConnected(NewPassword.this)){
                        ChangePasswordFinalCall changePasswordFinalCall = new ChangePasswordFinalCall(username,newPassword,NewPassword.this);
                        changePasswordFinalCall.execute();
                    }
                    else{
                        Snackbar.make(newPasswordRootLayout,"No Internet Connection!",Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Snackbar.make(newPasswordRootLayout,"Password didn't match!",Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }
}
