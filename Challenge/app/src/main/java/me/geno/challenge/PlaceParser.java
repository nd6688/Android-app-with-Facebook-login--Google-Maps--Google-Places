package me.geno.challenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Neel on 10/8/2015.
 */
public class PlaceParser {


    public String[] parseResponse(JSONObject result) throws JSONException {
        String[] totalDetails = new String[4];
        if(result.has("formatted_address"))
            totalDetails[3] = "Address: " + result.getString("formatted_address");
        else
            totalDetails[3] = "Address: N/A";
        if(result.has("formatted_phone_number"))
            totalDetails[2] = "Phone Number: " + result.getString("formatted_phone_number");
        else
            totalDetails[2] = "Phone Number: N/A";
        if(result.has("name"))
            totalDetails[0] = "Name: " + result.getString("name");
        else
            totalDetails[0] = "Name: N/A";
        if(result.has("open_now"))
            totalDetails[1] = "Open Now? " + result.getBoolean("open_now");
        else
            totalDetails[1] = "Open Now? N/a";

        return totalDetails;
    }

}
