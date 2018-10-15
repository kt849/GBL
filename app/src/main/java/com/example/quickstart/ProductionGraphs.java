package com.example.quickstart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProductionGraphs extends AppCompatActivity {

    int s_dmy;
    int ex = 0;
    int ex1 = 0;
    ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<BarEntry> barEntries1 = new ArrayList<>();
    ArrayList<BarEntry> percentage = new ArrayList<>();
    ArrayList<String> gblprpd = new ArrayList<>();
    ArrayList<String> machines = new ArrayList<>();
    ArrayList<String> dta = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_graphs);
         barEntries.clear();
         barEntries1.clear();
         percentage.clear();
         machines.clear();
         dta.clear();
         gblprpd.clear();
        ProductionReport.dta.clear();
        Button greport = (Button) findViewById(R.id.greport);
        greport.setVisibility(View.GONE);
        TextView output = (TextView) findViewById(R.id.output);
        output.setText("Please wait...");
        // Use s_dmy to get to the desired graph,i.e, month, daily and yearly.
        BarChart chart = (BarChart) findViewById(R.id.chart2);
        chart.invalidate();
        chart.setVisibility(View.GONE);
        chart = (BarChart) findViewById(R.id.chart1);
        chart.invalidate();
        chart.setVisibility(View.GONE);

        try {
            new ProductionGraphs.FTPDownloader().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void greport(View view) {

        Intent i = new Intent(this,ProductionReport.class);
        startActivity(i);
    }


    public class MyAxisValueFormatter implements IAxisValueFormatter
    {
        private String [] m;
        public MyAxisValueFormatter(String[] arr)
        {
            this.m = arr;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
         //   Log.i("deep",Integer.toString((int)value));
            return m[(int) value];
        }

        @Override
        public int getDecimalDigits() {
            return 0;
        }


    }
    private class FTPDownloader extends AsyncTask<Void, Void, Void> {

        FTPClient ftp = null;
        InputStream in;

        public FTPDownloader() {
            ftp = null;
        }

        @Override
        protected Void doInBackground(Void... voids) {
           String s=null;
            Log.i("FTP","FTP Service Started");
           while(s==null) {
               try {

                   ftp = new FTPClient();
                   ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
                   int reply;
                   ftp.connect("link of server");
                   reply = ftp.getReplyCode();
                   if (!FTPReply.isPositiveCompletion(reply)) {
                       ftp.disconnect();
                       throw new Exception("Exception in connecting to FTP Server");
                   }
                   ftp.login("uname", "passwd");
                   ftp.setFileType(FTP.BINARY_FILE_TYPE);

                   ftp.enterLocalPassiveMode();


               } catch (Exception e) {
                   e.printStackTrace();
                   Log.i("FTP", "Error Occurred.");
               }
               try {

                   in = ftp.retrieveFileStream("fle path on FTP server");
                   int data = in.read();
                   s = "";
                   int commas = 0;
                   int max = 0;
                   int xa = 0, xa1 = 0,xa2=0;
                   while (data != -1) {

                       char ch = (char) data;
                       if (ch != ',')
                       {
                           s = s + ch;
                           data = in.read();
                           continue;
                       }
                       else
                           {
                           ++commas;
                           s = s + " ";

                           if (commas == 8) {
                               barEntries.add(new BarEntry(xa,(int) Float.parseFloat(s)));
                               Log.i("FTP", s);
                               ++xa;
                           }
                           if (commas == 6) {
                               barEntries1.add(new BarEntry(xa1, (int)Float.parseFloat(s)));
                               Log.i("barentries1", s);
                               ++xa1;
                           }

                           if (commas == 4) {

                               machines.add(s);
                               Log.i("machines", s);
                           }
                           gblprpd.add(s);
                           s = "";
                       }
                           data = in.read();

                           if (commas == 8) {
                               s = s + (char) data;
                               for (int i = 0; i < 5; ++i)
                                   s = s + (char) in.read();
                               s = s + " ";
                               percentage.add(new BarEntry(xa2,(int)Float.parseFloat(s)));
                               ++xa2;
                               gblprpd.add(s);
                               s = "";
                               commas = 0;
                               data = in.read();
                           }
                       }



                   in.close();
                   //        Log.i("Max",Integer.toString(max));
                   //        Log.i("Num",Integer.toString(gblprpd.size()));
//   Making spaces equal for all the Machine Names.
                   for (int i = 3; i < gblprpd.size(); i += 9) {

                       String str = gblprpd.get(i);
                       int tmp = 0;
                       Log.i("Size", Integer.toString(str.length()));
                       for (int j = 0; j < 10 - gblprpd.get(i).length(); ++j) {


                           str = str + " ";

                       }

                       Log.i("Breaker", "break");
                       //str=str+"|";
                       Log.i("Size", Integer.toString(str.length()));

                       gblprpd.set(i, str);
                       Log.i("Machine", gblprpd.get(i));

                   }
                   //Moving machine name to last making above loop redundant.
                   String stmp = "";
                   for (int i = 0; i < gblprpd.size(); ++i) {
                       s = "";
                       for (int j = 0; j < 9; ++j) {
                           if (j == 3) {
                               stmp = gblprpd.get(i);
                           } else
                               s = s + gblprpd.get(i) + " | ";

                           ++i;
                       }
                       s = s + stmp;
                       --i;

                       dta.add(s);

                   }
               }
                    catch (IOException e) {
                   e.printStackTrace();
                   Log.i("FTP", "Error Occurred");

               }

               if (this.ftp.isConnected()) {
                   try {
                       this.ftp.logout();
                       this.ftp.disconnect();
                   } catch (IOException f) {
                       // do nothing as file is already downloaded from FTP server
                   }
               }
           }
           Log.i("FTP","Service finished Successfully");
            return null;
        }

        @Override
        protected void onPreExecute()
        {

        }

        @Override
        protected void onPostExecute(Void cd) {

            ProductionReport pr = new ProductionReport();
            for(int i=0;i<dta.size();++i)
            {
                pr.dta.add(dta.get(i));
            }

            Button greport = (Button) findViewById(R.id.greport);
            greport.setVisibility(View.VISIBLE);
            TextView output = (TextView) findViewById(R.id.output);
            output.setText("");


            BarChart chart = (BarChart) findViewById(R.id.chart2);
            BarDataSet bds3 = new BarDataSet(percentage,"percentage");
         //   BarDataSet bds = new BarDataSet(barEntries, "target");
            BarData ddata = new BarData(bds3);
            float barWidth = 0.2f;//0.45
            ddata.setBarWidth(barWidth);
            chart.setScaleEnabled(true);
            chart.setPinchZoom(true);
            chart.setData(ddata);
            XAxis xa = chart.getXAxis();
      //      Log.i("Message5432",Integer.toString(machines.size()));
            String[] mac = new String[machines.size()];
            String[] mac1 = new String[machines.size()];
            for(int i=0;i<machines.size();++i)
            {
               mac[i] = machines.get(i);
               mac1[i] = mac[i];
               Log.i("Message5432",mac[i]);
            }
            ex = 0;
            xa.setValueFormatter(new MyAxisValueFormatter(mac));
         //   chart.setVisibleXRangeMinimum(machines.size());
            chart.fitScreen();
            xa.setLabelCount(machines.size(),true);
           // xa.setCenterAxisLabels(true);
            xa.setAxisMinimum(0);
            xa.setGranularity(1f);
            xa.setGranularityEnabled(true);
            //xa.resetAxisMaximum();
            chart.setTouchEnabled(true);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            chart.setFitBars(true); // make the x-axis fit exactly all bars
            chart.setVisibility(View.VISIBLE);
            chart.invalidate();


            BarDataSet bds2 = new BarDataSet(barEntries, "Target");

          /*  BarChart chart1 = (BarChart) findViewById(R.id.chart1);
            BarDataSet bds1 = new BarDataSet(barEntries1,"Prepared");
            bds1.setColor(Color.parseColor("#F44336"));
            BarData ddata1 = new BarData(bds1,bds2);
            chart1.setScaleEnabled(true);
            chart1.setPinchZoom(true);
            chart1.setData(ddata1);
            XAxis xa1 = chart1.getXAxis();
            ex1 = 0;
            xa1.setValueFormatter(new MyAxisValueFormatter2(mac1));

            //xa1.setCenterAxisLabels(true);
            chart1.setTouchEnabled(true);
            chart1.setDragEnabled(true);
            chart1.setScaleEnabled(true);
            float groupSpace = 0.0f;//0.08
            float barSpace = 0.0f; // //0.02   x2 dataset
         //   chart1.setVisibleXRangeMinimum(machines.size());;


            chart1.fitScreen();
            ddata1.setBarWidth(barWidth);
            xa1.setLabelCount(machines.size()+1,true);
            chart1.groupBars( 0.0f,groupSpace, barSpace);
            chart1.setFitBars(true); // make the x-axis fit exactly all bars
            chart1.setVisibility(View.VISIBLE);
            xa1.setGranularity(1f);
            xa1.setAxisMinimum(0);
            xa1.setGranularityEnabled(true);
            xa1.setCenterAxisLabels(true);
            chart1.invalidate();
*/
            //chart.invalidate();
            //  Toast.makeText(MainActivity.this,"FTP File downloaded successfully",Toast.LENGTH_LONG).show();

            //  roes.get(0).setText("Date                 |" + " Shift                    |" + " Machine   |" + "Total Pcs  |" + " Boxes   |" + " S2MM 1120");
        /*
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


            Button ggraph = (Button) findViewById(R.id.ggraph);
            ggraph.setVisibility(View.VISIBLE);
            */

        }
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
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }
    public void backtomenu(View view) {
        Intent i = new Intent(this,ProductionMenu.class);
        startActivity(i);
    }


}






