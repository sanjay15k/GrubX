package nsit.com.canteenapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.ResetPasswordOTPCall;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by starhawk on 28/07/18.
 */

public class AccountFragment extends Fragment {

    private Toolbar toolBar;
    private ImageView userProfileImageView;
    private ImageView userProfileImageView2;
    private TextView userFullNameTextView;
    private TextView accountUsernameTextView;
    private TextView accountEmailIDTextView;
    private TextView accountPhoneNumberTextView;
    private LinearLayout changePasswordView;
    private LinearLayout checkRecentOrdersView;
    private LinearLayout checkPastOrdersView;
    private LinearLayout logoutView;
    private LoginDTO loginDTO;
    private ProgressDialog logOutProgressDialog;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.account_fragment,null);
        userProfileImageView = view.findViewById(R.id.userProfileImageView);
        userProfileImageView2 = view.findViewById(R.id.userProfileImageView2);
        userFullNameTextView = view.findViewById(R.id.userFullNameTextView);
        accountUsernameTextView = view.findViewById(R.id.accountUsernameTextView);
        accountEmailIDTextView = view.findViewById(R.id.accountEmailIDTextView);
        accountPhoneNumberTextView = view.findViewById(R.id.accountPhoneNumberTextView);
        changePasswordView = view.findViewById(R.id.changePasswordView);
        checkRecentOrdersView = view.findViewById(R.id.checkRecentOrdersView);
        checkPastOrdersView = view.findViewById(R.id.checkPastOrdersView);
        logoutView = view.findViewById(R.id.logoutView);
        System.out.println("I m account ******************");

        CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.user_account_profile_image_icon, userProfileImageView,getActivity());
        CommonUtilsClass.loadingBackgroundImagesToImageView(R.drawable.user_account_profile_image_icon,userProfileImageView2,getActivity());
//        CommonUtilsClass statusBarColor = new CommonUtilsClass();
//        Window window = getActivity().getWindow();
//        statusBarColor.updateStatusBarColor("#2196F3",window);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        loginDTO = ((MainActivity) Objects.requireNonNull(getContext())).accountDetails();

        toolBar = view.findViewById(R.id.accountToolBar);
        toolBar.setTitle("My Account");
        toolBar.setTitleTextColor(Color.WHITE);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolBar);
        }

        userFullNameTextView.setText(loginDTO.getFullName());
        accountUsernameTextView.setText(loginDTO.getUsername());
        accountEmailIDTextView.setText(loginDTO.getEmail());
        accountPhoneNumberTextView.setText(loginDTO.getMobileNumber());
        System.out.println("Hey my username is : "+loginDTO.getUsername());

        changePasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordOTPCall resetPasswordOTPCall = new ResetPasswordOTPCall(loginDTO.getUsername(),getActivity());
                resetPasswordOTPCall.execute();
            }
        });

        checkRecentOrdersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyRecentOrders.class);
                intent.putExtra("LoginDTO",loginDTO);
                startActivity(intent);
            }
        });

        checkPastOrdersView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyPastOrders.class);
                intent.putExtra("LoginDTO",loginDTO);
                startActivity(intent);
            }
        });

        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("I m clicked 1");
                logOutProgressDialog = CommonUtilsClass.createProgressDialog(getActivity(),"","Please wait");
                logOutProgressDialog.setCancelable(false);
                logOutProgressDialog.show();
                SharedPreferences loginSharedPreference = getActivity().getSharedPreferences("LoginData",MODE_PRIVATE);
                if (loginSharedPreference.contains("username")){
                    System.out.println("I m inside");
                    loginSharedPreference.edit().clear().commit();
                }
                Handler handler = new Handler(getActivity().getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logOutProgressDialog.dismiss();
                        Intent intent = new Intent(getActivity(),LogInScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                },2500);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_menu_bar, menu);
    }

}
