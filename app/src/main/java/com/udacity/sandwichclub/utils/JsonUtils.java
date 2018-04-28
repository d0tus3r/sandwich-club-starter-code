package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

//import json
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//arraylist and list for ingredients and aka
import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    public static Sandwich parseSandwichJson(String json){

        try {
            //create json object to pull data
            JSONObject sandwichJSON = new JSONObject(json);
            //create json object to pull name from sandwich
            JSONObject sandwichNameJSON = sandwichJSON.getJSONObject("name");

            //Get Strings to construct a new sandwich
            //main strings, will only pull a single string per
            String mainName = sandwichNameJSON.getString("mainName");
            String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");
            String description = sandwichJSON.getString("description");
            String imagePath = sandwichJSON.getString("image");

            //Create an array list to hold any aka names
            List<String> alsoKnownAs = new ArrayList<>();
            //create json array and parse
            JSONArray aKAJSON = sandwichNameJSON.getJSONArray("alsoKnownAs");
            //loop through and append data to array
            for (int i = 0; i < aKAJSON.length(); i++){
                alsoKnownAs.add(aKAJSON.getString(i));
            }

            //create array list to hold ingredients
            List<String> ingredients = new ArrayList<>();
            //create json array for retreiving ingredients
            JSONArray ingredientsJSON = sandwichJSON.getJSONArray("ingredients");
            //loop through and append data
            for (int i = 0; i < ingredientsJSON.length(); i++){
                ingredients.add(ingredientsJSON.getString(i));
            }
            //return new sandwich object populated by json data
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, imagePath, ingredients);







        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

}
