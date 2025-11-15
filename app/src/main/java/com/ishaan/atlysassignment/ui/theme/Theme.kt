package com.ishaan.atlysassignment.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Teal80,
    onPrimary = Teal20,
    primaryContainer = Teal30,
    onPrimaryContainer = Teal90,

    secondary = Amber80,
    onSecondary = Amber20,
    secondaryContainer = Amber30,
    onSecondaryContainer = Amber90,

    tertiary = Red80,
    onTertiary = Red20,
    tertiaryContainer = Red30,
    onTertiaryContainer = Red90,

    error = Error80,
    onError = Error20,
    errorContainer = Error30,
    onErrorContainer = Error90,

    background = DarkTheater,
    onBackground = TextOnDark,

    surface = DarkCard,
    onSurface = TextOnDark,

    surfaceVariant = DarkCard, // Can use DarkCard or a slightly different gray
    onSurfaceVariant = TextOnDarkSubtle,

    outline = DarkOutline,
    inverseSurface = Neutral90,
    inverseOnSurface = Neutral10
)

private val LightColorScheme = lightColorScheme(
    primary = Teal40,
    onPrimary = Teal100,
    primaryContainer = Teal90,
    onPrimaryContainer = Teal10,

    secondary = Amber40,
    onSecondary = Amber100,
    secondaryContainer = Amber90,
    onSecondaryContainer = Amber10,

    tertiary = Red40,
    onTertiary = Red100,
    tertiaryContainer = Red90,
    onTertiaryContainer = Red10,

    error = Error40,
    onError = Error100,
    errorContainer = Error90,
    onErrorContainer = Error10,

    background = AlmostWhite,
    onBackground = TextOnLight,

    surface = AlmostWhite,
    onSurface = TextOnLight,

    surfaceVariant = Neutral90, // A light gray for cards/dividers
    onSurfaceVariant = TextOnLightSubtle,

    outline = LightOutline,
    inverseSurface = Neutral10,
    inverseOnSurface = Neutral90
)

@Composable
fun AtlysAssignmentTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}