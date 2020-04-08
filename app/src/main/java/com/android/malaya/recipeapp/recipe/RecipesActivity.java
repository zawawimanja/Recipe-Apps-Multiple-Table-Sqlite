package com.android.malaya.recipeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.malaya.recipeapp.category.Category;
import com.android.malaya.recipeapp.category.CategoryActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.android.malaya.recipeapp.Adapter.DBHelper;
import com.android.malaya.recipeapp.Adapter.Recipe;
import com.android.malaya.recipeapp.Adapter.RecyclerRecipeAdapter;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spintype;
    private FloatingActionButton fabadd;
    private RecyclerView recyclertype;
    private TextView txtrecipealert;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private RecyclerRecipeAdapter rra;

    private static final String AD_UNIT_ID = "ca-app-pub-9046598316825555/2854806318";
   //private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";

    private InterstitialAd interstitialAd;
    private String id,name,type;

    int ads=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        spintype = findViewById(R.id.typespinner);
        fabadd = findViewById(R.id.fabadd);
        recyclertype = findViewById(R.id.recylertype);
        txtrecipealert = findViewById(R.id.txtrecipes);

        Bundle data = getIntent().getExtras();
        if (data != null){
            id = data.getString("ID");
            name = data.getString("Name");
            type = data.getString("Type");


        }




        //inflate data into spinner R.array.types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.types,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(adapter);
        SetUpRecycler();

        GetRecipe(type);
   //  GetRecipeAll();

        spintype.setOnItemSelectedListener(this);
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipesActivity.this,AddRecipeActivity.class));
            }
        });


//        // Initialize the Mobile Ads SDK.
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//
//        // Create the InterstitialAd and set the adUnitId.
//        interstitialAd = new InterstitialAd(this);
//        // Defined in res/values/strings.xml
//        interstitialAd.setAdUnitId(AD_UNIT_ID);
//
//        interstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//                Toast.makeText(RecipesActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
//
//
//                        showInterstitial();
//
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                Toast.makeText(RecipesActivity.this,
//                        "onAdFailedToLoad() with error code: " + errorCode,
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onAdClosed() {
//
//            }
//        });
//
//
//
//            startGame();
//
//            ads++;
//


    }

//    private void startGame() {
//        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
//        if (!interstitialAd.isLoading() && !interstitialAd.isLoaded()) {
//            AdRequest adRequest = new AdRequest.Builder().build();
//            interstitialAd.loadAd(adRequest);
//        }
//
//    }
//
//
//    private void showInterstitial() {
//        // Show the ad if it's ready. Otherwise toast and restart the game.
//        if (interstitialAd != null && interstitialAd.isLoaded()) {
//            interstitialAd.show();
//        } else {
//            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
//            startGame();
//        }
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RecipesActivity.this, CategoryActivity.class));

    }

    //spinner selected
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0){
        //do nothing
        }
        else if(i==1) {
            GetRecipeAll();
        }
        else{
            type = adapterView.getItemAtPosition(i).toString();
            GetRecipe(type);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //display the recipe detail
    private void GetDataID(){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from recipe_details where id like "+id,null);
        if (c.moveToFirst()){
            txtrecipealert.setVisibility(View.GONE);
            do {
                Recipe recipe = new Recipe();
                recipe.setId(c.getString(0));
                recipe.setImage_url(c.getString(1));
                recipe.setName(c.getString(2));
                recipe.setDescription(c.getString(3));
                recipe.setIngredients(c.getString(4));
                recipe.setInstruction(c.getString(5));
                recipe.setType(c.getString(6));
                recipes.add(recipe);
                rra.notifyDataSetChanged();
            }while (c.moveToNext());
        }
        else {
            txtrecipealert.setVisibility(View.VISIBLE);
        }
    }


    //show based on spinner selected
    private void GetRecipe(String foodtype){
        recipes.clear();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM recipe_details where type like '"+foodtype+"'",null);
        if (c.moveToFirst()){
            txtrecipealert.setVisibility(View.GONE);
            do {
                Recipe recipe = new Recipe();
                recipe.setId(c.getString(0));
                recipe.setImage_url(c.getString(1));
                recipe.setName(c.getString(2));
                recipe.setDescription(c.getString(3));
                recipe.setIngredients(c.getString(4));
                recipe.setInstruction(c.getString(5));
                recipe.setType(c.getString(6));
                recipes.add(recipe);
                rra.notifyDataSetChanged();
            }while (c.moveToNext());
        }
        else {
            txtrecipealert.setVisibility(View.VISIBLE);
        }
    }

    //show all recipe
    private void GetRecipeAll(){
        recipes.clear();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM recipe_details",null);
        if (c.moveToFirst()){
            txtrecipealert.setVisibility(View.GONE);
            do {
                Recipe recipe = new Recipe();
                recipe.setId(c.getString(0));
                recipe.setImage_url(c.getString(1));
                recipe.setName(c.getString(2));
                recipe.setDescription(c.getString(3));
                recipe.setIngredients(c.getString(4));
                recipe.setInstruction(c.getString(5));
                recipe.setType(c.getString(6));
                recipes.add(recipe);
                rra.notifyDataSetChanged();
            }while (c.moveToNext());
        }
        else {
            txtrecipealert.setVisibility(View.VISIBLE);
        }
    }

    private void SetUpRecycler(){
        rra = new RecyclerRecipeAdapter(recipes,this);
        recyclertype.setAdapter(rra);
        recyclertype.setLayoutManager(new LinearLayoutManager(this));
    }



}
