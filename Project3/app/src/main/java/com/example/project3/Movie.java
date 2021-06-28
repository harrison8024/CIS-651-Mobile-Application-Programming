package com.example.project3;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Movie {
    public String name;
    public String image;
    public String description;
    public String length;
    public String year;
    public double rating;
    public String director;
    public String stars;
    public String url;

    public Movie(){

    }
    public Movie(String name, String image, String description, String length, String year,
                 double rating, String director, String stars, String url){
        this.name = name;
        this.image = image;
        this.description = description;
        this.length = length;
        this.year = year;
        this.rating = rating;
        this.director = director;
        this.stars = stars;
        this.url = url;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("image", image);
        result.put("description", description);
        result.put("length", length);
        result.put("year", year);
        result.put("rating", rating);
        result.put("director", director);
        result.put("stars", stars);
        result.put("url", url);

        return result;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getImage() {
        return image;
    }

    public String getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public String getStars() {
        return stars;
    }

    public String getUrl() {
        return url;
    }

    public String getYear() {
        return year;
    }
}
