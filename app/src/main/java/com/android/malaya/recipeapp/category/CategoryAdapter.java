package com.android.malaya.recipeapp.category;



import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.malaya.recipeapp.recipe.AddRecipeActivity;
import com.android.malaya.recipeapp.recipe.RecipesActivity;
import com.bumptech.glide.Glide;
import com.android.malaya.recipeapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private ArrayList<Category> categoryArrayList = new ArrayList<>();
    private Context context;
   Drawable[] mCategorImage;
    int countImage =0;
    public static final String TAG="CategoryAdapter";
    private final String[] mPlaces;

    public CategoryAdapter(ArrayList<Category>categoryArrayList,Context context) {
        this.categoryArrayList = categoryArrayList;
        this.context = context;

        Resources resources = context.getResources();
        mPlaces = resources.getStringArray(R.array.categorydisplay);
        TypedArray a = resources.obtainTypedArray(R.array.category_picture);
        mCategorImage = new Drawable[a.length()];
        for (int i = 0; i < mCategorImage.length; i++) {
            mCategorImage[i] = a.getDrawable(i);


        }
        a.recycle();

    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_layout,parent,false);
        CategoryHolder rh = new CategoryHolder(v);
        return rh;
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, final int position) {

        final Category r = categoryArrayList.get(position);

           countImage++;
            //display 5 categories
        if(countImage<=5){
            holder.civfood.setImageDrawable(mCategorImage[position % mCategorImage.length]);

            holder.txtfoodname.setText(mPlaces[position% mPlaces.length]);

        }else{

            Uri   image = Uri.parse(r.getImage_url());

            holder.txtfoodname.setText(r.getType());

            Glide.with(context).load(image).into(holder.civfood);

        }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, RecipesActivity.class);
                    i.putExtra("Type",r.getType());
//                    i.putExtra("TypeResources",mPlaces[position % mPlaces.length]);
                    i.putExtra("ID",r.getId());
                    Log.i(TAG,"idAdapter"+r.getId());
                    context.startActivity(i);
                }
            });

    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder{

        private TextView txtfoodname;
        private CircleImageView civfood;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            txtfoodname = itemView.findViewById(R.id.foodname);
            civfood = itemView.findViewById(R.id.foodimage);
        }
    }

}
