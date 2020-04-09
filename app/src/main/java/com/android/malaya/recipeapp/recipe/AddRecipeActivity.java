package com.android.malaya.recipeapp.recipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.malaya.recipeapp.R;
import com.android.malaya.recipeapp.category.AddCategoryActivity;
import com.bumptech.glide.Glide;
import com.android.malaya.recipeapp.DBHelper.DBHelper;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText edname,eddesc,edingredients,edinstruction;
    private ImageView foodimage;
    private Button btnadd;
    private String name,desc,ingredients,instruction,imagecheck,type,image;
    private Spinner spintype;
    Uri path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add New Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_add_recipe);
        edname = findViewById(R.id.ed_name);
        eddesc = findViewById(R.id.ed_fooddesc);
        edingredients = findViewById(R.id.ed_foodingredients);
        edinstruction = findViewById(R.id.ed_foodinstruc);
        foodimage = findViewById(R.id.foodimage);
        spintype = findViewById(R.id.spintype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(adapter);
        spintype.setOnItemSelectedListener(this);
        foodimage.setOnClickListener(this);
        btnadd = findViewById(R.id.btnadd);
        btnadd.setOnClickListener(this);
    }

    private void selectImage(){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_OPEN_DOCUMENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            path = data.getData();
            Glide.with(this).load(path).into(foodimage);
            image = path.toString();


        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    private void addIntoDB(){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image_url",image);
        values.put("name",name);
        values.put("description",desc);
        values.put("ingredients",ingredients);
        values.put("instruction",instruction);
        values.put("type",type);
        long row = db.insert("recipe_details",null,values);
        if (row == -1){
            Toast.makeText(AddRecipeActivity.this,"Unable to add recipe",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(AddRecipeActivity.this,"New Recipe added succesfully",Toast.LENGTH_LONG).show();
            Intent i = new Intent(AddRecipeActivity.this, RecipesActivity.class);
            //go to recipe activity and  pass display type of food
            i.putExtra("Type",type);
            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.foodimage:
                selectImage();
                imagecheck = "Yes";
                break;
            case R.id.btnadd:
                name = edname.getText().toString().trim();
                desc = eddesc.getText().toString().trim();
                ingredients = edingredients.getText().toString().trim();
                instruction = edinstruction.getText().toString().trim();
                if (name.equals("")||desc.equals("")||ingredients.equals("")||instruction.equals("")||type.equals("--Choose One--") ||imagecheck==""||path==null){
                    Toast.makeText(AddRecipeActivity.this,"Please fill in all the information",Toast.LENGTH_LONG).show();


                }
                else {
                    Glide.with(this).load(path).into(foodimage);
                    image = path.toString();
                    addIntoDB();
                }


                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
