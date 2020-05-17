package si.uni_lj.fri.pbd.miniapp3.adapter;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import si.uni_lj.fri.pbd.miniapp3.R;
import si.uni_lj.fri.pbd.miniapp3.models.dto.RecipeSummaryDTO;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecipeViewHolder> {
    private String instantiatedBy;
    private Context context;
    private List<RecipeSummaryDTO> recipes;

    public RecyclerViewAdapter(String instantiatedBy, Context context, List<RecipeSummaryDTO> recipes) {
        this.instantiatedBy = instantiatedBy;
        this.context = context;
        this.recipes = recipes;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        public ImageView recipeImageView;
        public TextView recipeTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            this.recipeImageView = itemView.findViewById(R.id.image_view);
            this.recipeTextView = itemView.findViewById(R.id.text_view_content);
            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO show recipe details in DetailsActivity
                }
            }); */
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);

        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        RecipeSummaryDTO r = recipes.get(position);
        Glide.with(context).load(r.getStrMealThumb()).into(holder.recipeImageView);
        holder.recipeTextView.setText(r.getStrMeal());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}
