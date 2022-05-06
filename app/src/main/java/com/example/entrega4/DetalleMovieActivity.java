package com.example.entrega4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetalleMovieActivity extends AppCompatActivity {
    TextView titulo,genre,date,sinopsis,language,company;
    ImageView poster;
    Activity activity;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallemovie);

        titulo = findViewById(R.id.Title);
        genre = findViewById(R.id.Genre);
        date = findViewById(R.id.date);
        sinopsis = findViewById(R.id.sinopsis);
        language = findViewById(R.id.director);
        poster = findViewById(R.id.imageView);
        company = findViewById(R.id.prod_compa);


        String titulo_peli = getIntent().getStringExtra("titulo");
        String genre_peli = getIntent().getStringExtra("genre");
        String sinopsis_peli = getIntent().getStringExtra("sinopsis");
        String date_peli = getIntent().getStringExtra("date");
        String image_peli = getIntent().getStringExtra("image");
        String company_peli = getIntent().getStringExtra("company");
        titulo.setText(titulo_peli);
        genre.setText(genre_peli);
        sinopsis.setText(sinopsis_peli);
        date.setText(date_peli);
        company.setText(company_peli);
        //View view = inflater.inflate(R.layout.activity_detallemovie,null,false);
        Glide.with(this).load(url_imagenes+image_peli).into(poster);


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e("","se destruye el detalle movie");
        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show();
    }

}