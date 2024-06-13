package com.example.reportsfordrivers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.reportsfordrivers.ui.theme.AppTheme
import com.google.android.gms.ads.MobileAds
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