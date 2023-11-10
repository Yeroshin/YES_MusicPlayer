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
import androidx.compose.ui.layout.onGloballyPositioned


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

    /*val value1 = 80
    val value2 = 40
    val value3 =25
    val value4 = 20
    val value5 = 2
    val values = listOf(
        value1,
        value2,
        value3,
        value4,
        value5,
    )*/
    val columnCount = values.size

    val maxValue = 500

/*    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
*/
    var screenWidth = 472.dp
    var screenHeight = 206.dp

    var squareSize = 16.dp
    var columnSpacing = 2.dp

    var maxHeightCount = 25

//////////////

    ////////////////////////////
    Box(
        modifier = Modifier

            .background(Color.Blue)
            .onGloballyPositioned { coordinates ->
                 screenWidth = coordinates.size.height.dp
                 screenHeight = coordinates.size.width.dp
                ///////////////
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


}