package nsit.com.canteenapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import nsit.com.canteenapp.Adapters.HomeFeaturedItemsAdapter;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.HomeFeaturedItemsCall;


/**
 * Created by starhawk on 27/07/18.
 */

public class HomeFragment extends Fragment {


    private Toolbar toolBar;
    private CollapsingToolbarLayout collapsingToolBar;
    private View view;
    private RecyclerView homeItemsRecyclerView;
    private ProgressDialog progressDialog;
    private CoordinatorLayout coordinatorLayoutMain;
    private RelativeLayout homeRelativeLayout;
    private ImageView noItemsOrConnectionPrblmImageView;
    private TextView tapToConnectTextView;
    private Button refreshBtn;
    private boolean refreshBtnCreated;
    private ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList =new ArrayList<>();
    private TextView welcomeUserMessageTextView1;
    private TextView welcomeUserMessageTextView2;

    public boolean isNetworkConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    public void connected(){
        noItemsOrConnectionPrblmImageView.setImageResource(0);
        tapToConnectTextView.setText("");
        noItemsOrConnectionPrblmImageView.setClickable(false);
        homeRelativeLayout.removeView(refreshBtn);
        getFavouriteList();
        System.out.println("Array list is : "+favouritesItemDTOArrayList);
        HomeFeaturedItemsCall homeFeaturedItemsCall = new HomeFeaturedItemsCall(homeItemsRecyclerView, getActivity(), progressDialog, favouritesItemDTOArrayList, homeRelativeLayout);
        homeFeaturedItemsCall.execute();
    }

    public void notConnected(){
        if(!refreshBtnCreated) {
            createRefreshBtn();
            homeRelativeLayout.addView(refreshBtn);
        }
        Snackbar.make(coordinatorLayoutMain,"Please connect to internet",Snackbar.LENGTH_SHORT).show();
        homeItemsRecyclerView.setBackgroundColor(Color.parseColor("#E0E0E0"));
        noItemsOrConnectionPrblmImageView.setImageResource(R.drawable.internet_connection_unavailable_icon);
        noItemsOrConnectionPrblmImageView.setClickable(true);
        tapToConnectTextView.setText("Tap to connect to wifi");

        noItemsOrConnectionPrblmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected()){
                    refreshBtnCreated = false;
                    connected();
                }
                else {
                    refreshBtnCreated = true;
                    notConnected();
                }
            }
        });

    }

    public void createRefreshBtn(){
        System.out.println("I m creating btn");
        refreshBtn = new Button(getActivity());
        refreshBtn.setText("Refresh");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        refreshBtn.setLayoutParams(params);
        refreshBtn.setBackgroundColor(Color.BLACK);
        refreshBtn.setTextColor(Color.WHITE);
        params.addRule(RelativeLayout.BELOW,R.id.tapToConnectTextView);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        refreshBtn.setPadding(90,10,90,10);
        params.setMargins(0,20,0,0);
    }

    public void getFavouriteList(){
        try {
            File file = CommonUtilsClass.openFile(Objects.requireNonNull(getActivity()),"favourites.ser");
            if (!file.exists()){
                file.createNewFile();
            }
            favouritesItemDTOArrayList = CommonUtilsClass.readFromFile(file);
            if (favouritesItemDTOArrayList == null){
                favouritesItemDTOArrayList = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String[] getDay() {
        Calendar calendar = Calendar.getInstance();
        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            return new String[]{"Good Morning", "It's time for snacks"};
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            return new String[]{"Good Afternoon", "Its time for lunch"};
        } else if (timeOfDay >= 17 && timeOfDay <= 24) {
            return new String[]{"Good Evening", "It's time for dinner"};
        }
        return null;
    }

    public void setTopBannerMessage(){

        String timeOfDay[] = getDay();
        String fullName = ((MainActivity) Objects.requireNonNull(getActivity())).accountDetails().getFullName();
        String firstName = fullName;
        if (fullName.contains(" ")){
            System.out.println("Yes it CONTAINS *********");
            firstName = fullName.substring(0,fullName.indexOf(" "));
        }
        welcomeUserMessageTextView1.setText(timeOfDay[0]+" , "+ firstName);
        welcomeUserMessageTextView2.setText(timeOfDay[1]);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        coordinatorLayoutMain = view.findViewById(R.id.coordinatorLayoutMain);
        homeRelativeLayout = view.findViewById(R.id.homeRelativeLayout);
        toolBar = view.findViewById(R.id.toolBar);
        collapsingToolBar = view.findViewById(R.id.collapsingToolBar);
        homeItemsRecyclerView = view.findViewById(R.id.homeItemsRecyclerView);
        noItemsOrConnectionPrblmImageView = view.findViewById(R.id.noItemsOrConnectionPrblmImageView);
        tapToConnectTextView = view.findViewById(R.id.tapToConnectTextView);
        progressDialog = CommonUtilsClass.createProgressDialog(getActivity(),"Getting items list","Please wait while we load data for you");
        welcomeUserMessageTextView1 = view.findViewById(R.id.welcomeUserMessageTextView1);
        welcomeUserMessageTextView2 = view.findViewById(R.id.welcomeUserMessageTextView2);

        collapsingToolBar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolBar.setExpandedTitleColor(Color.TRANSPARENT);

        setTopBannerMessage();

        ((SimpleItemAnimator)homeItemsRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        if(isNetworkConnected()){
            connected();
        }
        else{
            notConnected();
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolBar);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_menu_bar, menu);
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("I m called onPause method");
        try {
            System.out.println("Size is : "+favouritesItemDTOArrayList);
            File file = CommonUtilsClass.openFile(getActivity(),"favourites.ser");
            CommonUtilsClass.writeToFile(file,favouritesItemDTOArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        HomeFeaturedItemsAdapter.setIsAnimationFirstTime(false);
    }
}
