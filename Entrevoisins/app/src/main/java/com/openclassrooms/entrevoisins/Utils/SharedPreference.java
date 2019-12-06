package com.openclassrooms.entrevoisins.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {

    public static final String PREFS_NAME = "NEIGHBOUR_APP";
    public static final String FAVORITES = "Neighbour_Favorite";

    public SharedPreference(){
        super();
    }

    public void saveFavorites(Context context, List<Neighbour> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.apply();
    }

    public void addFavorite(Context context, Neighbour neighbour) {
        List<Neighbour> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Neighbour>();
        favorites.add(neighbour);
        saveFavorites(context, favorites);

        Log.i("DEBUG", "add to fav");
    }

    public void removeFavorite(Context context, Neighbour neighbour) {
        ArrayList<Neighbour> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(neighbour);
            saveFavorites(context, favorites);

            Log.i("DEBUG", "remove to fav");
        }
    }

    public ArrayList<Neighbour> getFavorites(Context context){
        SharedPreferences settings;
        List<Neighbour> favorites;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if(settings.contains(FAVORITES)){
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Neighbour[] favoriteItems = gson.fromJson(jsonFavorites, Neighbour[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Neighbour>(favorites);
        } else {
            return null;
        }
        return (ArrayList<Neighbour>) favorites;
    }

}
