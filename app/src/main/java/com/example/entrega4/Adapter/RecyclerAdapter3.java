package com.example.entrega4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrega4.MovieDetailsResults;
import com.example.entrega4.R;
import com.example.entrega4.TheMovieDatasetApi;
import com.example.entrega4.TrendingResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerAdapter3 extends RecyclerView.Adapter<RecyclerAdapter3.ViewHolder> implements View.OnClickListener{
    private List<TrendingResults.Result> trendings;
    LayoutInflater inflater;
    public  String GenreName, date;
    Fragment fragment;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    public static String BASE_URL = "https://api.themoviedb.org";

    //para las imagenes, como el poster_path solo nos da un trozo del link que necesiamtos, tenemos que tener la primera
    //parte que es generica a todos

    //listener
    private View.OnClickListener  listener;
    public void setOnClickListener (View.OnClickListener listener){
        this.listener = listener;
    }



    public RecyclerAdapter3(Context context, List<TrendingResults.Result> trendings){
        this.inflater = LayoutInflater.from(context);

        this.trendings = trendings;
    }

    @NonNull
    @Override
    public RecyclerAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Llama a este método siempre que necesita crear una ViewHolder nueva.
        // El método crea el ViewHolder y su View asociada, y los inicializa,
        // pero no completa el contenido de la vista
        // (aún no se vinculó el ViewHolder con datos específicos).
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view, parent, false);
        View view = inflater.inflate(R.layout.item_list_view,parent,false);
        view.setOnClickListener(this);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_view,parent,false);
        //inflate para modificar la jerarquia de views, para poder utiliar diseños en otras views
        return new RecyclerAdapter3.ViewHolder(view);
    }
    //el ViewHolder se utiliza para cada contenedor dentro de la vista de Recycler, para
    //vincular los datos
    // tenemos unos datos y queremos vincularlos con esas vistas, asi que se crea el adapter
    //public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter3.ViewHolder holder, int position) {
        //Llama a este método para asociar una ViewHolder con los datos.
        // El método recupera los datos correspondientes y los usa
        // para completar el diseño del contenedor de vistas.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);
        Integer id = trendings.get(position).getId();
        Call<MovieDetailsResults> call3 = myInterface.listOfMovieDetails(id, API_KEY);
        call3.enqueue(new Callback<MovieDetailsResults>() {
            @Override
            public void onResponse(Call<MovieDetailsResults> call, Response<MovieDetailsResults> response) {
                int iteradorListaGeneros = 0;

                MovieDetailsResults results = response.body();
                List<MovieDetailsResults.Genre> listOfGenre = results.getGenres();
                int SizeGenreList = listOfGenre.size();
                if(SizeGenreList==1){
                    GenreName = listOfGenre.get(iteradorListaGeneros).getName();
                } else {
                    while (iteradorListaGeneros < SizeGenreList) {
                        if(iteradorListaGeneros+1==SizeGenreList){
                            GenreName += listOfGenre.get(iteradorListaGeneros).getName();
                        }else{
                            GenreName += listOfGenre.get(iteradorListaGeneros).getName() + ", ";
                            if(SizeGenreList>7&&SizeGenreList==7){
                                iteradorListaGeneros=SizeGenreList;
                            }
                        }

                        iteradorListaGeneros++;
                    }
                }
            }
            @Override
            public void onFailure(Call<MovieDetailsResults> call, Throwable t) {

            }
        });



        Double pop = trendings.get(position).getPopularity();

        //Glide.with(getContext()).load(poster_paths.get(j)).into();
        View view = inflater.inflate(R.layout.item_list_view,null,false);
        Glide.with(view).load(url_imagenes+trendings.get(position).getPosterPath()).into(holder.image);
        holder.Titulo.setText(trendings.get(position).getTitle());
        holder.Release.setText(trendings.get(position).getFirstAirDate());
        holder.Genrer.setText(GenreName);
        holder.popu.setProgress((int) (pop/50));
        holder.prog.setText(String.valueOf(Math.round(((pop/50.0))*100.0)/100.0)+"%");

    }

    public void updateData(List<TrendingResults.Result> newitems) {
        trendings.clear();
        trendings.addAll(newitems);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Titulo,Genrer,Release,prog;
        private ImageView image;
        private ProgressBar popu;
        // private ImageView poster;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            Titulo = itemView.findViewById(R.id.Titulo);
            Release = itemView.findViewById(R.id.Release);
            Genrer = itemView.findViewById(R.id.Genrer);
            popu = itemView.findViewById(R.id.progress_bar);
            image = itemView.findViewById(R.id.imgItem);
            prog = itemView.findViewById(R.id.text_view_progress);
            // poster = itemView.findViewById(R.id.imgItem);
        }

    }

    @Override
    public int getItemCount() {
        return trendings.size();
    }

}
