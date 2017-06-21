package com.example.justin.alarmclock;

import android.app.AlarmManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //to make alarm manager
    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    Context context;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        //initialize alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initialize the alarm
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);

        //Create a calander instance
        final Calendar calendar = Calendar.getInstance();

        //create an intent to the alarm reviciever class
        final Intent my_Intent = new Intent(this.context, Alarm_Reciever.class);



        //Onclick Listener
        Button StartAlarm = (Button) findViewById(R.id.startAlarm);


        StartAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                //setting calendar to the specified time
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                //create a string of picked time for validation purposes
                int hour = alarmTimePicker.getHour();
                int min = alarmTimePicker.getMinute();

                //convert to string
                String hourString = String.valueOf(hour);
                String minString = String.valueOf(min);


                //convert to proper time for kids aka non 24 hour and fix the minutes under 10
                // where displays 10:03 as 10:3
                if(hour > 12){
                    hourString = String.valueOf(hour - 12);
                }

                if(min < 10){
                    minString = "0" + String.valueOf(min);
                }

                //display through pop up chosen alarm time
                Toast.makeText(context, "Your alarm is set to " + hourString + ":" + minString, Toast.LENGTH_SHORT).show();

                //put extra string into myintent
                my_Intent.putExtra("extra", "alarm on");

                //create a pending intent that delays the intent until the specified calendar time
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        my_Intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
            }
        });

        //stop button Listener
        Button StopAlarm = (Button) findViewById(R.id.endAlarm);

        StopAlarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //method cancels alarm
                alarmManager.cancel(pendingIntent);

                //tell clock the off button was pressed
                my_Intent.putExtra("extra", "alarm off");

                //stop the ringtone
                sendBroadcast(my_Intent);
            }
        });



    }


}
