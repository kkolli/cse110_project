package com.example.cse_software_engineering;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements GoogleMap.OnMapClickListener, GoogleMap.CancelableCallback {
    private GoogleMap mMap;
    private List mMarkers = new ArrayList();
    private Iterator marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setUpMapIfNeeded();
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        Button btnUpdate = (Button) findViewById(R.id.sendLocationBtn);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.sendLocation);
                String geoData = editText.getText().toString();
                String[] coordinate = geoData.split(",");
                double latitude = Double.valueOf(coordinate[0]).doubleValue();
                double longitude = Double.valueOf(coordinate[1]).doubleValue();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 12),2000,null);
            }
        });

    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // The Map is verified. It is now safe to manipulate the map.

            }
        }
    }

    @Override
    public void onMapClick(LatLng position) {

        final LatLng pos = position;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("New Place-it");
        alert.setMessage("Please enter a Marker Title:");
        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                Toast.makeText(MainActivity.this, "Tag added!", Toast.LENGTH_SHORT).show();
                Marker added = mMap.addMarker(new MarkerOptions()
                        .position(pos)
                        .title(value)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
                        .snippet("You can put other info here!"));

                mMarkers.add(added);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(MainActivity.this, "Nothing added!", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();


    }

    @Override
    public void onFinish() {
        if (marker.hasNext()) {
            Marker current = (Marker)marker.next();
            mMap.animateCamera(CameraUpdateFactory.newLatLng(current.getPosition()), 2000, this);
            current.showInfoWindow();
        }
    }

    @Override
    public void onCancel() {

    }
}
