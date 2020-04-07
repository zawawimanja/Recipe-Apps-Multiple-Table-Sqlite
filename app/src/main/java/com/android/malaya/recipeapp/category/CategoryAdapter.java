package com.android.malaya.recipeapp.category;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.malaya.recipeapp.Adapter.Recipe;
import com.android.malaya.recipeapp.RecipesActivity;
import com.bumptech.glide.Glide;
import com.android.malaya.recipeapp.R;
import com.android.malaya.recipeapp.ViewRecipeActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private ArrayList<Category> recipes = new ArrayList<>();
    private Context context;

    public CategoryAdapter(ArrayList<Category> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }


    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.category_layout,parent,false);
        CategoryHolder rh = new CategoryHolder(v);
        return rh;
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        final Category r = recipes.get(position);
        holder.txtfoodname.setText(r.getType());

        //get image uri
        Uri image = Uri.parse(r.getImage_url());
        //load image using glide
        Glide.with(context).load(image).into(holder.civfood);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, RecipesActivity.class);
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
