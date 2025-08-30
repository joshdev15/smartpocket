package com.joshdev.smartpocket.ui.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.joshdev.smartpocket.ui.activities.home.HomeActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val goToHome = Intent(this, HomeActivity::class.java)
        goToHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(goToHome)

//        setContent {
//            SmartPocketTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Column(
//                        modifier = Modifier.padding(innerPadding)
//                    ) {
//                        Text("Hola mundo!")
//                    }
//                }
//            }
//        }
    }
}