package com.example.kivocabo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnSuccessListener

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private var TAG: String = MainActivity::class.java.simpleName;
    private lateinit var map: GoogleMap
    private lateinit var mgetCurrentLocation: GetCurrentLocation
    private lateinit var mLatLng: LatLng
    private var DEFAULT_ZOOM: Float = 18F

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION])
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)*/
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mgetCurrentLocation = GetCurrentLocation(this)
            mgetCurrentLocation.CurrentLocation()

            Handler(Looper.myLooper()!!).postDelayed({
                Log.v(
                    TAG,
                    "Enlem : " + mgetCurrentLocation.latitude + " Boylam : " + mgetCurrentLocation.latitude
                )
                val mapFragment =
                    supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
                mapFragment.getMapAsync(this)
                mLatLng = LatLng(mgetCurrentLocation.latitude, mgetCurrentLocation.longitude);

            }, 2000)

        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.v(TAG, "grantResults : " + grantResults.size);
    }

    override fun onMapReady(p0: GoogleMap) {

        p0.isIndoorEnabled = true
        /*if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            p0.isMyLocationEnabled = true*/
        p0.addMarker(MarkerOptions().position(mLatLng))

        p0?.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,DEFAULT_ZOOM))

        //map = p0;
    }
}