package me.geno.challenge;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static me.geno.challenge.JSONKeys.*;

/**
 * Created by Neel on 10/5/2015.
 */
public class AroundMe extends Fragment {
    public View view;
    public int totalPlaces;
    HomeActivity location = new HomeActivity();
    LatLng myLocation;
    String url = "https://maps.googleapis.com/maps/api/place/search/json?";
    int radius = 100;
    String key = "AIzaSyA3jz-ixv45AsgqEjyqCIgo78sZjvIRdRs";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.around_me,container,false);

        if(location.latitude!=0 && location.longitude!=0)
            myLocation = new LatLng(location.latitude, location.longitude);
        else
            myLocation = new LatLng(40.7431735,-73.9799391);

        url = url + "location="+myLocation.latitude+","+myLocation.longitude+"&radius="+radius+"&key="+key;

        final RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response){
                        AroundMeParser parse = new AroundMeParser();
                        JSONArray cleanPlaces = parse.cleanPlaces(parse.parseResponse(response));
                        try {
                            cleanPlaces = sortTop25(cleanPlaces);
                            display(cleanPlaces);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "ERROR while Sorting or Display", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(getActivity(), "ERROR: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("","ERROR: + error.getMessage()");
                    }
                });

        requestQueue.add(jsObjRequest);

        return view;
    }

    public JSONArray sortTop25(JSONArray cleanPlaces) throws JSONException {
        JSONArray top25 = new JSONArray();
        JSONObject temp = new JSONObject();
        int n = cleanPlaces.length();
        JSONObject[] cleanPlacesArray = new JSONObject[n];
        for(int i=0; i<n; i++){
            cleanPlacesArray[i] = cleanPlaces.getJSONObject(i);
        }


        int i,j;
        n = cleanPlaces.length();
        for(i = 0; i < n; i++){
            for(j = 1; j < (n-i); j++){
                try {
                    if (!cleanPlacesArray[j-1].getString(PLACE_RATING).isEmpty())
                        if(Double.parseDouble(cleanPlacesArray[j - 1].getString(PLACE_RATING)) > Double.parseDouble(cleanPlacesArray[j].getString(PLACE_RATING))){
                            temp = cleanPlacesArray[j-1];
                            cleanPlacesArray[j - 1] = cleanPlacesArray[j];
                            cleanPlacesArray[j]=temp;
                        }
                } catch (JSONException e) {
                    return null;
                }
            }
        }
        n = cleanPlaces.length();
        j=1;
        for(i=n-1; i>=0; i--){
            top25.put(cleanPlacesArray[i]);
            j++;
            if(j>25)
                break;
        }
        totalPlaces = j-1;
        return top25;
    }

    public void display(JSONArray sorted25Places) throws JSONException {
        final int N = totalPlaces;

        final TextView[] places = new TextView[N];

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);

        for (int i = 0; i < N; i++) {
            TextView place = new TextView(getActivity());


            place.setTextColor(Color.BLACK);
            final JSONObject placeObject = sorted25Places.getJSONObject(i);


            place.setText("Place Name: " + placeObject.getString(PLACE_NAME) +
                    "\nPlace Rating: " + placeObject.getString(PLACE_RATING) +
                    "\nPlace Types: " + placeObject.getJSONArray(PLACE_TYPES).toString() + "\n\n");

            place.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), Place.class);
                    try {
                        intent.putExtra("PLACE_ID", placeObject.getString(PLACE_ID));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getActivity().startActivity(intent);
                }
            });


            linearLayout.addView(place);


            places[i] = place;
        }
    }

}