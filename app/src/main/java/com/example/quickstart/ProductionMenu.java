package com.example.quickstart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;

public class ProductionMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_menu);
    }
//If any monthly yearlty or daily method is called
    public void menu2(View view)
    {

        Intent i = new Intent(this,ProductionGraphs.class);//Inittially set to daily.
        switch (view.getId()) {
            case R.id.pmonthly://If monthly is pressed go to pmonthly_report Class
                i = new Intent(this,pmonthly_report.class);
                break;
            case R.id.pyearly://Similarly for pyearly Class.
                i = new Intent(this,pyearly_report.class);
                break;
        }
        startActivity(i);

    }

    public void backtomenu(View view) {//back button takes to menu activity class, this method called when back button is pressed.
       Intent i = new Intent(this,MenuActivity.class);
       startActivity(i);
    }

    public void Logout(View view) {//If you press logout button.
           String filename = "info";
                            String fileContents = "logged out";
                            java.io.FileOutputStream outputStream;

                            try {
                                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                                outputStream.write(fileContents.getBytes());
                                outputStream.close();
                                }
                            catch (Exception e) {
                                e.printStackTrace();
                                }
        cancelAlarm();
        // Delete file containing user name pending.........
        startActivity(new Intent(this,LoginActivity.class));


    }


    public void cancelAlarm() {
        NotificationService ns = new NotificationService();
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

}
