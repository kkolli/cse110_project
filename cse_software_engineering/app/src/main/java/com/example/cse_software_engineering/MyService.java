package com.example.cse_software_engineering;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;

/**
 * Created by Krishna on 2/10/14.
 */
public class MyService extends Service{
    private final IBinder mBinder = new LocalBinder();
    private NotificationManager notificationManager;
    public static Placeits placeits;

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
       placeits = new Placeits();

   }



    private void createNotification(String typeofplaceit)
    {
        Intent intent = new Intent(this, NotificationReceiver.class);
        Intent intent2 = new Intent(this, Discard.class);
        PendingIntent pintent =  PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent pintent2 =  PendingIntent.getActivity(this, 0, intent2, 0);



        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("A place it is nearby!")
                        .setContentText(typeofplaceit)
                        .addAction(R.drawable.ic_launcher, "Discard", pintent2)
                        .addAction(R.drawable.ic_launcher, "Reschedule", pintent);

        mBuilder.setPriority(100);
        notificationManager.notify(0, mBuilder.build());
    }

    private void findPlaceItLocation()
    {



        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Marker m = placeits.getItem(0);
                LatLng ll = m.getPosition();
                Location locationA = new Location("Point A");
                locationA.setLatitude(ll.latitude);
                locationA.setLongitude(ll.longitude);
                double distance = locationA.distanceTo(location);
                if(distance > 0.0)
                    MyService.this.createNotification(m.getTitle());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

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
