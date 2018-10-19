package nsit.com.canteenapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import nsit.com.canteenapp.Adapters.CanteensCategoryItemsRecyclerViewAdapter;
import nsit.com.canteenapp.CommonUtils.CommonUtilsClass;
import nsit.com.canteenapp.DTO.CanteensCategoryItemsDTO;
import nsit.com.canteenapp.DTO.FavouritesItemDTO;
import nsit.com.canteenapp.DTO.LoginDTO;

public class CategoryItemsList extends AppCompatActivity {

    private RecyclerView categoryItemsRecyclerView;
    private ArrayList<CanteensCategoryItemsDTO> arrayList =new ArrayList<>();
    private CanteensCategoryItemsRecyclerViewAdapter canteensCategoryItemsRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String retrieveCategory;
    private ConstraintLayout canteenCategoryItemsLayout;
    private LoginDTO loginDTO;

    private ArrayList<FavouritesItemDTO> favouritesItemDTOArrayList;

    public void getFavouriteItems(){
        try {
            File file = CommonUtilsClass.openFile(this, "favourites.ser");
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

//    public void setArrayListForSancks(){
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Burger","10",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Pizza","30",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Noodles","50",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Pasta","20",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Samosa","10",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Chilly Potatoe","20",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Burger","25",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Pizza","99",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Maggie","45",false));
//    }
//
//    public void setArrayListForDrinks(){
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Coca Cola","35",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Pizza","45",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Noodles","30",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Pasta","40",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Samosa","50",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Chilly Potatoe","25",false));
//    }
//
//    public void setArrayListForSouthIndian(){
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Dosa","15",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Idli","30",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Noodles","25",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Pasta","70",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Burger","25",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Pizza","75",false));
//        arrayList.add(new CanteensCategoryItemsDTO("https://3a09223b3cd53870eeaa-7f75e5eb51943043279413a54aaa858a.ssl.cf3.rackcdn.com/6cc909516a2f6165f3b4df4a191a8510d5dbe696-1519639984-5a93ddb0-620x348.jpg","Maggie","40",false));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items_list);

        categoryItemsRecyclerView = findViewById(R.id.categoryItemsRecyclerView);
        canteenCategoryItemsLayout = findViewById(R.id.canteenCategoryItemsLayout);

        int width = CommonUtilsClass.getScreenWidth()/2;

        Intent intent = getIntent();
        retrieveCategory = intent.getStringExtra("Category");
        loginDTO = intent.getParcelableExtra("LoginDTO");
        arrayList = intent.getParcelableArrayListExtra("canteenItemsArrayList");
        String titleToSet=null;

        switch (retrieveCategory) {
            case "snacks":
                titleToSet = "Snacks";
                break;
            case "Drinks":
                titleToSet = "Drinks";
                break;
            case "southIndian":
                titleToSet = "South Indian";
                break;
            case "chineese":
                titleToSet = "Chineese";
                break;
            case "indian":
                titleToSet = "Indian";
                break;
            default:
                titleToSet = "Default";
        }
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(titleToSet);

//        if (retrieveCategory.equalsIgnoreCase("Snacks")){
//            setArrayListForSancks();
//        }
//        if (retrieveCategory.equalsIgnoreCase("Drinks")){
//            setArrayListForDrinks();
//        }
//        if (retrieveCategory.equalsIgnoreCase("South Indian")){
//            setArrayListForSouthIndian();
//        }

        getFavouriteItems();

//        if (favouritesItemDTOArrayList != null && favouritesItemDTOArrayList.size()>0) {
            canteensCategoryItemsRecyclerViewAdapter = new CanteensCategoryItemsRecyclerViewAdapter(arrayList, this, width, favouritesItemDTOArrayList, canteenCategoryItemsLayout);
//        }
        layoutManager = new GridLayoutManager(this,2);

        categoryItemsRecyclerView.setLayoutManager(layoutManager);
        categoryItemsRecyclerView.setAdapter(canteensCategoryItemsRecyclerViewAdapter);

        canteensCategoryItemsRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.add(1,32,100,"Cart").setIcon(R.drawable.cart_top_bar_icon).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case 32 :
                Intent intent = new Intent(this,MyCart.class);
                intent.putExtra("LoginDTO",loginDTO);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            System.out.println("Size is : "+favouritesItemDTOArrayList);
            File file = CommonUtilsClass.openFile(this,"favourites.ser");
            CommonUtilsClass.writeToFile(file,favouritesItemDTOArrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
