package br.com.lucianoac.receita;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.List;

import br.com.lucianoac.receita.services.IReceitaService;
import br.com.lucianoac.receita.services.response.RecipeItem;
import br.com.lucianoac.receita.services.response.RecipeList;
import br.com.lucianoac.receita.ui.RecipeAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipes_grid)
    RecyclerView ltvReceitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.yummly.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IReceitaService service = retrofit.create(IReceitaService.class);

        Call<RecipeList> call =  service.listarReceitas(null);
        call.enqueue(new Callback<RecipeList>() {
            @Override
            public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                RecipeList list = response.body();
                RecipeAdapter ca = new RecipeAdapter(list.getListrecipe());
                ltvReceitas.setAdapter(ca);
                ltvReceitas.setItemAnimator(new DefaultItemAnimator());
                int columns = getResources().getInteger(R.integer.recipes_columns);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), columns);
                ltvReceitas.setLayoutManager(gridLayoutManager);
            }

            @Override
            public void onFailure(Call<RecipeList> call, Throwable t) {

            }
            })  ;
    }
}
