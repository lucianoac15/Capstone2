package br.com.lucianoac.receita.ui;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.lucianoac.receita.R;
import br.com.lucianoac.receita.services.response.RecipeItem;

public class RecipeViewHolder extends ViewHolder {

    TextView tvwNome;
    TextView tvwNota;
    ImageView imgReceita;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        tvwNome = (TextView) itemView.findViewById(R.id.tvwNome);
        tvwNota = (TextView) itemView.findViewById(R.id.tvwNota);
        imgReceita = (ImageView) itemView.findViewById(R.id.imgReceita);
    }


}
