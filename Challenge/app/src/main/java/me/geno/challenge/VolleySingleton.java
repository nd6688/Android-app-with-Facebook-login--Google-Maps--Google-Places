package me.geno.challenge;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Neel on 10/8/2015.
 */
public class VolleySingleton {
    private static VolleySingleton instance=null;
    private RequestQueue requestQueue;
    private VolleySingleton(){
        requestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
    }
    public static VolleySingleton getInstance(){
        if(instance==null)
        {
            instance=new VolleySingleton();
        }
        return instance;
    }
    public RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
