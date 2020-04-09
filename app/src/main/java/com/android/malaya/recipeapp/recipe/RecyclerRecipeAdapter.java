package com.android.malaya.recipeapp.recipe;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.android.malaya.recipeapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerRecipeAdapter extends RecyclerView.Adapter<RecyclerRecipeAdapter.RecipeHolder>{

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Context context;
    private final Drawable[] mRecipeImage;
    int countImage=0;
    private final String[] mPlaceDesc;
    private final String[] mPlaces;

    public RecyclerRecipeAdapter(ArrayList<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;

        Resources resources = context.getResources();
        mPlaces = resources.getStringArray(R.array.places);
        mPlaceDesc = resources.getStringArray(R.array.place_desc);
        TypedArray a = resources.obtainTypedArray(R.array.recipe_picture);
        mRecipeImage= new Drawable[a.length()];
        for (int i = 0; i < mRecipeImage.length; i++) {
            mRecipeImage[i] = a.getDrawable(i);
        }
        a.recycle();

    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recipe_layout,parent,false);
        RecipeHolder rh = new RecipeHolder(v);
        return rh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
        final Recipe r = recipes.get(position);


        countImage++;

        Uri   image = Uri.parse(r.getImage_url());

        //shown demo when on first launch
        //display 5 item
        if(image==null){

            // holder.civfood.setImageDrawable(mCategorImage[position % mCategorImage.length]);

            Glide.with(context).load(mRecipeImage).into(holder.civfood);
            holder.txtfooddesc.setText(mPlaceDesc[position % mPlaceDesc.length]);
            holder.txtfoodname.setText(mPlaces[position % mPlaces.length]);

        }else{
            //get image uri
            holder.txtfoodname.setText(r.getName());
            holder.txtfooddesc.setText(r.getDescription());
            //get image uri


            //load image using glide
            Glide.with(context).load(image).into(holder.civfood);

        }

        //set click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ViewRecipeActivity.class);
                i.putExtra("Name",r.getName());
                i.putExtra("ID",r.getId());
                i.putExtra("Type",r.getType());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder{

        private TextView txtfoodname,txtfooddesc;
        private CircleImageView civfood;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            txtfoodname = itemView.findViewById(R.id.foodname);
            txtfooddesc = itemView.findViewById(R.id.fooddesc);
            civfood = itemView.findViewById(R.id.foodimage);
        }
    }

}
