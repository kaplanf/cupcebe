package com.nomad.cuppcebe;

import android.app.Application;


public class CuppCebeApplication extends Application {

    public static final String USER_ID = "user_id";
    private static CuppCebeApplication singleton;

    public static String IS_LOGIN = "islogin";
    public static String FACEBOOK_USER_ID = "facebook_user_id";
    public static String ACCESS_TOKEN_PREF_KEY = "access_token_pref_key";

    public static String CUPPCEBE_TOKEN_KEY = "cuppcebe_token_key";

    public static String USER_OBJECT ="user_object";

    public static CuppCebeApplication getInstance() {
        if (singleton == null)
            singleton = new CuppCebeApplication();
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
