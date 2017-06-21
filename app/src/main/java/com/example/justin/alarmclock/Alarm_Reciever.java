package com.example.justin.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Justin on 2017-06-20.
 */

public class Alarm_Reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We are in the Reciever", "YAY");

        //fetch extra string rfom intent
        String getYourString = intent.getExtras().getString("extra");

        //create an intent to the ringtone service
        Intent serviceIntent = new Intent(context, RingtonePlayingService.class);

        serviceIntent.putExtra("extra", getYourString);

        //start the ringtone service
        context.startService(serviceIntent);

    }
}
