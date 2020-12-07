package com.project.pillrem;
/**
 * Created by Sulthan Nizarudin on 06-12-2020.
 */
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

public class SecondFragment extends Fragment {
    CheckBox checkBox;
    ToggleButton d1,d2,d3,d4,d5,d6,d7;
    EditText mname;
    String day,time;
    TimePicker mdate;
    private static Context context = null;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState


    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(getActivity());
        checkBox = (CheckBox) getView().findViewById(R.id.checkBox);
        mname=getView().findViewById(R.id.mname);
        day="";
        time="";
        d1= getView().findViewById(R.id.toggleButton);
        d2= getView().findViewById(R.id.toggleButton2);
        d3= getView().findViewById(R.id.toggleButton3);
        d4= getView().findViewById(R.id.toggleButton4);
        d5= getView().findViewById(R.id.toggleButton5);
        d6= getView().findViewById(R.id.toggleButton6);
        d7= getView().findViewById(R.id.toggleButton7);
        mdate = getView().findViewById(R.id.mdate);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour, minute,am_pm;
                if (Build.VERSION.SDK_INT >= 23) {
                    hour = mdate.getHour();
                    minute = mdate.getMinute();
                } else {
                    hour = mdate.getCurrentHour();
                    minute = mdate.getCurrentMinute();
                }
                if (hour > 12) {
                    am_pm = 43200000 ;
                    hour = hour - 12;
                } else {
                    am_pm = 0;
                }
                time = Integer.toString(hour*3600000 + minute*60000 + am_pm);
                if (d1.isChecked()) day += "S"; else day+=" ";
                if (d2.isChecked()) day += "M"; else day+=" ";
                if (d3.isChecked()) day += "T"; else day+=" ";
                if (d4.isChecked()) day += "W"; else day+=" ";
                if (d5.isChecked()) day += "T"; else day+=" ";
                if (d6.isChecked()) day += "F"; else day+=" ";
                if (d7.isChecked()) day += "S"; else day+=" ";
                db.addMedicine(new Medicine(mname.getText().toString(), day, time));
                context=getActivity();
                Toast.makeText(context, "Reminder Set", Toast.LENGTH_SHORT).show();


                NavHostFragment.findNavController(SecondFragment.this)

                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChanged) {
                boolean checked = checkBox.isChecked();
                        if (checked) {
                            d1.setChecked(true);
                            d2.setChecked(true);
                            d3.setChecked(true);
                            d4.setChecked(true);
                            d5.setChecked(true);
                            d6.setChecked(true);
                            d7.setChecked(true);                        }
                        else {
                            d1.setChecked(false);
                            d2.setChecked(false);
                            d3.setChecked(false);
                            d4.setChecked(false);
                            d5.setChecked(false);
                            d6.setChecked(false);
                            d7.setChecked(false);
                        }
                }
        });
    }
}