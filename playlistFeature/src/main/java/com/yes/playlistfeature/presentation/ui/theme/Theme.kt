package com.yes.playlistfeature.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class YESSpacing(
    val neumorphicSurface: Modifier = Modifier
        .fillMaxWidth()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    light,
                    shadow
                )
            )
        )
        .padding(0.dp, 2.dp, 0.dp, 2.dp),
    val twoLineListsContainer: Modifier = Modifier
        .fillMaxWidth()
        .padding(
            start = 0.dp,
            top = 8.dp,
            end = 0.dp,
            bottom = 8.dp
        ),
    val twoLineListsLeadingAvatar: Modifier = Modifier
        .padding(16.dp, 0.dp, 16.dp, 0.dp),
    val twoLineListsTrailingSupportText: Modifier = Modifier
        .padding(16.dp, 0.dp, 16.dp, 0.dp),
)

data class YESTypography(
    val small: TextStyle,
    val regular: TextStyle,
    val regularBold: TextStyle,
    val large: TextStyle,
    val colossus: TextStyle
)

data class YESColors(
    val tint: Color,
    val brandedColor: Color,
    val brandedLight: Color,
    val brandedDark: Color,
    val text: Color,
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
        text = Color.Unspecified,
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
        regularBold = TextStyle.Default,
        large = TextStyle.Default,
        colossus = TextStyle.Default
    )
}
val LocalSpacing = staticCompositionLocalOf { YESSpacing() }
val themeColors = YESColors(
    tint = tint,
    brandedColor = brandedColor,
    brandedLight = brandedLight,
    brandedDark = brandedDark,
    text = textGray,
    transparent = transparent,
    black = shadow,
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
    regularBold = regularBold,
    large = large,
    colossus = colossus
)
val themeSpacing = YESSpacing()

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
    val spacing: YESSpacing
        @Composable
        get() = LocalSpacing.current

}