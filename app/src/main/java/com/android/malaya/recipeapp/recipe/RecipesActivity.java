package com.android.malaya.recipeapp.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.malaya.recipeapp.R;
import com.android.malaya.recipeapp.category.CategoryActivity;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.android.malaya.recipeapp.DBHelper.DBHelper;

import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spintype;
    private FloatingActionButton fabadd;
    private RecyclerView recyclertype;
    private TextView txtrecipealert;
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private RecyclerRecipeAdapter adapterRecycler;
    public static final String TAG="RecipesActivity";
    private String type;



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

            type = data.getString("Type");

            Log.i(TAG,"type"+type);


            getRecipeType(type);

        }

        //inflate data into spinner R.array.types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.types,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(adapter);

        // set the Layout Manager.
        recyclertype.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the adapter and set it to the RecyclerView.
        adapterRecycler =new RecyclerRecipeAdapter(recipes,this);
        recyclertype.setAdapter(adapterRecycler);

        spintype.setOnItemSelectedListener(this);
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipesActivity.this,AddRecipeActivity.class));
            }
        });

        // Notify the adapter of the change.
        adapterRecycler.notifyDataSetChanged();

    }



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
            adapterRecycler.notifyDataSetChanged();
        }
        else{
            type = adapterView.getItemAtPosition(i).toString();
            Log.i(TAG,"typeSpinner"+type);
            getRecipeType(type);
            adapterRecycler.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //show based on spinner selected
    private void getRecipeType(String foodtype){
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

            }while (c.moveToNext());

        }
        else {
            txtrecipealert.setVisibility(View.VISIBLE);
        }
    }



}
