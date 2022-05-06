package com.example.entrega4.model;

import java.io.Serializable;

public class ItemList implements Serializable {
    private String titulo, release,genrer,poster_path,sinopsis,language,original_title,company;
    private double popularity;
    //private int imgResource;

    public ItemList(String titulo,String release,String genrer,String sinopsis,String language,String poster_path,double popularity,String original_title,String compnay){
        this.titulo = titulo;
        this.release = release;
        this.poster_path = poster_path;
        //this.imgResource = imgResource;
        this.genrer = genrer;
        this.sinopsis = sinopsis;
        this.language=language;
        this.popularity = popularity;
        this.original_title = original_title;
        this.company = company;
    }
    public String getPosterPath(){return poster_path;}
    public String getTitulo(){return titulo;}
    public  String getRelease(){return release;}
    public  String getGenrer(){return genrer;}
    public  String getSinopsis(){return sinopsis;}
    public  String getLanguage(){return language;}
    public double getPopularity(){return popularity;}
    public String getOriginal_title(){return original_title;}
     public String getCompany(){return company;}
    //public int getImgResource(){return imgResource;}
}