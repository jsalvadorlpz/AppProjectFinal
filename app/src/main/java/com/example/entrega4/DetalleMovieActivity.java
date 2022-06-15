package com.example.entrega4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.entrega4.Adapter.RecyclerAdapterActores;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleMovieActivity extends AppCompatActivity {
    TextView titulo,genre,date,sinopsis,language,company,idioma;
    ImageView poster,poster2,imagen;
    private LinearLayout layout;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    public static String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "65b0f0c1dca6b0957d34d1fceaf3107a";
    RecyclerView recyclerView;
    List<CreditResults.Cast> listaActores,lista10Actores;
    RecyclerAdapterActores recyclerAdapterActores;
    Integer idpelicula,id2;
    String back, post,Titulo,titulo2,Path,path2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallemovie);
        //RECYCLER Y ADAPTER DE ACTORES
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewActoresMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerAdapterActores = new RecyclerAdapterActores(this,new ArrayList<>(),id2,titulo2,path2);
        recyclerView.setAdapter(recyclerAdapterActores);
        listaActores = new ArrayList<CreditResults.Cast>();
        lista10Actores = new ArrayList<CreditResults.Cast>();


        titulo = findViewById(R.id.Title);
        genre = findViewById(R.id.Genre);
        date = findViewById(R.id.date);
        sinopsis = findViewById(R.id.sinopsis);
        language = findViewById(R.id.director);
        poster = findViewById(R.id.imageView);
        poster2 = findViewById(R.id.imageViewBack);
        company = findViewById(R.id.prod_compa);
        idioma = findViewById(R.id.idioma);
        layout = findViewById(R.id.layoutDetalleMovie);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.id.imageView);
        //crearPalette(bitmap);
        paletteGenerator();



        Integer id =Integer.parseInt(getIntent().getStringExtra("idMovie"));

        String tituloMovie =getIntent().getStringExtra("tituloMovie");
        String pathMovie =getIntent().getStringExtra("imageMovie");
        Log.e("","El titulo recibido es "+tituloMovie);
        Intent detalle = new Intent(this, DetalleMovieActivity.class);
        detalle.putExtra("tituloPeli",tituloMovie);
        idpelicula = id;
        id2 = id;
        Intent actores  = new Intent(this,ActoresMovie.class);
        Bundle extras = new Bundle();
        extras.putInt("idepeli",idpelicula);


        //actores.putExtra(extras);
        getRecomendados(id);
        getCredits(id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);

          Call<MovieDetailsResults> call3 = myInterface.listOfMovieDetails(id, API_KEY);
           call3.enqueue(new Callback<MovieDetailsResults>() {
               @Override
               public void onResponse(Call<MovieDetailsResults> call, Response<MovieDetailsResults> response) {
                   int iteradorListaCompanys = 0;
                   int iteradorListaGeneros = 0;
                   String GenreName = "";
                   String CompnayName = "";
                  MovieDetailsResults results = response.body();
                   List<MovieDetailsResults.Genre> listOfGenre = results.getGenres();
                   int SizeGenreList = listOfGenre.size();
                   List<MovieDetailsResults.ProductionCompany> listOfCompanys = results.getProductionCompanies();
                   int SizeCompanysList = listOfCompanys.size();
                   if(SizeGenreList==1){
                       GenreName = listOfGenre.get(iteradorListaGeneros).getName();
                   } else {
                       while (iteradorListaGeneros < SizeGenreList) {
                           if(iteradorListaGeneros+1==SizeGenreList){
                               GenreName += listOfGenre.get(iteradorListaGeneros).getName();
                           }else{
                               GenreName += listOfGenre.get(iteradorListaGeneros).getName() + ", ";
                           }
                           //CompnayName += listOfCompanys.get(iteradorListaCompanys).getName() + ", ";
                           iteradorListaGeneros++;
                       }
                   }
                   if(SizeCompanysList==1) {
                       CompnayName = listOfCompanys.get(iteradorListaCompanys).getName();
                   }else{
                       while (iteradorListaCompanys < SizeCompanysList) {
                           if(iteradorListaCompanys+1==SizeCompanysList){
                               CompnayName += listOfCompanys.get(iteradorListaCompanys).getName();
                           }else{
                               CompnayName += listOfCompanys.get(iteradorListaCompanys).getName() + ", ";
                           }
                           //CompnayName += listOfCompanys.get(iteradorListaCompanys).getName() + ", ";
                           iteradorListaCompanys++;
                       }
                   }
                   Log.e("",GenreName);
                   Log.e("",CompnayName);
                   genre.setText(GenreName);
                   company.setText(CompnayName);
                   titulo.setText(results.getTitle()+ "("+ results.getOriginalTitle()+")");
                   sinopsis.setText(results.getOverview());
                   date.setText(results.getReleaseDate());
                   idioma.setText(results.getOriginalLanguage());
                   //image = (String) results.getPosterPath();
                   back = results.getBackdropPath();
                   setImages(back);
                   Titulo = results.getTitle();
                   Path = results.getBackdropPath();
                   Log.e("","El back drop path es" + Path);

               }
               @Override
               public void onFailure(Call<MovieDetailsResults> call, Throwable t) {

               }
          });




    }
    //obtener el palette con los colores el bitmap
    public Palette createPaletteSync(Bitmap bitmap){
        Palette palette = Palette.from(bitmap).generate();
        return palette;

    }
    public void paletteGenerator(){
        imagen = findViewById(R.id.imageView);
        BitmapDrawable drawable = (BitmapDrawable) imagen.getDrawable();
        if(drawable  != null) {
            Bitmap bitmap = drawable.getBitmap();
            Palette palette = createPaletteSync(bitmap);
            Palette.Swatch dominatswatch = palette.getDominantSwatch();
            if (dominatswatch != null) {
                int bgColor = dominatswatch.getRgb();
                layout.setBackgroundColor(bgColor);
            }
        }
    }
    public void setImages(String back){
        String image_peli = getIntent().getStringExtra("imageMovie");
        Glide.with(this).load(url_imagenes+image_peli).into(poster);
        Glide.with(this).load(url_imagenes+back).into(poster2);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("","se destruye el detalle movie");
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
    }

    public void getCredits(Integer id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface = retrofit.create(TheMovieDatasetApi.class);

        Call<CreditResults> call = myInterface.listOfCredit(id,API_KEY);
        call.enqueue(new Callback<CreditResults>() {
            @Override
            public void onResponse(Call<CreditResults> call, Response<CreditResults> response) {
                CreditResults results = response.body();
                List<CreditResults.Cast> listOfActors = results.getCast();
                listaActores = listOfActors;
                int iterador = 0;
                while(iterador < 10) {
                    lista10Actores.add(listaActores.get(iterador));
                    iterador++;
                }
                recyclerAdapterActores.updateDataActores(lista10Actores,id2,Titulo,Path);
            }
            @Override
            public void onFailure(Call<CreditResults> call, Throwable t) {

            }
        });

    }
    public void getRecomendados(Integer id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDatasetApi myInterface2 = retrofit.create(TheMovieDatasetApi.class);

        Call<RecomedadosResults> call = myInterface2.listOfRecomendados(id,API_KEY);

        call.enqueue(new Callback<RecomedadosResults>() {
            @Override
            public void onResponse(Call<RecomedadosResults> call, Response<RecomedadosResults> response) {
                Log.e("","Recomendados entra en el OnResponse");
            }
            @Override
            public void onFailure(Call<RecomedadosResults> call, Throwable t) {
                Log.e("","Recomendados entra en el onFailure");

            }
        });
    }

}