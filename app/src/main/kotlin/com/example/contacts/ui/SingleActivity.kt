package com.example.contacts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.contacts.ui.main.MainScreen
import com.example.contacts.ui.theme.ExampleTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Единственная активити приложения
 */
@AndroidEntryPoint
internal class SingleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleTheme {
                MainScreen()
            }
        }
    }
}
