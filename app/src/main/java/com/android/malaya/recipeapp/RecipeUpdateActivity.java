package com.android.malaya.recipeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

import com.android.malaya.recipeapp.Adapter.DBHelper;

public class RecipeUpdateActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String id,name,desc,ingredients,instruction,type,imagecheck;
    private EditText edname,eddesc,edingredients,edinstruction;
    private ImageView foodimage;
    private Spinner spintype;
    private Button btnupdate;
    private DBHelper helper = new DBHelper(this);
    private Uri path;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Recipe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_recipe_update);
        Bundle data = getIntent().getExtras();
        if (data != null){
            id = data.getString("ID");
        }
        edname = findViewById(R.id.ed_name);
        eddesc = findViewById(R.id.ed_fooddesc);
        edingredients = findViewById(R.id.ed_foodingredients);
        edinstruction = findViewById(R.id.ed_foodinstruc);
        foodimage = findViewById(R.id.foodimage);
        btnupdate = findViewById(R.id.btnupdate);
        spintype = findViewById(R.id.spintype);
        adapter = ArrayAdapter.createFromResource(this,R.array.category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(adapter);
        spintype.setOnItemSelectedListener(this);
        GetData();
        foodimage.setOnClickListener(this);
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
            foodimage.setImageURI(path);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean UpdateData(){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("image_url",path.toString());
        values.put("name",name);
        values.put("description",desc);
        values.put("ingredients",ingredients);
        values.put("instruction",instruction);
        values.put("type",type);
        db.update("recipe_details",values,"id = ?",new String[] {id});
        return true;
    }

    private void GetData(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from recipe_details where id like "+id,null);
        if (c.moveToFirst()){
            path = Uri.parse(c.getString(1));
            foodimage.setImageURI(path);
            edname.setText(c.getString(2));
            eddesc.setText(c.getString(3));
            edingredients.setText(c.getString(4));
            edinstruction.setText(c.getString(5));
            spintype.setSelection(adapter.getPosition(c.getString(6)));
        }
        else {
            Toast.makeText(RecipeUpdateActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
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
                name = edname.getText().toString().trim();
                desc = eddesc.getText().toString().trim();
                ingredients = edingredients.getText().toString().trim();
                instruction = edinstruction.getText().toString().trim();
                if (name.equals("")||desc.equals("")||ingredients.equals("")||instruction.equals("")){
                    Toast.makeText(RecipeUpdateActivity.this,"Please fill in all the information",Toast.LENGTH_LONG).show();
                }
                else {
                    UpdateData();
                    if (UpdateData() == true){
                        Toast.makeText(RecipeUpdateActivity.this,"Succesfully Updated",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RecipeUpdateActivity.this,ViewRecipeActivity.class);
                        i.putExtra("ID",id);
                        i.putExtra("Name",name);
                        i.putExtra("Type",type);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(RecipeUpdateActivity.this,"Unable to update",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.foodimage:
                SelectImage();
                imagecheck = "Yes";
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
