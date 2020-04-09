package com.android.malaya.recipeapp.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.android.malaya.recipeapp.R;

import java.io.ByteArrayOutputStream;

public class DBHelper extends SQLiteOpenHelper {

    public static final String COLUMN_IMAGE = "image_url";
    public static final String TABLE_NAME = "category_details";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    Context context;

    public DBHelper(Context context) {
        super(context, "recipe.db", null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        // Create table SQL query
        String CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_IMAGE + " TEXT,"
                        +  COLUMN_TYPE+ " TEXT"
                        + ")";

        //we need to display image & title only
        sqLiteDatabase.execSQL(CREATE_TABLE);



        sqLiteDatabase.execSQL("create table recipe_details(id INTEGER PRIMARY KEY AUTOINCREMENT,image_url TEXT,name TEXT, description TEXT," +
                "ingredients TEXT,instruction TEXT, type TEXT)");

        //insert demo value in category 5 item
        ContentValues categoryDemoBread = new ContentValues();
        categoryDemoBread .put(COLUMN_IMAGE, R.drawable.bread_category);
        categoryDemoBread .put(COLUMN_TYPE,"Bread");
        sqLiteDatabase.insert("category_details",null,categoryDemoBread );

        ContentValues categoryDemoBrownie = new ContentValues();
        categoryDemoBrownie .put(COLUMN_IMAGE, R.drawable.brownie_category);
        categoryDemoBrownie .put(COLUMN_TYPE,"Brownie");
        sqLiteDatabase.insert("category_details",null,categoryDemoBrownie );

        ContentValues categoryDemoCake = new ContentValues();
        categoryDemoCake .put(COLUMN_IMAGE, R.drawable.cake_category);
        categoryDemoCake.put(COLUMN_TYPE,"Cake");
        sqLiteDatabase.insert("category_details",null,categoryDemoCake);


        ContentValues categoryDemoPizza = new ContentValues();
        categoryDemoPizza  .put(COLUMN_IMAGE, R.drawable.pizza_category);
        categoryDemoPizza  .put(COLUMN_TYPE,"Pizza");
        sqLiteDatabase.insert("category_details",null,categoryDemoPizza );


        ContentValues categoryDemoSoup = new ContentValues();
        categoryDemoSoup .put(COLUMN_IMAGE, R.drawable.soup_category);
        categoryDemoSoup .put(COLUMN_TYPE,"Soup");
        sqLiteDatabase.insert("category_details",null,categoryDemoSoup );



//bread
        ContentValues valuesRecipeBread = new ContentValues();
        valuesRecipeBread .put("image_url",R.drawable.recipebread);
        valuesRecipeBread .put("name","Sun-dried tomato and olive bread");
        valuesRecipeBread .put("description","A savoury, salty Mediterranean sun-dried tomato loaf that makes a great sandwich with peppery rocket and ham.");
        valuesRecipeBread .put("ingredients","500g/1lb 2oz strong white flour\n" +
                "15g/½oz salt\n" +
                "55ml/2fl oz olive oil\n" +
                "20g/¾oz fresh yeast\n" +
                "275ml/9fl oz water\n" +
                "170g/6oz black Greek olives, pitted and chopped\n" +
                "55g/2oz sun-dried tomatoes");
        valuesRecipeBread .put("instruction","Mix all ingredients, apart from the olives and tomatoes, in a large bowl. Take care not to put the yeast in direct contact with the salt when they are first added to the bowl.\n" +
                "\n" +
                "Knead well with your hands and knuckles until the dough is elastic, smooth and shiny. Cover with a piece of cling film and leave to rise for one hour.\n" +
                "\n" +
                "Bake at 220C/425F/Gas 7 for 30 minutes until golden-brown. Remove from the oven and cool on a wire rack.");
        valuesRecipeBread .put("type","Bread");
        sqLiteDatabase.insert("recipe_details",null,valuesRecipeBread);

//brownies
        //insert demo value in recipe 5 item . each category contain 1 item
        ContentValues valuesRecipeBrownies = new ContentValues();
        valuesRecipeBrownies.put("image_url",R.drawable.recipebrownies);
        valuesRecipeBrownies.put("name","Fudgy chocolate brownies");
        valuesRecipeBrownies.put("description","Each brownie provide 209 kcal, 9g protein, 14g carbohydrate, 12g fat, 4g fibre. For this recipe you will need a 20cm/8in square cake tin.");
        valuesRecipeBrownies.put("ingredients","400g/14oz tin black beans, rinsed and drained (235g/8½oz drained weight)\n" +
                "80g/3oz light vegetable spread\n" +
                "4 large free-range eggs\n" +
                "60g/2¼oz good-quality cocoa powder, plus 1 tsp for dusting\n" +
                "50g/1¾oz ground almonds\n" +
                "60g/2¼oz dark chocolate chips");
        valuesRecipeBrownies.put("instruction","Preheat the oven to 180C/160C Fan/Gas 4. Line a 20cm/8in square baking tin with baking paper.\n" +
                "\n" +
                "Put the black beans and vegetable spread into a food processor and blend until smooth. Add the eggs and blend again briefly, until well combined. Transfer the mixture to a large bowl.\n" +
                "\n" +
                "Remove from the oven and leave to cool slightly before carefully lifting the brownie out of the tin and cutting it into squares. Enjoy while still warm, sprinkled with a little sifted cocoa.");
        valuesRecipeBrownies.put("type","Brownie");
        sqLiteDatabase.insert("recipe_details",null,valuesRecipeBrownies);


        //cake
        ContentValues valuesRecipeCake= new ContentValues();
        valuesRecipeCake.put("image_url",R.drawable.recipecake);
        valuesRecipeCake.put("name","Apple sandwich cake");
        valuesRecipeCake.put("description","Mary Berry's ultimate apple cake – a Victoria sponge that stays really moist from grated apple. The lemon-flavoured cream keeps it fresh-tasting.");
        valuesRecipeCake.put("ingredients","225g/8oz baking spread, straight from the fridge, plus extra for greasing\n" +
                "225g/8oz caster sugar\n" +
                "225g/8oz self-raising flour\n" +
                "1 tsp baking powder\n" +
                "4 large free-range eggs, beaten\n" +
                "2 eating apples, peeled, cored and grated (see Recipe Tips)\n" +
                "icing sugar, for dusting");
        valuesRecipeCake.put("instruction","Preheat the oven to 180C/160C Fan/Gas 4. Grease two 20cm/8in round, loose-bottomed sandwich tins and line the bases with baking paper.\n" +
                "\n" +
                "Measure all the sponge ingredients except the apple and icing sugar into a large bowl and beat with an electric hand whisk until combined. Fold the grated apple into the mixture, then divide between the tins and level the tops.\n" +
                "\n" +
                "Bake in the oven for about 25–30 minutes until golden, well risen and coming away from the sides of the tins. Allow to cool in the tins.");
        valuesRecipeCake.put("type","Cake");
        sqLiteDatabase.insert("recipe_details",null,valuesRecipeCake);


        //pizza
        ContentValues valuesRecipePizza = new ContentValues();
        valuesRecipePizza.put("image_url",R.drawable.recipepizza);
        valuesRecipePizza.put("name","Quick pepperoni pizza");
        valuesRecipePizza.put("description","A quick and easy pizza with no proving required. Tailor the toppings to suit your taste.");
        valuesRecipePizza.put("ingredients","300g/10½oz strong white bread flour, plus extra for dusting\n" +
                "2 tsp baking powder\n" +
                "½ tsp sea salt\n" +
                "2 tsp rapeseed oil\n" +
                "150–175ml/5–6fl oz lukewarm water\n" +
                "freshly ground black pepper");
        valuesRecipePizza.put("instruction"," Preheat the oven to 200C/180C Fan/Gas 6 and place a large baking tray upside-down on a shelf.\n" +
                "\n" +
                "To make the dough, sift the flour and baking powder into a large bowl. Add the salt and a pinch of pepper, then stir in the oil. Make a well in the centre, then stir in the warm water and mix to a soft dough, drawing the flour in from the side of the bowl a little at a time – you might need to add another 25ml/1fl oz water if the dough is a little dry; you want it to be slightly sticky and soft.\n" +
                "\n" +
                "Tip the dough onto a lightly floured work surface and knead for 2 minutes, until smooth and springy.");
        valuesRecipePizza.put("type","Pizza");
        sqLiteDatabase.insert("recipe_details",null,valuesRecipePizza);


        //soup
        ContentValues valuesRecipeSoup = new ContentValues();
        valuesRecipeSoup.put("image_url",R.drawable.recipesoup);
        valuesRecipeSoup.put("name","Chicken soup and dumplings");
        valuesRecipeSoup.put("description"," Chicken soup is known as 'Jewish penicillin.' It's the ultimate comfort food served with matzo dumplings.");
        valuesRecipeSoup.put("ingredients","1 x 1.5kg/3lb 5oz chicken\n" +
                "1 onion, peeled, sliced\n" +
                "2 celery stalks, chopped\n" +
                "2 garlic cloves, peeled, chopped\n" +
                "6-8 fresh parsley stalks\n" +
                "1 bay leaf\n" +
                "12 white peppercorns" +
                "60g/2¼oz dark chocolate chips");
        valuesRecipeSoup.put("instruction"," For the chicken soup, place all the soup ingredients into a large saucepan, cover with cold water and bring to the boil. Reduce the heat and simmer for 1 hour.\n" +
                "\n" +
                "Remove the chicken and set aside to cool slightly. Remove the meat from the chicken and slice into chunks.\n" +
                "\n" +
                "To serve, reheat the chicken broth, if necessary, and place some cooked chicken into the bottom of each of 6 serving bowls, pour the chicken broth on top and add a few dumplings.");
        valuesRecipeSoup.put("type","Soup");
        sqLiteDatabase.insert("recipe_details",null,valuesRecipeSoup);









    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists category_details");
        sqLiteDatabase.execSQL("drop table if exists recipe_details");

        // Create tables again
        onCreate(sqLiteDatabase);
    }

}
