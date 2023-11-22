package com.yes.player.presentation.ui.theme


import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun EqualizerViewPreview() {

    val values = doubleArrayOf(
        10.0,
        20.0,
        30.0,
        40.0,
        50.0,
        60.0,
        70.0,
        80.0,
        90.0,
        20.0
    )
    //  EqualizerView(values)
}/*
@Composable
fun AdaptiveGrid(values: DoubleArray, numColumns: Int) {
    val cellSize = (MaterialTheme.typography.body1.fontSize * 1.5).dp
    val numRows = (values.size + numColumns - 1) / numColumns
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items((0 until numRows)) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(cellSize / 8)
            ) {
                for (colIndex in 0 until numColumns) {
                    val valueIndex = rowIndex * numColumns + colIndex
                    if (valueIndex < values.size) {
                        val value = values[valueIndex]
                        val cellColor = if (value > 0.0) Color.Black else Color.White
                        Spacer(
                            modifier = Modifier
                                .size(cellSize)
                                .background(color = cellColor)
                        )
                    } else {
                        Spacer(modifier = Modifier.size(cellSize)) // Пустая ячейка
                    }
                }
            }
        }
    }
}*/

@Composable
fun AdaptiveGrid(values: DoubleArray) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(values.toList()) { value ->
            val cellColor = if (value > 0.0) Color.Black else Color.White
            val cellSize = 50.dp // Задаем размер ячейки
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cellSize)
                    .padding(vertical = 4.dp)
                    .background(color = cellColor)
            )
        }
    }
}

@Composable
fun AdaptiveGrid(values: List<Double>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(values) { value ->
            val cellColor = if (value > 0.0) Color.Black else Color.White
            val cellSize = 50.dp // Задаем размер ячейки
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cellSize)
                    .padding(vertical = 4.dp)
                    .background(color = cellColor)
            )
        }
    }
}

/*
@Composable
fun EqualizerView(values: DoubleArray) {
    val maxValue = 1.0

    SubcomposeLayout { constraints ->
        val placeables = values.map { item ->
            val countBlack = ((item * constraints.maxHeight) / maxValue).toInt()
            val countWhite = constraints.maxHeight - countBlack

            layout(constraints.maxWidth, constraints.maxHeight) {
                var yPosition = 0
                repeat(countWhite) {
                    val placeable = subcompose {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.White)
                        )
                    }.first().measure(constraints)
                    placeable.placeRelative(0, yPosition)
                    yPosition += placeable.height
                }
                repeat(countBlack) {
                    val placeable = subcompose {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Black)
                        )
                    }.first().measure(constraints)
                    placeable.placeRelative(0, yPosition)
                    yPosition += placeable.height
                }
            }
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var xPosition = 0
            placeables.forEach { placeable ->
                placeable.measure(constraints)
                placeable.placeRelative(xPosition, 0)
                xPosition += placeable.width
            }
        }
    }
}*/
@Composable
fun EqualizerVie(values: DoubleArray) {
    val localDensity = LocalDensity.current

    val columnCount = values.size
    val maxValue = 1

    var screenWidth by remember { mutableStateOf(400.dp) }
    var screenHeight by remember { mutableStateOf(200.dp) }

    var squareSize by remember { mutableStateOf(16.dp) }
    var columnSpacing by remember { mutableStateOf(2.dp) }
    var maxHeightCount by remember { mutableIntStateOf(25) }


//////////////

    ////////////////////////////
    Box(
        modifier = Modifier

            .background(Color.LightGray)
            .onGloballyPositioned { coordinates ->
                screenWidth = with(localDensity) { coordinates.size.width.toDp() }
                screenHeight = with(localDensity) { coordinates.size.height.toDp() }
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
            values.forEachIndexed { index, value ->
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    val countBlack = ((value * maxHeightCount) / maxValue).toInt()
                    val countWhite = maxHeightCount - countBlack
                    repeat(countWhite) {
                        Spacer(modifier = Modifier.height(columnSpacing))
                        Box(
                            modifier = Modifier
                                .size(squareSize)
                                .background(Color.Gray)
                        )
                    }
                    repeat(countBlack) {
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

@Composable
fun DimensionSubcomposeLayout(
    modifier: Modifier = Modifier,
    placeMainContent: Boolean = true,
    mainContent: @Composable () -> Unit,
    dependentContent: @Composable (Size) -> Unit
) {
    SubcomposeLayout(
        modifier = modifier
    ) { constraints: Constraints ->

        // Subcompose(compose only a section) main content and get Placeable
        val mainPlaceables: List<Placeable> = subcompose(SlotsEnum.Main, mainContent)
            .map {
                it.measure(constraints.copy(minWidth = 0, minHeight = 0))
            }

        // Get max width and height of main component
        var maxWidth = 0
        var maxHeight = 0

        mainPlaceables.forEach { placeable: Placeable ->
            maxWidth += placeable.width
            maxHeight = placeable.height
        }

        val dependentPlaceables: List<Placeable> = subcompose(SlotsEnum.Dependent) {
            dependentContent(Size(maxWidth.toFloat(), maxHeight.toFloat()))
        }
            .map { measurable: Measurable ->
                measurable.measure(constraints)
            }


        layout(maxWidth, maxHeight) {

            if (placeMainContent) {
                mainPlaceables.forEach { placeable: Placeable ->
                    placeable.placeRelative(0, 0)
                }
            }

            dependentPlaceables.forEach { placeable: Placeable ->
                placeable.placeRelative(0, 0)
            }
        }
    }
}

@Composable
fun content() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.Red)
    )
}

enum class SlotsEnum { Main, Dependent }

@Composable
fun DimensionLayout(
    values: FloatArray,
    mainContent: @Composable () -> Unit,
    dependentContent: @Composable (IntSize, FloatArray) -> Unit
) {
    SubcomposeLayout { constraints ->

        val mainPlaceables = subcompose(SlotsEnum.Main, mainContent).map {
            it.measure(constraints)
        }
        val maxSize = mainPlaceables.fold(IntSize.Zero) { currentMax, placeable ->
            IntSize(
                width = maxOf(currentMax.width, placeable.width),
                height = maxOf(currentMax.height, placeable.height)
            )
        }
        layout(maxSize.width, maxSize.height) {
            //    mainPlaceables.forEach { it.placeRelative(0, 0) }
            subcompose(SlotsEnum.Dependent) {
                dependentContent(maxSize, values)
            }.forEach {
                it.measure(constraints).placeRelative(0, 0)
            }
        }
    }
}

@Composable
fun MainContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    )
}

@Preview( device = Devices.PIXEL_4)
@Composable
fun PreviewContent() {
    val frequencies = floatArrayOf(0.2F, 0.4F, 0.7F, 0.5F, 0.9F, 1F, 0.8F)
    DimensionLayout(
        values = frequencies,
        mainContent = { MainContent() },
        dependentContent = { size, values ->
            DependentContent(
                size,
                values
            )
        } // Передаем DependentContent в качестве dependentContent
    )

}

@Composable
fun DependentContent(maxSize: IntSize, values: FloatArray) {
    val animatedValues = remember { mutableStateOf(values) }

    animatedValues.value.forEachIndexed { index, value ->
        animatedValues.value[index] = animateFloatAsState(
            targetValue = values[index],
            animationSpec = tween(
                durationMillis = 300
            ), label = ""
        ).value
    }


    val columnCount = values.size
    val maxValue = 1
    val screenWidth = maxSize.width.pxToDp()
    val screenHeight = maxSize.height.pxToDp()
    var squareSize = (screenWidth / columnCount)
    val columnSpacing = squareSize / 8
    squareSize -= columnSpacing
    val maxHeightCount = (screenHeight / (squareSize + columnSpacing)).toInt()
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(columnSpacing)
    ) {
        animatedValues.value.forEach { value ->
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                val countBlack = ((value * maxHeightCount) / maxValue).toInt()
                val countWhite = maxHeightCount - countBlack
                repeat(countWhite) {
                    Spacer(modifier = Modifier.height(columnSpacing))
                    Box(
                        modifier = Modifier
                            .size(squareSize)
                            .background(Color.Transparent)
                    )
                }
                repeat(countBlack) {
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

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }