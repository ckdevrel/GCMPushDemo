package com.takeoffandroid.GCMDemo.GCMPushNotification;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
 
 
public class GCMRegistrationIntentService extends IntentService {
 
 
    private static final String TAG = "IntentService";

 
    public GCMRegistrationIntentService () {
        super (TAG);
    } 
 
 
    @Override 
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(GCMUtils.PROJECT_ID,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i (TAG, "Token " + token);
            sharedPreferences.edit().putBoolean(GCMUtils.SENT_TOKEN_TO_SERVER, true).apply();
        } catch (Exception e) {
            Log.d(TAG, "Failedtorefresh", e);
            sharedPreferences.edit().putBoolean(GCMUtils.SENT_TOKEN_TO_SERVER, false).apply();
        }

        // Notify UI that registration has completed, so the progress indicator can be hidden. 
        Intent registrationComplete = new Intent(GCMUtils.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    } 
 
 
}