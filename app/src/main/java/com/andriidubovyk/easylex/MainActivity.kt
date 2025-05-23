package com.andriidubovyk.easylex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.andriidubovyk.easylex.presentation.component.bottom_nav_bar.BottomNavigationPanel
import com.andriidubovyk.easylex.presentation.navigation.NavigationHost
import com.andriidubovyk.easylex.presentation.screen.settings.utils.NotificationReceiver
import com.andriidubovyk.easylex.ui.theme.EasyLexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NotificationReceiver.createNotificationChannel(this)
        enableEdgeToEdge()
        setContent {
            EasyLexTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNavigationPanel(navController = navController) }
                    ) {
                        NavigationHost(
                            navController = navController,
                            modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }
    }
}