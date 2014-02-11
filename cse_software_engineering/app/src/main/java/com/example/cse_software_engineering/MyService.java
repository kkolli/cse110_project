package com.example.cse_software_engineering;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by Krishna on 2/10/14.
 */
public class MyService extends Service{
    private final IBinder mBinder = new LocalBinder();
    private NotificationManager notificationManager;

    public class LocalBinder extends Binder {
        public MyService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MyService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        //return mBinder;
        return null;
    }

   @Override
   public  void onCreate()
   {
       Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
       notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
       this.createNotification("Groceries");
   }



    private void createNotification(String typeofplaceit)
    {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pintent =  PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("A place it is nearby!")
                        .setContentText(typeofplaceit)
                        .addAction(R.drawable.ic_launcher, "Discard", null)
                        .addAction(R.drawable.ic_launcher, "Reschedule", pintent);

        notificationManager.notify(0, mBuilder.build());
    }

    private void findPlaceItLocation()
    {
        /*Location locationA = new Location("point A");

        locationA.setLatitude(location.getLatitude());
        locationA.setLongitude(location.getLongitude());

        Location locationB = new Location("point B");

        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);

        distance = locationA.distanceTo(locationB);*/
    }

   @Override
   public void onDestroy()
   {
       Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
       stopSelf();
   }
}
