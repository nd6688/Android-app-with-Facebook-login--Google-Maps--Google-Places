package me.geno.challenge;

import android.app.Application;
import android.content.Context;

/**
 * Created by Neel on 10/8/2015.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance=this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

}
