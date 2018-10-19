package nsit.com.canteenapp;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nsit.com.canteenapp.Adapters.FavouritesRecyclerViewAdapter;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.Helper.RecyclerItemTouchHelper;

/**
 * Created by starhawk on 30/07/18.
 */

public class FavouritesFragment extends Fragment  implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    private View view;
    private RecyclerView swipeRecyclerView;
    private FavouritesRecyclerViewAdapter favouritesRecyclerViewAdapter;
    private ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList;
    private Toolbar favouritesToolbar;
    private ImageView emptyFavList;
    private int width;
    private AppCompatActivity activity;
    private RelativeLayout favouriteFragmentParentLayout;
    private CoordinatorLayout favouritesCoordinatorLayout;


    public void getFavouriteItems(){
        try {
            File file = CommonUtilsClass.openFile(getActivity(), "favourites.ser");
            if (file.exists()) {
                favouritesItemDTOArrayList = CommonUtilsClass.readFromFile(file);
            }
            if (favouritesItemDTOArrayList == null) {
                favouritesItemDTOArrayList = new ArrayList<>();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.favourites_fragment,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        favouritesToolbar = view.findViewById(R.id.favouritesToolbar);
        emptyFavList = view.findViewById(R.id.emptyFavList);
        favouriteFragmentParentLayout = view.findViewById(R.id.favouriteFragmentParentLayout);
        favouritesCoordinatorLayout = view.findViewById(R.id.favouritesCoordinatorLayout);

        favouritesToolbar.setTitle("Favourite Items");
        favouritesToolbar.setTitleTextColor(Color.WHITE);
        favouritesToolbar.setBackgroundColor(Color.parseColor("#4CAF50"));

        setHasOptionsMenu(true);
        activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(favouritesToolbar);
        }

        width = CommonUtilsClass.getScreenWidth()/2;

        swipeRecyclerView = view.findViewById(R.id.swipeRecyclerView);

        swipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(swipeRecyclerView);

        new MyTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu_bar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            swipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        }
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            swipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        }
    }

    public void showEmptyMessage(){
        emptyFavList.setImageResource(R.drawable.empty_fav_list_icon);
        RelativeLayout.LayoutParams nothinHereMsgLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams saveItemMsgLayoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView nothinHereMsg = new TextView(getActivity());
        TextView saveItemsMsg = new TextView(getActivity());

        nothinHereMsg.setText("Nothing Here");
        nothinHereMsg.setId(R.id.notingHereMsgTextView);
        nothinHereMsg.setGravity(Gravity.CENTER);
        nothinHereMsgLayoutParams.addRule(RelativeLayout.BELOW,R.id.emptyFavList);
        nothinHereMsg.setLayoutParams(nothinHereMsgLayoutParams);
        nothinHereMsgLayoutParams.setMargins(0,60,0,0);
        nothinHereMsg.setTextSize(17);
        nothinHereMsg.setTypeface(Typeface.DEFAULT_BOLD);
        favouriteFragmentParentLayout.addView(nothinHereMsg);

        saveItemsMsg.setText("Save items while you order and add them to your bag later!");
        saveItemMsgLayoutParams1.addRule(RelativeLayout.BELOW,R.id.notingHereMsgTextView);
        saveItemsMsg.setGravity(Gravity.CENTER);
        saveItemMsgLayoutParams1.setMargins(0,50,0,100);
        saveItemsMsg.setLayoutParams(saveItemMsgLayoutParams1);
        favouriteFragmentParentLayout.addView(saveItemsMsg);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof FavouritesRecyclerViewAdapter.ViewHolder) {

            favouritesRecyclerViewAdapter.removeItem(viewHolder.getAdapterPosition());
            if(favouritesItemDTOArrayList.size() == 0){
                showEmptyMessage();
            }

        }
    }

    public class MyTask extends AsyncTask<Void,Void,Void>{

        ProgressDialog progressDialog;

        MyTask(){
            progressDialog = CommonUtilsClass.createProgressDialog(getActivity(),"Getting favourites list","Please wait for a while");
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Handler handler = new Handler(getActivity().getMainLooper());
            getFavouriteItems();
            System.out.println("Hey "+favouritesItemDTOArrayList);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (favouritesItemDTOArrayList != null && favouritesItemDTOArrayList.size() > 0) {
                        favouritesRecyclerViewAdapter = new FavouritesRecyclerViewAdapter(favouritesItemDTOArrayList, getActivity(), width, favouritesCoordinatorLayout);
                        swipeRecyclerView.setAdapter(favouritesRecyclerViewAdapter);
                    } else {
                        showEmptyMessage();
                    }
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            System.out.println("One is : "+favouritesItemDTOArrayList.size());
            File file = CommonUtilsClass.openFile(getActivity(),"favourites.ser");
            CommonUtilsClass.writeToFile(file,favouritesItemDTOArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
