package com.android.malaya.recipeapp.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.malaya.recipeapp.Adapter.DBHelper;
import com.android.malaya.recipeapp.Adapter.Recipe;
import com.android.malaya.recipeapp.Adapter.RecyclerRecipeAdapter;
import com.android.malaya.recipeapp.AddRecipeActivity;
import com.android.malaya.recipeapp.R;
import com.android.malaya.recipeapp.RecipesActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {



    private Spinner spintype;
    private FloatingActionButton fabadd;
    private RecyclerView recyclertype;

    private TextView txtrecipealert;
    private ArrayList<Category> recipes = new ArrayList<>();
    private CategoryAdapter rra;
    private String id,name,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        fabadd = findViewById(R.id.fabadd);
        recyclertype = findViewById(R.id.recylertype);
        txtrecipealert = findViewById(R.id.txtrecipes);

        Bundle data = getIntent().getExtras();
        if (data != null){
            id = data.getString("ID");
            name = data.getString("Name");
            type = data.getString("Type");
        }



        SetUpRecycler();
        GetRecipeAll();

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, AddCategoryActivity.class));
            }
        });


    }

    //show all recipe
    private void GetRecipeAll(){
        recipes.clear();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM category_details",null);
        if (c.moveToFirst()){
            txtrecipealert.setVisibility(View.GONE);
            do {
                Category recipe = new Category();
                recipe.setId(c.getString(0));
                recipe.setImage_url(c.getString(1));
                recipe.setType(c.getString(2));
                recipes.add(recipe);
                rra.notifyDataSetChanged();
            }while (c.moveToNext());
        }
        else {
            txtrecipealert.setVisibility(View.VISIBLE);
        }
    }

    private void SetUpRecycler(){
        rra = new CategoryAdapter(recipes,this);
        recyclertype.setAdapter(rra);
        recyclertype.setLayoutManager(new LinearLayoutManager(this));
    }



}
