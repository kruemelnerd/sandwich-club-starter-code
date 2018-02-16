package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        //The easy solution:
//        Gson gson = new Gson();
//        Sandwich sandwich = gson.fromJson(json, Sandwich.class);


        Sandwich sandwich = new Sandwich();
        JSONObject sandwichJson = new JSONObject(json);
        sandwich.setDescription(sandwichJson.getString("description"));
        sandwich.setPlaceOfOrigin(sandwichJson.getString("placeOfOrigin"));
        sandwich.setImage(sandwichJson.getString("image"));


        JSONObject name = sandwichJson.getJSONObject("name");
        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
        List<String> sandwichAlsoKnownAs = new ArrayList<>();
        if (alsoKnownAs != null) {
            for (int i=0;i<alsoKnownAs.length();i++){
                sandwichAlsoKnownAs.add(alsoKnownAs.getString(i));
            }
        }
        sandwich.setAlsoKnownAs(sandwichAlsoKnownAs);
        sandwich.setMainName(name.getString("mainName"));


        JSONArray ingredients =  sandwichJson.getJSONArray("ingredients");
        List<String> sandwichIngredients = new ArrayList<>();
        if (ingredients != null) {
            for (int i=0;i<ingredients.length();i++){
                sandwichIngredients.add(ingredients.getString(i));
            }
        }
        sandwich.setIngredients(sandwichIngredients);

        return sandwich;
    }
}
