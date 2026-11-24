package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = LightGreen,
    onPrimaryContainer = PrimaryGreenDark,
    secondary = PrimaryGreen,
    onSecondary = Color.White,
    secondaryContainer = LightBlue,
    onSecondaryContainer = MainTextLight,
    tertiary = Color(0xFFE5A100),
    tertiaryContainer = LightYellow,
    onTertiaryContainer = MainTextLight,
    background = AppBackgroundLight,
    onBackground = MainTextLight,
    surface = AppSurfaceLight,
    onSurface = MainTextLight,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = SecondaryTextLight,
    outline = BorderLight,
    error = StatusDanger,
    errorContainer = LightRed,
    onErrorContainer = StatusDanger
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4CAF50),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1B4D24),
    onPrimaryContainer = Color(0xFFC8E6C9),
    secondary = Color(0xFF81C784),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF1E3A5F),
    onSecondaryContainer = Color(0xFFD0E1F9),
    tertiary = Color(0xFFFFB74D),
    tertiaryContainer = Color(0xFF4E3600),
    onTertiaryContainer = Color(0xFFFFE0B2),
    background = AppBackgroundDark,
    onBackground = MainTextDark,
    surface = AppSurfaceDark,
    onSurface = MainTextDark,
    surfaceVariant = Color(0xFF262C34),
    onSurfaceVariant = SecondaryTextDark,
    outline = BorderDark,
    error = Color(0xFFEF5350),
    errorContainer = Color(0xFF5F1414),
    onErrorContainer = Color(0xFFFFCDD2)
)

@Composable
fun HomeStockTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
