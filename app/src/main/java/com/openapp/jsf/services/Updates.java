package com.openapp.jsf.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.openapp.jsf.activities.Home;
import com.openapp.jsf.activities.R;
import com.openapp.jsf.database.Items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class Updates extends Service  implements Runnable{
    private Items itemsDBHelper;
    private final int SLEEP_TIME = (1000 * 60)* 10; //10 MINUTES
    private final int SERVER_PORT = 3000;
    private final String SERVER_ADDRESS = "10.0.2.2";
    private final int CHAR_BUFFER_LENGTH = 1024;
    private static boolean serviceRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(serviceRunning) {
            return START_STICKY;
        }
        serviceRunning = true;
        startDB();
        new Thread(this).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startDB() {
        itemsDBHelper = new Items(this);
        itemsDBHelper.setDB(itemsDBHelper.getWritableDatabase());
    }

    @Override
    public void run() {

        while(true)
        {
            try {
                Log.d("printing","printing!"+Thread.currentThread().getName());
                Thread.sleep(SLEEP_TIME);
                if(isNotConnected()) continue;
                downloadUpdates();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void downloadUpdates(){
        HttpURLConnection urlConnection = null;
        try {
            String url_string = "http://"+SERVER_ADDRESS+":"+SERVER_PORT+"/updates?timestamp="+URLEncoder.encode(itemsDBHelper.getLatestTimestamp(), Charset.defaultCharset().name());
            URL url = new URL(url_string);
            Log.d("url",url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
            int charsRead = 0;
            char[] charsBuffer = new char[CHAR_BUFFER_LENGTH];
            String jsonString="";
            while(in.ready()){
                charsRead = in.read(charsBuffer,0,charsBuffer.length);
                jsonString = jsonString.concat(new String(charsBuffer,0,charsRead));
            }
            JSONObject json = new JSONObject(jsonString);
            JSONArray items = (JSONArray) json.get("items");

            if(items.length()>0){
                addUpdatesToDB(items);
                fireNotification();
            }

        }
          catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
        }
    }

    private void fireNotification() {
        Intent intent = new Intent(this, Home.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification n  = new NotificationCompat.Builder(this)
                .setContentTitle("You have new updates")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.icon)
                .setAutoCancel(true)
                .setContentIntent(pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }

    private void addUpdatesToDB(JSONArray items) throws JSONException {
        for(int i = 0;i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            itemsDBHelper.addItem(item.getString("content"), item.getInt("type"), item.getString("timestamp"));
        }
    }

    private boolean isNotConnected() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return !isConnected;
    }

}
