package br.com.lucianoac.receita.screen;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.lucianoac.receita.R;
import br.com.lucianoac.receita.util.OnItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.text_movie_review_content)
    TextView content;
    @BindView(R.id.text_movie_review_author)
    TextView author;

    @Nullable
    private OnItemClickListener onItemClickListener;

    public RecipeReviewViewHolder(View itemView, @Nullable OnItemClickListener onItemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
