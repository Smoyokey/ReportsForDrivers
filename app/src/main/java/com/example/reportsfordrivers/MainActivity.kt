package com.example.reportsfordrivers

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.reportsfordrivers.ui.layouts.createreports.PermissionScreenState
import com.example.reportsfordrivers.ui.theme.AppTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    val callPermissionRequest =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
//            permissionScreenState.value = getState()
//        }
//
//    private val permissionScreenState: MutableState<PermissionScreenState> by lazy {
//        mutableStateOf(getState())
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        MobileAds.initialize(this) {}

        setContent {
            AppTheme(content = {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReportsForDriversApp(activity = this@MainActivity)
                }
            })
        }
    }

//    private fun getState() = if(isPermissionGranted()) {
//        PermissionScreenState(title = "Can't", buttonText = "No save")
//    } else {
//        PermissionScreenState(title = "Can", buttonText = "Save")
//    }
//
//    private fun isPermissionGranted() = ContextCompat.checkSelfPermission(
//        this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//    ) == PackageManager.PERMISSION_GRANTED
//

}