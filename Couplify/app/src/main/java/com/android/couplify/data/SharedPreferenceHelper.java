/*
 * Couplify Version 2,3
 *  SLIIT MAD Project
 *  SLIIT Kandy
 *  asiriofficial@gmail.com
 *  All Rights reserved
 * */

package com.android.couplify.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.couplify.model.User;


public class SharedPreferenceHelper {
    private static SharedPreferenceHelper instance = null;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static String SHARE_USER_INFO = "userinfo";
    private static String SHARE_KEY_NAME = "name";
    private static String SHARE_KEY_EMAIL = "email";
    private static String SHARE_KEY_AVATA = "avata";
    private static String SHARE_KEY_STEPCOUNT = "stepcount";
    private static String SHARE_KEY_LOCATION = "location";
    private static String SHARE_KEY_ACCELERATION = "acceleration";
    private static String SHARE_KEY_UID = "uid";


    private SharedPreferenceHelper() {}


    public static SharedPreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceHelper();
            preferences = context.getSharedPreferences(SHARE_USER_INFO, Context.MODE_PRIVATE);
            editor = preferences.edit();
        }
        return instance;
    }

    public void saveUserInfo(User user) {
        editor.putString(SHARE_KEY_NAME, user.name);
        editor.putString(SHARE_KEY_EMAIL, user.email);
        editor.putString(SHARE_KEY_AVATA, user.avata);
        editor.putString(SHARE_KEY_STEPCOUNT, String.valueOf(user.stepcount));
        editor.putString(SHARE_KEY_ACCELERATION, String.valueOf(user.acceleration));
        editor.putString(SHARE_KEY_UID, StaticConfig.UID);
        editor.putString(SHARE_KEY_LOCATION, user.location);
        editor.apply();
    }

    public User getUserInfo(){
        String userName = preferences.getString(SHARE_KEY_NAME, "");
        String email = preferences.getString(SHARE_KEY_EMAIL, "");
        String avatar = preferences.getString(SHARE_KEY_AVATA, "default");
        String stepcount = preferences.getString(SHARE_KEY_STEPCOUNT, "0");
        String acceleration = preferences.getString(SHARE_KEY_ACCELERATION, "0");
        String location = preferences.getString(SHARE_KEY_LOCATION, "Srilanka");


        User user = new User();
        user.name = userName;
        user.email = email;
        user.avata = avatar;
        user.stepcount = Integer.parseInt(stepcount);
        user.location = location;
        user.acceleration = Double.parseDouble(acceleration);

        return user;
    }

    public String getUID(){
        return preferences.getString(SHARE_KEY_UID, "");
    }

}
