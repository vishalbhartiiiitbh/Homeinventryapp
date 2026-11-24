package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.core.navigation.HomeStockMainApp
import com.example.myapplication.ui.theme.HomeStockTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomeStockTheme {
                HomeStockMainApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeStockAppPreview() {
    HomeStockTheme {
        HomeStockMainApp()
    }
}
