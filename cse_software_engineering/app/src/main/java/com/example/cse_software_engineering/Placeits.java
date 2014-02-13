package com.example.cse_software_engineering;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.internal.d;

import java.util.ArrayList;

/**
 * Created by Krishna on 2/12/14.
 */
public class Placeits{
    private ArrayList<Marker> listOfItems;

    public Placeits()
    {
        listOfItems = new ArrayList<Marker>();
    }

    public void add(Marker m)
    {
        listOfItems.add(m);
    }

    public Marker getItem(int i)
    {
        return listOfItems.get(i);
    }


}
