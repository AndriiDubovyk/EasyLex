package com.andriidubovyk.easylex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.andriidubovyk.easylex.presentation.screen.flashacards.FlashcardsScreen
import com.andriidubovyk.easylex.ui.theme.EasyLexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyLexTheme {
                FlashcardsScreen(Modifier.fillMaxSize())
            }
        }
    }
}