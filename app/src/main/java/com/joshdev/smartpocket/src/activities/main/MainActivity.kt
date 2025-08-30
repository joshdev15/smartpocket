package com.joshdev.smartpocket.src.activities.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.joshdev.smartpocket.src.activities.home.HomeActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val goToHome = Intent(this, HomeActivity::class.java)
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