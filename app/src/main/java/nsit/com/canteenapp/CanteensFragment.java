package nsit.com.canteenapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Objects;

import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;

/**
 * Created by starhawk on 28/07/18.
 */

public class CanteensFragment extends Fragment {

    private View view;
    private Intent intent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.canteens_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        Toolbar canteensToolbar = view.findViewById(R.id.canteensToolbar);
        LinearLayout mcCainCanteenLayout = view.findViewById(R.id.mcCainCanteenLayout);
        LinearLayout miniZaycaCanteenLayout = view.findViewById(R.id.miniZaycaCanteenLayout);
        LinearLayout justCafeCanteenLayout = view.findViewById(R.id.justCafeCanteenLayout);
        LinearLayout amulCanteenLayout = view.findViewById(R.id.amulCanteenLayout);

        canteensToolbar.setTitle("Canteens");
        canteensToolbar.setTitleTextColor(Color.WHITE);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(canteensToolbar);
        }

        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mc_cain_canteens_background, mcCainCanteenLayout,getActivity());
        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mini_zayca_canteens_background, miniZaycaCanteenLayout,getActivity());
        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.just_cafe_canteens_background, justCafeCanteenLayout,getActivity());
        CommonUtilsClass.loadingBackgroundImagesToLayout(R.drawable.mc_cain_canteens_background, amulCanteenLayout,getActivity());

        mcCainCanteenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),McCainCanteenItemsCategory.class);
                intent.putExtra("LoginDTO",((MainActivity) Objects.requireNonNull(getContext())).accountDetails());
                startActivity(intent);
            }
        });

        miniZaycaCanteenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),MiniZaycaCanteenItemsCategory.class);
                intent.putExtra("LoginDTO",((MainActivity) Objects.requireNonNull(getContext())).accountDetails());
                startActivity(intent);
            }
        });

        justCafeCanteenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),JustCafeCanteenItemsCategory.class);
                intent.putExtra("LoginDTO",((MainActivity) Objects.requireNonNull(getContext())).accountDetails());
                startActivity(intent);
            }
        });

        amulCanteenLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),McCainCanteenItemsCategory.class);
                intent.putExtra("LoginDTO",((MainActivity) Objects.requireNonNull(getContext())).accountDetails());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_bar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


}
