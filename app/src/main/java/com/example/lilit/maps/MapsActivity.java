package com.example.lilit.maps;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    TextView status;
    TextView myLocation;
    LocationManager locationManager;
    Button button;


    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            myLocation.setText("latitide : " + location.getLatitude() + "longitude : " + location.getLongitude());
        //    Log.d("MyLog", "onLocationChanged");
        }

        @Override
        public void onStatusChanged(String provider, int stat, Bundle bundle) {

            status.setText("status is : " + String.valueOf(stat));
            Log.d("MyLog", "onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String s) {
            status.setText("status is : " + String.valueOf(s));
          //  Log.d("MyLog", "onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String s) {
            status.setText("status is : " + String.valueOf(s));
          //  Log.d("MyLog", "onProviderDisabled");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();
        mMap.setMyLocationEnabled(true);
        status = (TextView) findViewById(R.id.gps_status);
        myLocation = (TextView) findViewById(R.id.my_location);
        button = (Button) findViewById(R.id.map_test);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });
        initMap();
}

    private void initMap (){
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("MyLog", "you clicked : " + latLng.toString());
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
}
