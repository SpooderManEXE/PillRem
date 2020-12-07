package com.project.pillrem;

/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
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
    static int currentMinutes;
    @Override
    public void onReceive(Context context, Intent intent) {
        notificationChannelId(context);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context, "notifyme")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(Pill)
                .setContentText("Take your pill!")
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(currentMinutes,builder.build());

    }
    static void check(Context context){

        DatabaseHandler db = new DatabaseHandler(context);
        List<Medicine> allmed = db.getAllMedicine();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        int currentHour = cal.get(Calendar.HOUR_OF_DAY)*3600000;
        currentMinutes = cal.get(Calendar.MINUTE)*60000;
        int day = cal.get(Calendar.DAY_OF_WEEK);


        long currentTime = currentMinutes+currentHour+60000;
        boolean alarm = false;

        if (allmed.size() > 0) {
            for(int i=0; i<allmed.size();i++){
               if ((day==Calendar.SUNDAY&&allmed.get(i).getday().charAt(0)=='S')||(day==Calendar.MONDAY&&allmed.get(i).getday().charAt(1)=='M')||(day==Calendar.TUESDAY&&allmed.get(i).getday().charAt(2)=='T')||(day==Calendar.WEDNESDAY&&allmed.get(i).getday().charAt(3)=='W')||(day==Calendar.THURSDAY&&allmed.get(i).getday().charAt(4)=='T')||(day==Calendar.FRIDAY&&allmed.get(i).getday().charAt(5)=='F')||(day==Calendar.SATURDAY&&allmed.get(i).getday().charAt(6)=='S'))
               {
                    if(currentTime== Integer.parseInt(allmed.get(i).gettime())){
                        Log.d("k", "ALARM!!! ");

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

    private void notificationChannelId(Context context){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();
            CharSequence name="Noti ZZZZ";
            String desc = "Get to work !";
            int impo = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel= new NotificationChannel("notifyme",name,impo);
            channel.setDescription(desc);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            channel.setSound(sound,att);
            NotificationManager notificationManager= context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}