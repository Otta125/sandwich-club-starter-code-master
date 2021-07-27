package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> IngredientsList = new ArrayList<>();
        Sandwich sandwich;
        JSONObject SandObject = new JSONObject(json);
        JSONObject NameObject = SandObject.getJSONObject("name");

        String MainName = NameObject.getString("mainName");

        JSONArray AlsoKnownAs = NameObject.getJSONArray("alsoKnownAs");
        if (AlsoKnownAs != null) {
            for (int i = 0; i < AlsoKnownAs.length(); i++) {
                alsoKnownAsList.add(AlsoKnownAs.getString(i));
            }
        }

        String PlaceOfOrigin = SandObject.getString("placeOfOrigin");

        String Description = SandObject.getString("description");

        String Image = SandObject.getString("image");

        JSONArray Ingredients = SandObject.getJSONArray("ingredients");
        if (Ingredients != null) {
            for (int i = 0; i < Ingredients.length(); i++) {
                IngredientsList.add(Ingredients.getString(i));
            }

        }

        sandwich = new Sandwich(MainName, alsoKnownAsList, PlaceOfOrigin, Description, Image, IngredientsList);

        return sandwich;
    }
}
