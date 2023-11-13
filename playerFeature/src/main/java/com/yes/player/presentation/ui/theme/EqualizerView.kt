package com.yes.player.presentation.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
@Preview
@Composable
fun EqualizerViewPreview() {
    val value1 = 10F
    val value2 = 20F
    val value3 =30F
    val value4 = 40F
    val value5 = 50F
    val values = listOf(
        10F,
        20F,
        30F,
        40F,
        50F,
        60F,
        70F,
        80F,
        90F,
        100F
    )
    EqualizerView(values)
}

@Composable
fun EqualizerView(values:List<Float> ) {
  /*  val map= mapOf(
        20 to 20F,
        30 to 80F,
        40 to 50F,
        50 to 60F,
        60 to 70F,
        70 to 30F,
    )*/


    val columnCount = values.size
    val maxValue = 100F

    var screenWidth by remember { mutableStateOf(400.dp) }
    var screenHeight by remember { mutableStateOf(500.dp) }

    var squareSize by remember { mutableStateOf(16.dp) }
    var columnSpacing by remember { mutableStateOf(2.dp) }
    var maxHeightCount by remember { mutableIntStateOf(25) }


//////////////

    ////////////////////////////
    Box(
        modifier = Modifier

            .background(Color.LightGray)
            .onGloballyPositioned { coordinates ->
                screenWidth = coordinates.size.width.dp
                screenHeight = coordinates.size.height.dp
                squareSize = (screenWidth / columnCount)
                columnSpacing = squareSize / 8
                squareSize -= columnSpacing
                maxHeightCount = (screenHeight / (squareSize + columnSpacing)).toInt()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(columnSpacing)
        ) {
            values.forEachIndexed { _, value ->
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    val count = ((value * maxHeightCount) / maxValue).toInt()
                    repeat(count) {
                        Spacer(modifier = Modifier.height(columnSpacing))
                        Box(
                            modifier = Modifier
                                .size(squareSize)
                                .background(Color.Black)
                        )
                    }
                }
            }
        }
    }


}