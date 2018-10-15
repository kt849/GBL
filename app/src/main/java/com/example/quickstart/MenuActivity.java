package com.example.quickstart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

//When any of Sales or Production button is pressed this method is called.
    public void menu(View view) {

        switch (view.getId()) {

            case R.id.production://In case of production button run the ProductionMenu Activity.
                startActivity(new Intent(this,ProductionMenu.class));
                break;

        }
    }

    public void Logout(View view) {//If you press logout button this method is called.
                            String filename = "info";
                            String fileContents = "logged out";//change info content to "logged out".
                            java.io.FileOutputStream outputStream;

                            try {
                                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                outputStream.write(fileContents.getBytes());
                                outputStream.close();
                                }
                            catch (Exception e) {
                                e.printStackTrace();
                                }
        cancelAlarm();//To stop showing notifications, cancel alarm, after logging out.
        // Delete file containing user name pending.........
        startActivity(new Intent(this,LoginActivity.class));// And go to login page after pressing logout button


    }


    public void cancelAlarm() {//No need to understand.
        NotificationService ns = new NotificationService();
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
}
