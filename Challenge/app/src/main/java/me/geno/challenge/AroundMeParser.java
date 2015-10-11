package me.geno.challenge;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static me.geno.challenge.JSONKeys.*;

/**
 * Created by Neel on 10/8/2015.
 */
public class AroundMeParser {

    public JSONArray parseResponse(JSONObject response){
        JSONArray places = new JSONArray();

        try {
            if(response.has(ARRAY_NAME))
                places = response.getJSONArray(ARRAY_NAME);
        } catch (JSONException e) {

        }

        return places;
    }

    public JSONArray cleanPlaces(JSONArray results){
        JSONArray cleanResults = new JSONArray();

        for(int i=0; i<results.length(); i++){
            try {
                JSONObject place = results.getJSONObject(i);
                JSONObject cleanPlace = new JSONObject();
                if(place.has(PLACE_NAME))
                    cleanPlace.put(PLACE_NAME,place.getString(PLACE_NAME));
                else
                    cleanPlace.put(PLACE_NAME,"No Name");
                if(place.has(PLACE_ID))
                    cleanPlace.put(PLACE_ID,place.getString(PLACE_ID));
                else
                    cleanPlace.put(PLACE_ID,"No Place Id");
                if(place.has(PLACE_RATING))
                    cleanPlace.put(PLACE_RATING, place.getString(PLACE_RATING));
                else
                    cleanPlace.put(PLACE_RATING,"0");
                if(place.has(PLACE_TYPES))
                    cleanPlace.put(PLACE_TYPES,place.getJSONArray(PLACE_TYPES));
                else
                    cleanPlace.put(PLACE_TYPES,"{}");
                cleanResults.put(cleanPlace);
            } catch (JSONException e) {
                return null;
            }
        }
        return cleanResults;
    }

}
