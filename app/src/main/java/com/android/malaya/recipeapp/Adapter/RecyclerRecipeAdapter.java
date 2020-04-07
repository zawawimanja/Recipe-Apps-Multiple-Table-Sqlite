package com.android.malaya.recipeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.android.malaya.recipeapp.R;
import com.android.malaya.recipeapp.ViewRecipeActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerRecipeAdapter extends RecyclerView.Adapter<RecyclerRecipeAdapter.RecipeHolder>{

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Context context;

    public RecyclerRecipeAdapter(ArrayList<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
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
        holder.txtfoodname.setText(r.getName());
        holder.txtfooddesc.setText(r.getDescription());
        //get image uri
        Uri image = Uri.parse(r.getImage_url());
        //load image using glide
        Glide.with(context).load(image).into(holder.civfood);

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
