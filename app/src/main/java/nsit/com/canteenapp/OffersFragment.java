package nsit.com.canteenapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import nsit.com.canteenapp.Adapters.OffersAdapter;
import nsit.com.canteenapp.DTO.LoginDTO;
import nsit.com.canteenapp.DTO.OffersDTO;
import nsit.com.canteenapp.Retrofit.ApiInterface.Calls.GetOffersCall;

public class OffersFragment extends Fragment {

    private Toolbar offersToolbar;
    private RecyclerView offersRecyclerView;
    private View view;
    private NestedScrollView nestedScrollViewOffers;
    private LoginDTO loginDTO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.offers_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        offersToolbar = view.findViewById(R.id.offersToolbar);
        offersRecyclerView = view.findViewById(R.id.offersRecyclerView);
        nestedScrollViewOffers = view.findViewById(R.id.nestedScrollViewOffers);

        offersToolbar.setTitle("Offers");
        offersToolbar.setTitleTextColor(Color.WHITE);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(offersToolbar);
        }

        loginDTO = ((MainActivity)getActivity()).accountDetails();

        System.out.println("I calling GetOffersCall");
        GetOffersCall getOffersCall = new GetOffersCall(getActivity(),offersRecyclerView,nestedScrollViewOffers,loginDTO);
        getOffersCall.execute();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.top_menu_bar, menu);
    }

}
