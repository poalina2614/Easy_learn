package com.example.english_cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettActivity extends AppCompatActivity {
    SharedPreferences settings;
    EditText num;
    Spinner time, theme;
    Switch aSwitch;
    TextView status;
    SharedPreferences.Editor PrefEditor;


    AlarmManager alarmManager;

    PendingIntent alarmIntent;

    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    private static String CHANNEL_ID = "Easy Learn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett);
        settings = getSharedPreferences("PrefName", MODE_PRIVATE);
        num = findViewById(R.id.num_max);
        time = findViewById(R.id.time_send);
        theme = findViewById(R.id.them_sp);
        aSwitch = findViewById(R.id.msg_sw);
        status = findViewById(R.id.status);
        TextView text = findViewById(R.id.textView8);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{"светлая", "темная"});
        ArrayAdapter adapterTime = new ArrayAdapter(this, android.R.layout.simple_spinner_item,
                new String[]{"1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00",
                        "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
                        "20:00", "21:00", "22:00", "23:00", "00:00"});

        time.setAdapter(adapterTime);
        theme.setAdapter(adapter);
        int pos_time = settings.getInt("time_num", 8);
        time.setSelection(pos_time);
        int pos_theme = settings.getInt("theme_num", 0);
        theme.setSelection(pos_theme);
        boolean msg = settings.getBoolean("msg", false);
        aSwitch.setChecked(msg);

        String countW = settings.getString("countW", "10");
        num.setText(countW);
        if(!aSwitch.isChecked()){
            time.setEnabled(false);
            text.setEnabled(false);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    time.setEnabled(false);
                    text.setEnabled(false);
                } else {
                    time.setEnabled(true);
                    text.setEnabled(true);
                }
            }
        });
    }


    public void saveSett(View view) {
        PrefEditor = settings.edit();
        PrefEditor.putInt("time_num", time.getSelectedItemPosition());
        PrefEditor.putInt("theme_num", theme.getSelectedItemPosition());
        PrefEditor.putString("countW", num.getText().toString());
        PrefEditor.putBoolean("msg", aSwitch.isChecked());
        PrefEditor.apply();
        status.setText(settings.getAll().toString());
        if (settings.getBoolean("msg", false)) Toast.makeText(this, "сохранилось(нет)", Toast.LENGTH_SHORT).show();
    }


    public void to_sett2(View view) {
        Intent i = new Intent(SettActivity.this, MainActivity.class);
        i.putExtra("num", 3);
        startActivity(i);
    }

    private void addAlarmNotification(long startTime, String title, String text){
        AlarmManager alarmManager;

        PendingIntent alarmIntent;

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), (int)startTime, intent, 0);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime, alarmIntent);
    }
}