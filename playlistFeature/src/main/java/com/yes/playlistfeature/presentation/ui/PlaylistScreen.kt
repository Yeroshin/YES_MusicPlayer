package com.yes.playlistfeature.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yes.core.presentation.BaseViewModel
import com.yes.playlistfeature.di.component.PlaylistComponent
import com.yes.playlistfeature.presentation.contract.PlaylistContract
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yes.playlistfeature.presentation.model.TrackUI
import com.yes.playlistfeature.presentation.ui.theme.AppTheme
import com.yes.playlistfeature.presentation.ui.theme.CustomTheme

interface DependencyResolver {
    fun getPlaylistComponent(): PlaylistComponent
}

@Composable
fun PlaylistScreen() {
    val context = LocalContext.current.applicationContext as DependencyResolver
    val component by remember {
        mutableStateOf(context.getPlaylistComponent())
    }
    val dependency by remember {
        mutableStateOf(component.getDependency())
    }

    val viewModel: BaseViewModel<PlaylistContract.Event,
            PlaylistContract.State,
            PlaylistContract.Effect> = viewModel(factory = dependency.factory)
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value.playlistState) {
        is PlaylistContract.PlaylistState.Success -> {
            (uiState.value.playlistState as PlaylistContract.PlaylistState.Success).tracks?.let {
                PlaylistColumn(it)
            }
        }

        is PlaylistContract.PlaylistState.Loading -> {

        }

        is PlaylistContract.PlaylistState.Idle -> {

        }

    }

    /*  val data = listOf(
         "a","b","c"
      )*/

}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
/*@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)*/
@Composable
fun ReplyAppPreview() {
    val data = listOf(
        TrackUI(
            1,
            "title",
            "info",
            "3:00",
        ),
        TrackUI(
            2,
            "title2",
            "info2",
            "5:00",
        )
    )

    AppTheme {
        PlaylistColumn(data)
    }
}

//@Preview( device = Devices.PIXEL_4)


@Composable
fun PlaylistColumn(uiState: List<TrackUI>) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(uiState) { index, data ->
            ItemTrack(index, data)
        }
    }
}

@Composable
fun ItemTrack(index: Int, data: TrackUI, modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color.Black
                    )
                )
            )
            .padding( 0.dp,1.dp,0.dp,2.dp),

        color= MaterialTheme.colorScheme.primary


    ) {
        Row(modifier.fillMaxWidth()) {

            Text(

                text = index.toString()
            )
            Column {
                Row(modifier.fillMaxWidth()) {
                    Text(
                        color = CustomTheme.colors.primaryText,
                        text = data.title
                    )
                    Text(
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = data.duration
                    )
                }

                Text(text = data.info)
            }
        }

    }
}


