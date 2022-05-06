package com.example.entrega4.model;

public class ItemListSeries {
    private String titulo,poster_path_serie;

    //private int imgResource;

    public ItemListSeries(String titulo,String poster_path_serie){
        this.titulo = titulo;

        this.poster_path_serie = poster_path_serie;

    }

    public String getTitulo(){return titulo;}
    public String getPosterPathSerie(){return poster_path_serie;}

}
