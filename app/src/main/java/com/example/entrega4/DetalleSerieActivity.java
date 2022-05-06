package com.example.entrega4;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleSerieActivity extends AppCompatActivity {
    TextView titulo,genre,date,sinopsis,language;
    ImageView poster;
    Activity activity;
    public String url_imagenes = "https://image.tmdb.org/t/p/w500";
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_serie);

        titulo = findViewById(R.id.TitleSerie);
        genre = findViewById(R.id.GenreSerie);
        date = findViewById(R.id.dateSerie);
        sinopsis = findViewById(R.id.sinopsisSerie);
        language = findViewById(R.id.director);
        poster = findViewById(R.id.imageViewSerie);
    }
}