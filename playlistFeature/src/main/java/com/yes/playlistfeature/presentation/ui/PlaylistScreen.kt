package com.yes.playlistfeature.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.yes.core.presentation.BaseViewModel
import com.yes.playlistfeature.di.component.PlaylistComponent
import com.yes.playlistfeature.presentation.contract.PlaylistContract
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yes.playlistfeature.presentation.model.TrackUI

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
@Preview( device = Devices.PIXEL_4)
@Composable
fun PlaylistColumnPreview(){
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
    PlaylistColumn(data)
}
@Composable
fun PlaylistColumn(uiState:List<TrackUI>){

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(uiState) {index, data ->
            ItemTrack(index,data)
        }
    }
}
@Composable
fun ItemTrack(index:Int,data:TrackUI, modifier: Modifier = Modifier) {

    Row(modifier.fillMaxWidth()) {
        Text(
color=
            text = index.toString())
        Column {
            Text(

                text = data.title)
            Text(text = data.info)
        }
    }
}