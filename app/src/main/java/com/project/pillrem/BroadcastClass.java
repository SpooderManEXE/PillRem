package com.project.pillrem;

/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class BroadcastClass extends BroadcastReceiver {
    static String Pill;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context, "notifyme")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(Pill)
                .setContentText("Take your pill!")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());

    }
    static void check(Context context){

        DatabaseHandler db = new DatabaseHandler(context);
        List<Medicine> allmed = db.getAllMedicine();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        int currentHour = cal.get(Calendar.HOUR_OF_DAY)*3600000;
        int currentMinutes = cal.get(Calendar.MINUTE)*60000;
        int day = cal.get(Calendar.DAY_OF_WEEK);


        long currentTime = currentMinutes+currentHour+60000;
        boolean alarm = false;

        if (allmed.size() > 0) {
            for(int i=0; i<allmed.size();i++){
               if ((day==Calendar.SUNDAY&&allmed.get(i).getday().charAt(0)=='S')||(day==Calendar.MONDAY&&allmed.get(i).getday().charAt(1)=='M')||(day==Calendar.TUESDAY&&allmed.get(i).getday().charAt(2)=='T')||(day==Calendar.WEDNESDAY&&allmed.get(i).getday().charAt(3)=='W')||(day==Calendar.THURSDAY&&allmed.get(i).getday().charAt(4)=='T')||(day==Calendar.FRIDAY&&allmed.get(i).getday().charAt(5)=='F')||(day==Calendar.SATURDAY&&allmed.get(i).getday().charAt(6)=='S'))
               {
                    if(currentTime== Integer.parseInt(allmed.get(i).gettime())){
                        Pill=allmed.get(i).getName();
                        Intent intent=new Intent(context,BroadcastClass.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,0);
                        AlarmManager alarmManager= (AlarmManager)context.getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP,currentTime,
                                pendingIntent);

                    }
                }
            }

        }
    }

}