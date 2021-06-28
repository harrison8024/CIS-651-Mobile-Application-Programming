package com.example.porject_2;

public class Movie {
    private final int image_id;
    private final String title;
    private final String year;
    private final String stars;
    private final float rating;
    private final String description;
    private final String length;
    private final String director;
    private final String url;

    public Movie(String title, int id, String description, String year, String length,
                 float rating, String director, String stars, String url){
        this.image_id = id;
        this.title = title;
        this.year = year;
        this.stars = stars;
        this.rating = rating;
        this.description = description;
        this.length = length;
        this.director = director;
        this.url = url;
    }

    public int getImage_id(){ return image_id; }
    public String getTitle(){ return title; }
    public String getYear(){ return year; }
    public String getStars(){ return stars; }
    public float getRating(){ return rating; }
    public String getDescription(){ return description; }
    public String getLength() { return length; }
    public String getDirector() { return director; }
    public String getUrl() { return url; }
}
