package com.yes.player.presentation.ui

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.times

@Preview
@Composable
fun EqualizerViewTest(/*value1: Int, value2: Int, value3: Int, value4: Int, value5: Int*/) {
    val value1 = 80
    val value2 = 40
    val value3 =25
    val value4 = 20
    val value5 = 2
    val columnCount = 5
    val columnSpacing = 4.dp

    val maxColumnHeight = listOf(value1, value2, value3, value4, value5).maxOrNull() ?: 0

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val squareSize = (screenWidth / columnCount )

    val columnHeights = listOf(value1, value2, value3, value4, value5)
        .map { (it / 100f) * maxColumnHeight }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        repeat(maxColumnHeight) { heightIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(columnSpacing)
            ) {
                repeat(columnCount) { columnIndex ->
                    val columnHeight = columnHeights[columnIndex]
                    val squareColor = if (heightIndex < columnHeight) Color.White else Color.Black
                    Box(
                        modifier = Modifier
                            .size(squareSize)
                            .offset(y = -(heightIndex * (squareSize / 2)))
                            .background(squareColor)
                    )
                }
            }
        }
    }
}