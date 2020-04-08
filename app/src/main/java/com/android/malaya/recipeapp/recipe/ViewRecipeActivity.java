package com.android.malaya.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.android.malaya.recipeapp.Adapter.DBHelper;

public class ViewRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private String id,name,type;
    private TextView txtdescription,txtingredients,txtinstruction;
    private Button btnupdate,btndelete;
    private ImageView foodimage;
    private CollapsingToolbarLayout ctl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getIntent().getExtras();
        if (data != null){
            id = data.getString("ID");
            name = data.getString("Name");
            type = data.getString("Type");
        }
        getSupportActionBar().setTitle(type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_view_recipe);
        ctl = findViewById(R.id.ctl);
        ctl.setTitle(name);
        foodimage = findViewById(R.id.foodimage);
        txtdescription = findViewById(R.id.txtdescription);
        txtingredients = findViewById(R.id.txtlistingredient);
        txtinstruction = findViewById(R.id.txtlistinstruction);
        btnupdate = findViewById(R.id.btnupdate);
        btndelete = findViewById(R.id.btndelete);
        btnupdate.setOnClickListener(this);
        btndelete.setOnClickListener(this);
        GetData();
    }

    //display the recipe detail
    private void GetData(){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from recipe_details where id like "+id,null);
        if(c.moveToFirst()){
            Uri pic = Uri.parse(c.getString(1));
            foodimage.setImageURI(pic);
            txtdescription.setText(c.getString(3));
            txtingredients.setText(c.getString(4));
            txtinstruction.setText(c.getString(5));
        }
        else {
            Toast.makeText(ViewRecipeActivity.this,"Unable to get data",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(ViewRecipeActivity.this,RecipesActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private int DeleteRecord(){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        int affectedrow = db.delete("recipe_details","id = ?",new String[] {id});
        return affectedrow;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnupdate:
                Intent i = new Intent(ViewRecipeActivity.this,RecipeUpdateActivity.class);
                i.putExtra("ID",id);
                i.putExtra("Name",name);
                startActivity(i);
                break;
            case R.id.btndelete:
                new AlertDialog.Builder(this).setTitle("Delete Recipe").setMessage("Are you sure you want to delete this recipe ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int checkstatus = DeleteRecord();
                                if (checkstatus > 0) {
                                    Toast.makeText(ViewRecipeActivity.this,"Recipe deleted succesfully",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(ViewRecipeActivity.this, RecipesActivity.class));
                                }
                                else {
                                    Toast.makeText(ViewRecipeActivity.this,"Unable to delete recipe",Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
