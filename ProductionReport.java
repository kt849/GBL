package com.example.quickstart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class ProductionReport extends AppCompatActivity {

    ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<BarEntry> barEntries1 = new ArrayList<>();
    ArrayList<String> gblprpd = new ArrayList<>();
    ArrayList<String> machines = new ArrayList<>();
   static  ArrayList<String> dta = new ArrayList<>();
    List<TextView> roes = new ArrayList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_report);
        barEntries.clear();
        machines.clear();
       // dta.clear();
        gblprpd.clear();
        TextView m0 = (TextView) findViewById(R.id.m0);
        TextView m1 = (TextView) findViewById(R.id.m1);
        TextView m2 = (TextView) findViewById(R.id.m2);
        TextView m3 = (TextView) findViewById(R.id.m3);
        TextView m4 = (TextView) findViewById(R.id.m4);
        TextView m5 = (TextView) findViewById(R.id.m5);
        TextView m6 = (TextView) findViewById(R.id.m6);
        TextView m7 = (TextView) findViewById(R.id.m7);
        TextView m8 = (TextView) findViewById(R.id.m8);
        TextView m9 = (TextView) findViewById(R.id.m9);
        TextView m10 = (TextView) findViewById(R.id.m10);
        TextView m11 = (TextView) findViewById(R.id.m11);
        TextView m12 = (TextView) findViewById(R.id.m12);
        TextView m13 = (TextView) findViewById(R.id.m13);
        TextView m14 = (TextView) findViewById(R.id.m14);
        TextView m15 = (TextView) findViewById(R.id.m15);
        roes.add(m0);
        roes.add(m1);
        roes.add(m2);
        roes.add(m3);
        roes.add(m4);
        roes.add(m5);
        roes.add(m6);
        roes.add(m7);
        roes.add(m8);
        roes.add(m9);
        roes.add(m10);
        roes.add(m11);
        roes.add(m12);
        roes.add(m13);
        roes.add(m14);
        roes.add(m15);
        roes.add((TextView)findViewById(R.id.m16));
        roes.add((TextView)findViewById(R.id.m17));
        roes.add((TextView)findViewById(R.id.m18));
        roes.add((TextView)findViewById(R.id.m19));
        roes.add((TextView)findViewById(R.id.m20));
        roes.add((TextView)findViewById(R.id.m21));
        roes.add((TextView)findViewById(R.id.m22));
        roes.add((TextView)findViewById(R.id.m23));
        roes.add((TextView)findViewById(R.id.m24));
        roes.add((TextView)findViewById(R.id.m25));
        roes.add((TextView)findViewById(R.id.m26));
        roes.add((TextView)findViewById(R.id.m27));
        roes.add((TextView)findViewById(R.id.m28));
        roes.add((TextView)findViewById(R.id.m29));
        roes.add((TextView)findViewById(R.id.m30));
        roes.add((TextView)findViewById(R.id.m31));
        roes.add((TextView)findViewById(R.id.m32));
        roes.add((TextView)findViewById(R.id.m33));
        roes.add((TextView)findViewById(R.id.m34));
        roes.add((TextView)findViewById(R.id.m35));
        roes.add((TextView)findViewById(R.id.m36));
        roes.add((TextView)findViewById(R.id.m37));
        roes.add((TextView)findViewById(R.id.m38));


        for (int i = 0; i < 39; ++i)
            roes.get(i).setText("");

        TextView output = (TextView) findViewById(R.id.output);
        output.setText("Data Retrieved:");
        for (int k = 0; k < dta.size(); ++k) {
            roes.get(k + 1).setText(dta.get(k));
        }
        for (int k = dta.size() + 1; k < 39; ++k)
            roes.get(k).setVisibility(View.GONE);
        ScrollView sv = (ScrollView) findViewById(R.id.sv);
        sv.setVisibility(View.VISIBLE);
        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        hsv.setVisibility(View.VISIBLE);

    }

    public void Logout(View view) {
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
        ns.rune = 0;
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
    public void backtomenu(View view) {
        dta.clear();
        Intent i = new Intent(this,ProductionGraphs.class);
        i.putExtra("s_dmy","1");// Change According to needs, this is a dummy value.
        startActivity(i);
    }

}
