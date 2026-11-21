package com.example.jathrproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val JathrLightColorScheme = lightColorScheme(
    primary = PrimaryPurple,
    onPrimary = Color.White,
    primaryContainer = LightLavender,
    onPrimaryContainer = DarkPurple,
    secondary = SecondaryText,
    onSecondary = Color.White,
    background = ScreenBackground,
    onBackground = MainText,
    surface = Color.White,
    onSurface = MainText,
    surfaceVariant = LightLavender,
    onSurfaceVariant = SecondaryText,
    outline = BorderColor
)

@Composable
fun JathrProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = JathrLightColorScheme,
        typography = Typography
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            content()
        }
    }
}
