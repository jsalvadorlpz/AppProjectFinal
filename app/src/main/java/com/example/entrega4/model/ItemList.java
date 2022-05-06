package com.example.entrega4.model;

import java.io.Serializable;

public class ItemList implements Serializable {
    private String titulo, release,genrer,poster_path,sinopsis,language,original_title;
    private double popularity;
    private  String id;
    //private int imgResource;

    public ItemList(String titulo,String release,String poster_path,String genrer,double popularity,String id){
        this.titulo = titulo;
        this.release = release;
        this.poster_path = poster_path;
        //this.imgResource = imgResource;
        this.genrer = genrer;
       // this.sinopsis = sinopsis;
       // this.language=language;
        this.popularity = popularity;
       // this.original_title = original_title;
        this.id = id;
    }
    public String getPosterPath(){return poster_path;}
    public String getTitulo(){return titulo;}
    public  String getRelease(){return release;}
    public  String getGenrer(){return genrer;}
    public  String getSinopsis(){return sinopsis;}
    public  String getLanguage(){return language;}
    public double getPopularity(){return popularity;}
    //public String getOriginal_title(){return original_title;}
     public String getId(){return id;}
    //public int getImgResource(){return imgResource;}
}