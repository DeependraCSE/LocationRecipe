package com.example.demoapp.locationapp

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.example.demoapp.MainActivity

@Composable
fun LocationDisplay(locationUtils : LocationUtils,
                    viewModal: LocationViewModal,
                    context : Context){

    val location = viewModal.location.value
    val address = location?.let { locationUtils.reverseGeocodeLocation(location) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {permissions->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true){
                // I have access to location
                locationUtils.requestLocationUpdates(viewModal=viewModal)
            }else{
                // Ask for location
                val rationalRequired =
                    ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)

                if (rationalRequired){
                    Toast.makeText(context,"Location permission are required", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context,"Location permission are required please enable android setting", Toast.LENGTH_LONG).show()
                }
            }
        })


    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        if (location != null){
            Text(text = "Address: ${location.latitude} and ${location.longitude} \n $address")
        }else{
            Text(text = "Location not available")
        }
        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)){
                //Permission already granted
                locationUtils.requestLocationUpdates(viewModal)
            }else{
                //Request permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ))
            }
        }) {
            Text(text = "Get Location")
        }
    }
}

@Composable
fun Myapp(viewModal: LocationViewModal){
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)

    LocationDisplay(locationUtils = locationUtils,viewModal= viewModal, context = context)
}