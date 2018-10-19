package nsit.com.canteenapp.Retrofit.ApiInterface.Calls;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.LogInScreen;
import nsit.com.canteenapp.Retrofit.ApiInterface.ApiInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class ChangePasswordFinalCall extends AsyncTask<Void,Void,Void> {

    private ProgressDialog progressDialog;
    private Context context;
    private String username;
    private String newPassword;
    private int statusCode;

    public ChangePasswordFinalCall(String username, String newPassword, Context context){
        this.username = username;
        this.newPassword = newPassword;
        this.context = context;
        progressDialog = CommonUtilsClass.createProgressDialog(context,"","Please wait while we reset your password.");
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

        Call<ResponseBody> changePasswordOTPCall = apiInterface.newPassword(username,newPassword);

        try {
            statusCode = changePasswordOTPCall.execute().code();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        progressDialog.dismiss();
        if (statusCode == 200){
            SharedPreferences loginSharedPreference = context.getSharedPreferences("LoginData",MODE_PRIVATE);
            SharedPreferences.Editor editor = loginSharedPreference.edit();
            if (loginSharedPreference.contains("username")){
                editor.remove("username");
                editor.remove("password");
                editor.commit();
            }
            Intent intent = new Intent(context,LogInScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
        else{
            Toast.makeText(context,"Some problem occurred, Please try resetting password again",Toast.LENGTH_LONG).show();
        }
    }


}
