package com.yes.player.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment

@Preview
@Composable
fun EqualizerView() {
    val value1 = 100
    val value2 = 40
    val value3 = 25
    val value4 = 20
    val value5 = 60
    val values = listOf(
        value1,
        value2,
        value3,
        value4,
        value5,
    )
    val columnCount = values.size

    val maxValue = 100

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var squareSize = (screenWidth / columnCount)
    val columnSpacing = squareSize / 8
    squareSize -= columnSpacing
    val maxHeightCount = screenHeight / (squareSize + columnSpacing)



    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(columnSpacing)
    ) {
        repeat(columnCount) { columnIndex ->
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                val count = ((values[ columnIndex] * maxHeightCount) / maxValue).toInt()
                repeat(count) { heightIndex ->
                    Spacer(modifier = Modifier.height(columnSpacing))
                    Box(
                        modifier = Modifier
                            .size(squareSize)
                            .background(Color.White)
                    )
                }
            }
        }

    }

}