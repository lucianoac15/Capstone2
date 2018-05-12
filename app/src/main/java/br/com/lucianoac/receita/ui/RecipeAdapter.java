package br.com.lucianoac.receita.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.lucianoac.receita.R;
import br.com.lucianoac.receita.services.response.RecipeItem;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{

    private List<RecipeItem> recipeList;

    public RecipeAdapter(List<RecipeItem> recipeList) {
        this.recipeList = recipeList;

    }
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        RecipeItem c = recipeList.get(position);
        Picasso.get().load(c.getSmallImageUrls()[0]).into(holder.imgReceita);
        holder.tvwNome.setText(c.getSourceDisplayName());
        holder.tvwNota.setText(c.getRating());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receita_list_item,parent, false);
        return new RecipeViewHolder(v);
    }
}
