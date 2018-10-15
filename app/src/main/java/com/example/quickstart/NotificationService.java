package com.example.quickstart;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class NotificationService extends IntentService{

    public static String username;
    public static int rune = 1;
    NotificationCompat.Builder notification;
    private static  int id = 342211;
    ArrayList<BarEntry> barEntries = new ArrayList<>();
    ArrayList<BarEntry> barEntries1 = new ArrayList<>();
    ArrayList<Integer> percentage = new ArrayList<>();
    ArrayList<String> gblprpd = new ArrayList<>();
    ArrayList<String> machines = new ArrayList<>();
    FTPClient ftp = null;
    String s;
    InputStream in;
    String txt = "";
    public NotificationService()
    {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        rune = 1;
        Log.i("Message1234","Service Started");
        username = " ";

        try
        {

            java.io.FileInputStream  fis = openFileInput("info");
            int read = fis.read();
            while(read!=-1)
            {
                txt+=(char)read;
                read = fis.read();

            }
            fis.close();
            username = txt.trim();
        }
        catch(IOException e)
        {
          Log.i("Message1234","Error");
        }
        if(!(txt.equals("logged out")))
        {
            //Connecting to FTP server

                try {
                    s = null;
                    ftp = new FTPClient();
                    ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
                    int reply;
                    ftp.connect("link of FTP server");//Link to FTP Server
                    reply = ftp.getReplyCode();
                    if (!FTPReply.isPositiveCompletion(reply)) {
                        ftp.disconnect();
                        throw new Exception("Exception in connecting to FTP Server");
                    }
                    ftp.login("uname", "passwd");//Credentials
                    ftp.setFileType(FTP.BINARY_FILE_TYPE);

                    ftp.enterLocalPassiveMode();

                } catch (Exception e) {
                    e.printStackTrace();
                    s = null;
              //      Log.i("Message1234", "Error Occurred1.");
                }
                try {

                    in = ftp.retrieveFileStream("File path on server");
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
                            int i;
                            for(i=0;i<s.length()-1;++i)
                                if(s.charAt(i)=='0')
                                    continue;
                                else
                                    break;
                            s = s.substring(i,s.length());
                            s.trim();
                            Log.i("Message123456",s);
                            percentage.add(Integer.parseInt(s));
                            ++xa2;
                            gblprpd.add(s);
                            s = "";
                            commas = 0;
                            data = in.read();
                        }
                    }
                    in.close();                /*    in = ftp.retrieveFileStream("");//File PAth
                    data = in.read();
                    s = "";
                    while (data != -1) {

                        char ch = (char) data;
                        s = s + ch;
                        data = in.read();
                    }

                    Log.i("Message1234", "Message Recieved");
                    in.close();

                    //   Log.i("FTP", "Error Occurred.");*/
                } catch (IOException e) {
                    e.printStackTrace();
                    s = null;
                    Log.i("Message1234", "Error Occurred2");

                }

                if (this.ftp.isConnected()) {
                    try {
                        this.ftp.logout();
                        this.ftp.disconnect();
                    } catch (IOException f) {
                        // do nothing as file is already downloaded from FTP server
                    }
                }
             //   Log.i("Message1234",username);
                int res = 0;
                String str="";

           /*    if(s!=null)//get userid from server.
                {
                    String tmp="";
                    StringTokenizer st = new StringTokenizer(s, ",");
                    while (st.hasMoreTokens()) {
                         str = st.nextToken().trim();
                     //    Log.i("Message1234",str);
                     //    Log.i("Message1234",str.substring(0,5));
                        if (str.substring(0,5).equals("USER:")) {

                            if (str.substring(5, str.length()).equals(username)) {
                                res = 1;
                                break;
                            }
                        }

                            tmp = tmp+ str;
                    }
                    Log.i("Message1234", username);
                    Log.i("Message1234", Integer.toString(res));
                    if(res==1) {//Create Notification if userid stored in info file is same as that on the server
                        Log.i("Message1234", "Message Recieved09878");
                        notification = new NotificationCompat.Builder(this, "GBL");
                        notification.setAutoCancel(true);
                        notification.setSmallIcon(R.drawable.borosil);
                        notification.setTicker("New Notification");
                        Log.i("Message1234",tmp);
                        notification.setWhen(System.currentTimeMillis());
                        notification.setContentTitle("Request Recieved");
                        notification.setContentText(tmp);

                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(id, notification.build());
                    }

                }
            }
*/
           Log.i("Message12345","Reached");
            for(int i=0;i<percentage.size();++i)
            {

                Log.i("Message123456",percentage.get(i).toString());
                if(Integer.parseInt(percentage.get(i).toString())>50 )
                {
                    Log.i("Message1234", "Message Recieved09879");
                    notification = new NotificationCompat.Builder(this, "GBL");
                    notification.setAutoCancel(true);
                    notification.setSmallIcon(R.drawable.borosil);
                    notification.setTicker("New Notification");
                    Log.i("Message1234","Hii");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle("50% production complete");
                    notification.setContentText(machines.get(i));

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(id, notification.build());
                    ++id;

                }
                if( Integer.parseInt(percentage.get(i).toString())>99)
                {
                    Log.i("Message1234", "Message Recieved090078");
                    notification = new NotificationCompat.Builder(this, "GBL");
                    notification.setAutoCancel(true);
                    notification.setSmallIcon(R.drawable.borosil);
                    notification.setTicker("New Notification");
                    Log.i("Message1234","Hello");
                    notification.setWhen(System.currentTimeMillis());
                    notification.setContentTitle("100% production complete");
                    notification.setContentText(machines.get(i));

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(id, notification.build());

                }
            }
        }
            /*
             java.io.FileInputStream fis=null;
                    String txt = "";
                    try
                    {
                     fis = openFileInput("info");
                     int read = fis.read();
                    while(read!=-1)
                    {
                        txt+=(char)read;
                        read = fis.read();
                    }
                    fis.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();

                    }
             */
    }
}
