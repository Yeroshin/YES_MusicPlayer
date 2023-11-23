package com.yes.playlistfeature.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class YESSpacing(
    val buttonHeightRegular: Dp = 40.dp,
    val buttonHeightSmall: Dp = 36.dp,
    val buttonWidthSmall: Dp = 80.dp,
    val paddingSmall: Dp = 4.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp
)
data class YESTypography(
    val small: TextStyle,
    val regular: TextStyle,
    val large: TextStyle,
    val colossus: TextStyle
)

data class YESColors(
    val tint: Color,
    val brandedColor: Color,
    val brandedLight: Color,
    val brandedDark: Color,
    val textGray: Color,
    val transparent: Color,

    val black: Color,
    val white: Color,
    val light: Color,
    val green: Color,

    val buttonStartColor: Color,
    val buttonCenterColor: Color,
    val buttonEndColor: Color,
    val buttonStartColorPressed: Color,
    val buttonCenterColorPressed: Color,
    val buttonEndColorPressed: Color,

    )


val LocalColors = staticCompositionLocalOf {
    YESColors(
        tint = Color.Unspecified,
        brandedColor = Color.Unspecified,
        brandedLight = Color.Unspecified,
        brandedDark = Color.Unspecified,
        textGray = Color.Unspecified,
        transparent = Color.Unspecified,
        black = Color.Unspecified,
        white = Color.Unspecified,
        light = Color.Unspecified,
        green = Color.Unspecified,
        buttonStartColor = Color.Unspecified,
        buttonCenterColor = Color.Unspecified,
        buttonEndColor = Color.Unspecified,
        buttonStartColorPressed = Color.Unspecified,
        buttonCenterColorPressed = Color.Unspecified,
        buttonEndColorPressed = Color.Unspecified,
    )
}
val LocalTypography = staticCompositionLocalOf {
    YESTypography(
        small = TextStyle.Default,
        regular = TextStyle.Default,
        large = TextStyle.Default,
        colossus = TextStyle.Default
    )
}
val LocalSpacing =  staticCompositionLocalOf{ YESSpacing() }
val themeColors = YESColors(
    tint = tint,
    brandedColor = brandedColor,
    brandedLight = brandedLight,
    brandedDark = brandedDark,
    textGray = textGray,
    transparent = transparent,
    black = black,
    white = white,
    light = light,
    green = green,
    buttonStartColor = buttonStartColor,
    buttonCenterColor = buttonCenterColor,
    buttonEndColor = buttonEndColor,
    buttonStartColorPressed = buttonStartColorPressed,
    buttonCenterColorPressed = buttonCenterColorPressed,
    buttonEndColorPressed = buttonEndColorPressed,
)
val themeTypography = YESTypography(
    small = small,
    regular = regular,
    large = large,
    colossus = colossus
)
val themeSpacing=YESSpacing()
@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = if (!useDarkTheme) {
        themeColors
    } else {
        themeColors
    }

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides themeTypography,
        LocalSpacing provides themeSpacing,
        content = content
    )
}

object YESTheme {
    val colors: YESColors
        @Composable
        get() = LocalColors.current
    val typography: YESTypography
        @Composable
        get() = LocalTypography.current

}