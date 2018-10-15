package com.example.quickstart;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class pmonthly_report extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    static int flag;
    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    ProgressDialog mProgress;
    int i = 0, res = 0,num;
    int set = 0, sale_prod = -1;
    String usid, pwd;
    TextView output;
    ArrayList<String> dta = new ArrayList<>();
    List<TextView> roes = new ArrayList<TextView>();
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    private static final String BUTTON_TEXT = "Fetch data";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {SheetsScopes.SPREADSHEETS_READONLY};
    java.io.File file;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmonthly_report);
        ScrollView sv = (ScrollView) findViewById(R.id.sv);
        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Fetching Data ...");
        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());



        roes.add((TextView)findViewById(R.id.m01));
        roes.add((TextView)findViewById(R.id.m02));
        roes.add((TextView)findViewById(R.id.m03));
        roes.add((TextView)findViewById(R.id.m11));
        roes.add((TextView)findViewById(R.id.m12));
        roes.add((TextView)findViewById(R.id.m13));
        roes.add((TextView)findViewById(R.id.m21));
        roes.add((TextView)findViewById(R.id.m22));
        roes.add((TextView)findViewById(R.id.m23));
        roes.add((TextView)findViewById(R.id.m31));
        roes.add((TextView)findViewById(R.id.m32));
        roes.add((TextView)findViewById(R.id.m33));
        roes.add((TextView)findViewById(R.id.m41));
        roes.add((TextView)findViewById(R.id.m42));
        roes.add((TextView)findViewById(R.id.m43));
        roes.add((TextView)findViewById(R.id.m51));
        roes.add((TextView)findViewById(R.id.m52));
        roes.add((TextView)findViewById(R.id.m53));
        roes.add((TextView)findViewById(R.id.m61));
        roes.add((TextView)findViewById(R.id.m62));
        roes.add((TextView)findViewById(R.id.m63));
        roes.add((TextView)findViewById(R.id.m71));
        roes.add((TextView)findViewById(R.id.m72));
        roes.add((TextView)findViewById(R.id.m73));
        roes.add((TextView)findViewById(R.id.m81));
        roes.add((TextView)findViewById(R.id.m82));
        roes.add((TextView)findViewById(R.id.m83));
        roes.add((TextView)findViewById(R.id.m91));
        roes.add((TextView)findViewById(R.id.m92));
        roes.add((TextView)findViewById(R.id.m93));
        roes.add((TextView)findViewById(R.id.m101));
        roes.add((TextView)findViewById(R.id.m102));
        roes.add((TextView)findViewById(R.id.m103));
        roes.add((TextView)findViewById(R.id.m111));
        roes.add((TextView)findViewById(R.id.m112));
        roes.add((TextView)findViewById(R.id.m113));
        roes.add((TextView)findViewById(R.id.m121));
        roes.add((TextView)findViewById(R.id.m122));
        roes.add((TextView)findViewById(R.id.m123));
        roes.add((TextView)findViewById(R.id.m131));
        roes.add((TextView)findViewById(R.id.m132));
        roes.add((TextView)findViewById(R.id.m133));
        roes.add((TextView)findViewById(R.id.m141));
        roes.add((TextView)findViewById(R.id.m142));
        roes.add((TextView)findViewById(R.id.m143));
        roes.add((TextView)findViewById(R.id.m151));
        roes.add((TextView)findViewById(R.id.m152));
        roes.add((TextView)findViewById(R.id.m153));
        roes.add((TextView)findViewById(R.id.m161));
        roes.add((TextView)findViewById(R.id.m162));
        roes.add((TextView)findViewById(R.id.m163));
        roes.add((TextView)findViewById(R.id.m171));
        roes.add((TextView)findViewById(R.id.m172));
        roes.add((TextView)findViewById(R.id.m173));
        roes.add((TextView)findViewById(R.id.m181));
        roes.add((TextView)findViewById(R.id.m182));
        roes.add((TextView)findViewById(R.id.m183));





        sv.setVisibility(View.GONE);
        hsv.setVisibility(View.GONE);
        for (int i = 0; i < 54; ++i)
            roes.get(i).setText("");
        getResultsFromApi();
    }


    public void callAPI(View v)
    {
        ScrollView sv = (ScrollView) findViewById(R.id.sv);
        sv.setVisibility(View.GONE);
        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
        hsv.setVisibility(View.GONE);
        TextView output = (TextView) findViewById(R.id.output);
        output.setVisibility(View.VISIBLE);
        output.setText("Please wait ...");
        // Toast.makeText(MainActivity.this,"Check",Toast.LENGTH_LONG).show();
        for (int k = 0; k < 39; ++k) {
            roes.get(k).setVisibility(View.VISIBLE);
            roes.get(k).setText("");
        }
        getResultsFromApi();
    }
    private void getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            mOutputText.setText("No network connection available.");
        } else {
            new pmonthly_report.MakeRequestTask(mCredential).execute();
        }
    }



    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                pmonthly_report.this,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }

    /**
     * An asynchronous task that handles the Google Sheets API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.sheets.v4.Sheets mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("GBL")
                    .build();
        }

        /**
         * Background task to call Google Sheets API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of names and majors of students in a sample spreadsheet:
         * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
         *
         * @return List of names and majors
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            String spreadsheetId, range;
            spreadsheetId = "link of spreadsheet";//change link here.
            range = "monthly!A3:C";


            List<String> results = new ArrayList<String>();
            ValueRange response = this.mService.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
            List<List<Object>> values = response.getValues();

            if (values != null) {
                set = 0;
                num = 0;
                int xa = 0;
                String strmp="";
                //  results.add("Date               |" + "Shift              |" + " Machine   |" + "Total Pcs  |" + " Boxes   |" + " S2MM 1120");
                for (List row : values) {
                    String spaces = "";
                    int len = row.get(0).toString().length();
                    for (int i = len; i < 30; ++i)
                        spaces += " ";
                    strmp = row.get(0).toString().trim() + spaces;
                    dta.add(strmp);
                    spaces = "";
                    len = row.get(1).toString().length();
                    for (int i = len; i < 30; ++i)
                        spaces += " ";
                    strmp = row.get(1).toString().trim() + spaces;
                    dta.add(strmp);
                    spaces = "";
                    len = row.get(2).toString().length();
                    for (int i = len; i < 30; ++i)
                        spaces += " ";
                    strmp = row.get(2).toString().trim() + spaces;
                    dta.add(strmp);
                    num+=3;
                }
                results.add("Fdfdwf");
            }
            return results;
        }





        @Override
        protected void onPreExecute() {
            //  mOutputText.setText("");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(List<String> output) {

            mProgress.hide();
            if (output == null || output.size() == 0) {

                //   mOutputText.setText("No results returned.");
            } else {

                set = 1;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        //roes.get(0).setText("Date                 |" + " Shift                    |" + " Machine   |" + "Total Pcs  |" + " Boxes   |" + " S2MM 1120");
                        TextView output = (TextView) findViewById(R.id.output);
                        output.setText("Data Retrieved:");
                        //   Log.i("Flag",dta.get(3));

                        for (int k = 0; k < num; ++k) {
                            roes.get(k).setText(dta.get(k));
                        }
                        roes.get(num-3).setBackgroundColor(Color.parseColor("#CCFFB3"));
                        roes.get(num-2).setBackgroundColor(Color.parseColor("#F9DABF"));
                        roes.get(num-1).setBackgroundColor(Color.parseColor("#BFF6F9"));
                        for (int k = num ; k < 57; ++k)
                            roes.get(k).setVisibility(View.GONE);
                        ScrollView sv = (ScrollView) findViewById(R.id.sv);
                        sv.setVisibility(View.VISIBLE);
                        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv);
                        hsv.setVisibility(View.VISIBLE);

                    }
                });
            }
        }



        @Override
        protected void onCancelled() {
            mProgress.hide();
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            MainActivity.REQUEST_AUTHORIZATION);
                } else {
                    mOutputText.setText("The following error occurred:\n"
                            + mLastError.getMessage());
                }
            } else {
                mOutputText.setText("Request cancelled.");
            }
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
    public void backtomenu(View view) {
        Intent i = new Intent(this,ProductionMenu.class);
        startActivity(i);
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
