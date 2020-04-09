package com.android.malaya.recipeapp.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.malaya.recipeapp.DBHelper.DBHelper;
import com.android.malaya.recipeapp.R;
import com.android.malaya.recipeapp.recipe.AddRecipeActivity;
import com.android.malaya.recipeapp.recipe.RecipesActivity;
import com.android.malaya.recipeapp.recipe.ViewRecipeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {




    private FloatingActionButton fabAdd;
    private RecyclerView recyclertype;

    private TextView txtrecipealert;
    private ArrayList<Category> categoriesList = new ArrayList<>();
    private CategoryAdapter adapter;
    private String id,image,type;
    public static final String TAG="CategoryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        fabAdd = findViewById(R.id.fabadd);
        recyclertype = findViewById(R.id.recylertype);
        txtrecipealert = findViewById(R.id.txtrecipes);

        Bundle data = getIntent().getExtras();
        if (data != null){
            id = data.getString("ID");
            image = data.getString("Image");
            type = data.getString("Type");
        }


        //display recyclerview
        setUpRecycler();

        //display all category
        getCategoryAll();

        //add category
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, AddCategoryActivity.class));
            }
        });


        // Notify the adapter of the change.
        adapter.notifyDataSetChanged();

    }

    //show all catgory
    private void getCategoryAll(){
        categoriesList.clear();
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM category_details",null);
        if (c.moveToFirst()){
            txtrecipealert.setVisibility(View.GONE);
            do {
                Category category= new Category();
                category.setId(c.getString(0));
                category.setImage_url(c.getString(1));
                category.setType(c.getString(2));
                categoriesList.add(category);
                adapter.notifyDataSetChanged();
            }while (c.moveToNext());
        }
        else {
            txtrecipealert.setVisibility(View.VISIBLE);
        }
    }


    private void setUpRecycler(){
        adapter = new CategoryAdapter(categoriesList,this);
        recyclertype.setAdapter(  adapter);
        recyclertype.setLayoutManager(new LinearLayoutManager(this));

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
        recyclertype.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclertype, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);




            }
        }));
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CategoryActivity.this,Integer.toString(which),Toast.LENGTH_LONG);
                if (which == 0) {
                    //update
                    final Category r = categoriesList.get(position);
                    Intent i = new Intent(CategoryActivity.this, CategoryUpdateActivity.class);
                    i.putExtra("ID",r.getId());
                    i.putExtra("Type",r.getType());
                    Log.i(TAG,"idAdapterCategoryActivity: "+r.getType());
                    startActivity(i);

                } else {
                    deleteNote(position);

                }
            }
        });
        builder.show();
    }




    /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
    private void deleteNote(int position) {
        // deleting the category from db
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("category_details", "id" + " = ?",
                new String[]{String.valueOf(categoriesList.get(position).getId())});
        db.close();

        // removing the category from the list
        categoriesList.remove(position);
        adapter.notifyItemRemoved(position);


    }







}
