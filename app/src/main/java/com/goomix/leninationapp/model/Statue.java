package com.goomix.leninationapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Statue {

    private long id;
    private String title;
    private String description;
    private  String image;
    private String coordinates;



    public Statue(JSONObject object) {
        try {
            id = object.getLong("id");
            title = object.getString("title");
            description = object.getString("description");
            image = object.getString("image");
            coordinates = object.getString("coordinates");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public long getId() {

        return id;
    }
    public void setId(long id){

        this.id= id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle (String title){
        this.title= title;
    }
    public  String getDescription(){
        return  description;
    }
    public void setDescription (String description){
        this.description= description;
    }
    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getCoordinates(){
        return coordinates;
    }
    public void setCoordinates (String coordinates){
        this.coordinates = coordinates;
    }

}
