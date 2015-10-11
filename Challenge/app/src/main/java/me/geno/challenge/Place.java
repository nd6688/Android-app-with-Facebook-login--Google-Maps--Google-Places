package me.geno.challenge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Neel on 10/8/2015.
 */
public class Place extends Activity {
    private String url = "https://maps.googleapis.com/maps/api/place/details/json?";
    private String placeid = "";
    private String key = "&key=AIzaSyA3jz-ixv45AsgqEjyqCIgo78sZjvIRdRs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place);

        Intent receivedIntent = getIntent();
        placeid = "placeid=" + receivedIntent.getStringExtra("PLACE_ID");;

        url = url + placeid + key;
        final RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response){
                        PlaceParser parse = new PlaceParser();
                        String[] parsedData;
                        Log.i("Inside ","onResponse");
                        try {
                            Log.i("Inside ","onResponse's try");
                            parsedData = parse.parseResponse(response.getJSONObject("result"));
                            display(parsedData);
                        } catch (JSONException e) {
                            Log.i("Inside ","onResponse's Catch");
                            TextView textView = (TextView) findViewById(R.id.textView);
                            textView.setText("URL: " + url + "\n\nError in catch: " + e.getMessage());
                            Log.i("URL", url);
                            return;
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("Inside ","onErrorResponse");
                        TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText("URL: " + url + "\n\nError: " + error.getStackTrace());
                        Log.i("URL", url);
                    }
                });

        requestQueue.add(jsObjRequest);

    }

    void display(String[] parsedData){
        TextView textView = (TextView) findViewById(R.id.textView);
        String viewString = "";
        for(int i=0; i< parsedData.length; i++){
            viewString += parsedData[i] + "\n";
        }
        textView.setTextColor(Color.BLACK);
        textView.setText(viewString);
    }
}
