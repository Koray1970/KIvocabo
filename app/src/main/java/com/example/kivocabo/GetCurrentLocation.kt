package com.example.kivocabo

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationRequest
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationRequestCompat.Quality
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource

class GetCurrentLocation(con:Context) {
    private var mContext:Context=con
    public var latitude: Double = 0.0
    public var longitude: Double = 0.0


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    public fun CurrentLocation() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(mContext.applicationContext)
        if (ActivityCompat.checkSelfPermission(
                mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val mcancelationTokenSource: CancellationTokenSource = CancellationTokenSource()
        fusedLocationProviderClient!!.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            mcancelationTokenSource.token
        )
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(mContext, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
    }
}