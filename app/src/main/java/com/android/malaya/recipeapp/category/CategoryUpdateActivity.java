package com.android.malaya.recipeapp.category;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.malaya.recipeapp.DBHelper.DBHelper;
import com.android.malaya.recipeapp.R;
import com.android.malaya.recipeapp.recipe.RecipeUpdateActivity;
import com.bumptech.glide.Glide;


public class CategoryUpdateActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String id,name,desc,ingredients,instruction,type,imageCheck;
    private EditText edname,eddesc,edingredients,edinstruction;
    private ImageView categoryImage;
    private Spinner spintype;
    private Button btnupdate;
    private DBHelper helper = new DBHelper(this);
    private Uri path;
    private ArrayAdapter<CharSequence> adapter;
    public static final String TAG="CategoryUpdateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_category_update);
        Bundle data = getIntent().getExtras();
        if (data != null){
            id = data.getString("ID");
            type = data.getString("Type");
            Log.i(TAG,"idUpdateCategory"+id);

        }

       categoryImage = findViewById(R.id.categoryImage);
        btnupdate = findViewById(R.id.btnupdate);
        spintype = findViewById(R.id.spintype);

         adapter = ArrayAdapter.createFromResource(this,R.array.category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(adapter);
        spintype.setOnItemSelectedListener(this);

        getCategoryID(id);

        categoryImage.setOnClickListener(this);
        btnupdate.setOnClickListener(this);
    }

    private void SelectImage(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(i,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            path = data.getData();
            categoryImage.setImageURI(path);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean UpdateData(){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image_url",path.toString());
        values.put("type",type);
        db.update("category_details",values,"id = ?",new String[] {id});
        return true;
    }

    //get id category
    private void getCategoryID(String position){

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM category_details where id like "+position,null);
        if (c.moveToFirst()){

            do {

                //value is there but error when inflate
                Log.i(TAG,"SPINNER"+c.getString(2));

                path = Uri.parse(c.getString(1));
                categoryImage.setImageURI(path);
               spintype.setSelection(  adapter.getPosition(c.getString(2)));

            }while (c.moveToNext());
        }
        else {

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
            case R.id.btnupdate:

                if ( (type.equals("--Choose One--") ||imageCheck=="")){
                    Toast.makeText(CategoryUpdateActivity.this,"Please fill in all the information",Toast.LENGTH_LONG).show();
                }
                else {
                    UpdateData();
                    if (UpdateData() == true){
                        Toast.makeText(CategoryUpdateActivity.this,"Succesfully Updated",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(CategoryUpdateActivity.this,CategoryActivity.class);
                        i.putExtra("ID",id);
                        i.putExtra("Type",type);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(CategoryUpdateActivity.this,"Unable to update",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.categoryImage:
                Toast.makeText(CategoryUpdateActivity.this,"Select Image",Toast.LENGTH_LONG).show();

                SelectImage();
                imageCheck = "Yes";
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
