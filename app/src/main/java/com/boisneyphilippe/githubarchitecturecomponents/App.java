package com.boisneyphilippe.githubarchitecturecomponents;

import android.app.Application;
import android.content.Context;

import com.boisneyphilippe.githubarchitecturecomponents.others.DataCenter;


/**
 * Created by Philippe on 02/03/2018.
 */

public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        DataCenter.init();
    }
}
