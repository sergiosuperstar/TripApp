package com.example.icf.tripappclient.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.icf.tripappclient.PermissionUtils;
import com.example.icf.tripappclient.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import static com.example.icf.tripappclient.R.id.map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
        GoogleMap.OnMyLocationButtonClickListener,  ActivityCompat.OnRequestPermissionsResultCallback{

    private GoogleMap map;
    private Polyline line1;
    private Polyline line2;

    private ArrayList<MarkerOptions> line1Stations  = new ArrayList<>();
    private ArrayList<MarkerOptions> line2Stations  = new ArrayList<>();

    private ArrayList<Marker> line1Markers = new ArrayList<>();
    private ArrayList<Marker> line2Markers = new ArrayList<>();

    private boolean mPermissionDenied = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        CheckBox check1 = (CheckBox) findViewById(R.id.line1box);
        CheckBox check2 = (CheckBox) findViewById(R.id.line2box);

        check1.setChecked(true);
        check2.setChecked(true);

        line1Stations.add(new MarkerOptions().position(new LatLng(45.26562909804279, 19.805066588040745)).title("Majevica"));
        line1Stations.add(new MarkerOptions().position(new LatLng(45.25904408732439, 19.809529783841526)).title("Detelinara"));
        line1Stations.add(new MarkerOptions().position(new LatLng(45.2504643038709, 19.815280439969456)).title("Klinički centar"));
        line1Stations.add(new MarkerOptions().position(new LatLng(45.24218539686798, 19.821031096097386)).title("Bulevar Evrope"));
        line1Stations.add(new MarkerOptions().position(new LatLng(45.24152061477978, 19.832017424222386)).title("Park city"));
        line1Stations.add(new MarkerOptions().position(new LatLng(45.248485331829535, 19.83890533697263)).title("Maksima Gorkog"));
        line1Stations.add(new MarkerOptions().position(new LatLng(45.25408972262825, 19.842317106839573)).title("Centar"));

        line2Stations.add(new MarkerOptions().position(new LatLng(45.25300212130111, 19.804379942532933)).title("Bistrica"));
        line2Stations.add(new MarkerOptions().position(new LatLng(45.25765449206412, 19.817340376492893)).title("Detelinara centar"));
        line2Stations.add(new MarkerOptions().position(new LatLng(45.26013157293705, 19.830729963895237)).title("Bulevar oslobođenja"));
        line2Stations.add(new MarkerOptions().position(new LatLng(45.25538877469383, 19.834978582974827)).title("Novosadskog sajma"));
        line2Stations.add(new MarkerOptions().position(new LatLng(45.248485331829535, 19.83890533697263)).title("Maksima Gorkog"));
        line2Stations.add(new MarkerOptions().position(new LatLng(45.250283026858014, 19.847724440213597)).title("Stražilovska"));

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        //map.addMarker(new MarkerOptions().position(new LatLng(45.2671, 19.8335)).title("Marker"));

        this.map = map;

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(45.22013157293705, 19.830729963895237));

        map.moveCamera(center);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(center);
        map.animateCamera(CameraUpdateFactory.zoomTo( 13.2f ));

        line1 = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(   new LatLng(45.26562909804279, 19.805066588040745),
                        new LatLng(45.23964709613551, 19.822576048489964),
                        new LatLng(45.240115481588326, 19.82637405645505),
                        new LatLng(45.24383220990154, 19.84143734228269),
                        new LatLng(45.25185407508154, 19.83716726553098),
                        new LatLng(45.25408972262825, 19.842317106839573)));

        line1.setColor(Color.argb(127, 0, 255, 0));

        line2 = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(   new LatLng(45.25300212130111, 19.804379942532933),
                        new LatLng(45.25850033670732, 19.82017278921262),
                        new LatLng(45.259587832734276, 19.828240873929417),
                        new LatLng(45.2604940635127, 19.832103254910862),
                        new LatLng(45.247865944733604, 19.839227202054417),
                        new LatLng(45.250283026858014, 19.847724440213597)));

        line2.setColor(Color.argb(127, 255, 0, 0));

        for(MarkerOptions marker: line1Stations){
            Marker mark = map.addMarker(marker);
            mark.setVisible(false);
            line1Markers.add(mark);
        }

        for(MarkerOptions marker: line2Stations){
            Marker mark = map.addMarker(marker);
            mark.setVisible(false);
            line2Markers.add(mark);
        }

        map.setOnPolylineClickListener(this);
        map.setOnMyLocationButtonClickListener(this);
        enableLocation();

    }

    @Override
    public void onPolylineClick(Polyline line) {

        if (line.equals(line1)){
            for(Marker marker: line1Markers){
                marker.setVisible(true);
            }
            for(Marker marker: line2Markers){
                marker.setVisible(false);
            }
        }else{
            for(Marker marker: line2Markers){
                marker.setVisible(true);
            }
            for(Marker marker: line1Markers){
                marker.setVisible(false);
            }
        }

    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.line1box:
                if (checked) {
                    line1.setVisible(true);
                }else {
                   line1.setVisible(false);
                    for(Marker marker: line1Markers){
                        marker.setVisible(false);
                    }
                }
                    break;
            case R.id.line2box:
                if (checked) {
                    line2.setVisible(true);
                }else{
                    line2.setVisible(false);
                    for(Marker marker: line2Markers){
                        marker.setVisible(false);
                    }
                }
                break;
        }
    }

    private void enableLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, 1,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (map != null) {
            // Access to the location has been granted to the app.
            map.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != 1) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}
