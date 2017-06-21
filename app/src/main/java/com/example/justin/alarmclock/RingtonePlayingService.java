package com.example.justin.alarmclock;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Justin on 2017-06-20.
 */

public class RingtonePlayingService extends Service{

    MediaPlayer mediaSong;
    int startId;
    boolean isRunning;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");




        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        //if else statements
        //no music user presses alarm on, music starts at right time
        if(!this.isRunning && startId == 1){

            //create and start ringtone
            mediaSong = MediaPlayer.create(this, R.raw.tinkerbell);
            mediaSong.start();
            //reset varibales
            this.isRunning = true;
            this.startId = 0;

            //notifications
            //set up notification service
            NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            //have main activity popup/set up an intent that goes to the main activity
            Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);

            //set up a pending intent
            PendingIntent pendingIntentMain = PendingIntent.getActivity(this, 0, intentMainActivity, 0);

            //make notification parameters
            Notification notifyPopup = new Notification.Builder(this)
                    .setContentTitle("An Alarm is going off!")
                    .setContentText("Click Me")
                    .setContentIntent(pendingIntentMain)
                    .setAutoCancel(true)
                    .build();


            //set up the notificcation call command
            notifyManager.notify(0,notifyPopup);
        }
        //if there is music playing, user pressed alarm off, music should stop
        else if(this.isRunning && startId == 0){

            //stop the ringtone
            mediaSong.stop();
            mediaSong.reset();

            //reset varibales
            this.isRunning = false;
            this.startId = 0;

        }
        //these are if the user presses random buttons, just bug proofing
        //if theres no music and user presses alarm off, do nothing
        else if(!this.isRunning && startId == 0){

            //reset varibales
            this.isRunning = false;
            this.startId = 0;
        }
        //if there is music and user presses alarm on, do nothing
        else if(this.isRunning && startId == 1) {

            //reset varibales
            this.isRunning = false;
            this.startId = 0;
        }
        //just end the string aka odd events
        else{

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Toast.makeText(this,"On Destroy Called", Toast.LENGTH_SHORT).show();
    }


}
