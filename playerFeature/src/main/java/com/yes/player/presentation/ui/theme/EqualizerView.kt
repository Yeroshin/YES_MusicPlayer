package com.yes.player.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.times
@Preview
@Composable
fun EqualizerView() {
    val value1 =100
    val value2 = 40
    val value3 =25
    val value4 = 20
    val value5 = 2
    val columnCount = 5

    val maxValue=100

    val maxColumnHeight = listOf(value1, value2, value3, value4, value5).maxOrNull() ?: 0

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val squareSize = (screenWidth / columnCount )
    val columnSpacing = squareSize/8
    val maxCount=screenHeight/squareSize
    val count =((value1*maxCount)/maxValue).toInt()

    val columnHeights = listOf(value1, value2, value3, value4, value5)
        .map { (it / 100f) * maxColumnHeight }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(columnSpacing)
    ) {
        repeat(columnCount) { columnIndex ->
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                repeat(count) { heightIndex ->
                    Box(
                        modifier = Modifier
                            .padding(columnSpacing)
                            .size(squareSize-columnSpacing*2 )
                            .background(Color.White)
                    )
                }
            }
        }

    }

}