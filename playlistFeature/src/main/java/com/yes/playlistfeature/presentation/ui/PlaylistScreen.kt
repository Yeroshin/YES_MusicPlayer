package com.yes.playlistfeature.presentation.ui

import androidx.compose.material3.rememberDismissState
import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FractionalThreshold

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismiss


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yes.core.presentation.BaseViewModel
import com.yes.playlistfeature.di.component.PlaylistComponent
import com.yes.playlistfeature.presentation.contract.PlaylistContract
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yes.playlistfeature.presentation.model.TrackUI
import com.yes.playlistfeature.presentation.ui.theme.AppTheme
import com.yes.playlistfeature.presentation.ui.theme.YESTheme

interface DependencyResolver {
    fun getPlaylistComponent(): PlaylistComponent
}

fun deleteItem(
    item: TrackUI,
    viewModel: BaseViewModel<PlaylistContract.Event, PlaylistContract.State, PlaylistContract.Effect>
) {
    viewModel.setEvent(
        PlaylistContract.Event.OnDeleteTrack(
            item
        )
    )
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

    var items: List<TrackUI> by remember { mutableStateOf(listOf()) }
    PlaylistColumn(items,
        onDelete = { item ->
            viewModel.setEvent(
                PlaylistContract.Event.OnDeleteTrack(
                    item
                )
            )
        })
    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value.playlistState) {
        is PlaylistContract.PlaylistState.Success -> {
            (uiState.value.playlistState as PlaylistContract.PlaylistState.Success).tracks?.let {
                items = it
            }
        }

        is PlaylistContract.PlaylistState.Loading -> {

        }

        is PlaylistContract.PlaylistState.Idle -> {

        }
    }
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

    /* AppTheme {
         PlaylistColumn(data)
     }*/
}

//@Preview( device = Devices.PIXEL_4)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlaylistColumn(items: List<TrackUI>, onDelete: (index: TrackUI) -> Unit) {
    val test = remember {
        mutableStateListOf(
            "Subscribe",
            "Like",
            "Share",
            "Comment",
            "MkrDeveloper"
        )
    }
    LazyColumn(
        //  state = rememberLazyListState(),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> item.hashCode() },
            itemContent = { index, data ->
                val dismissState = rememberDismissState()

                // check if the user swiped
                if (dismissState.isDismissed(direction = DismissDirection.StartToEnd)) {
                    onDelete(data)
                    // test.remove(data)
                }
                /////////////////////////
                /* val currentItem by rememberUpdatedState(data)
                 val dismissState = rememberDismissState(
                     confirmValueChange = {
                             onDelete(data)
                             // test.remove(currentItem )
                             true
                     }
                 )*/
                //////////////////////
                /*  var unread by remember { mutableStateOf(false) }
                  val dismissState = rememberDismissState(
                      confirmStateChange = {
                          if (it == DismissedToEnd) unread = !unread
                          it != DismissedToEnd
                      }
                  )*/

                SwipeToDismiss(
                    // modifier = Modifier.animateItemPlacement(),
                    state = dismissState,
                    directions = setOf(
                        DismissDirection.StartToEnd
                    ),

                    background = {
                        // this background is visible when we swipe.
                        // it contains the icon

                        // background color
                        val backgroundColor by animateColorAsState(
                            when (dismissState.targetValue) {
                                DismissValue.DismissedToEnd -> Color.Red.copy(alpha = 0.8f)
                                else -> Color.White
                            }, label = ""
                        )

                        // icon size
                        val iconScale by animateFloatAsState(
                            targetValue = 1.3f,
                            label = ""
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color = backgroundColor),
                            //   .padding(end = 16.dp), // inner padding
                            contentAlignment = Alignment.CenterStart // place the icon at the end (left)
                        ) {
                            Icon(
                                modifier = Modifier.scale(iconScale),
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "Delete",
                                tint = Color.White
                            )
                        }
                    },
                    dismissContent = {
                        // testItem(5)
                        // list item
                        ItemTrack(index, data)
                    }
                )
            }
        )

    }
}

@Composable
fun testItem(index: Int) {
    Text(
        modifier = YESTheme.spacing.neumorphicSurface,
        style = YESTheme.typography.colossus,
        text = "index.toString()",
    )
}

@Composable
fun ItemTrack(index: Int, data: TrackUI) {
    Surface(
        modifier = YESTheme.spacing.neumorphicSurface,
        color = YESTheme.colors.brandedColor
    ) {
        Row(
            modifier = YESTheme.spacing.twoLineListsContainer,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = YESTheme.spacing.twoLineListsLeadingAvatar,
                style = YESTheme.typography.colossus,
                text = index.toString(),
            )
            Column(
                modifier = Modifier.weight(1F, true),
            ) {
                Text(
                    style = YESTheme.typography.regularBold,
                    text = data.title
                )
                Text(
                    style = YESTheme.typography.regular,
                    text = data.info
                )
            }
            Text(
                modifier = YESTheme.spacing.twoLineListsTrailingSupportText,
                style = YESTheme.typography.regular,
                text = data.duration
            )
        }
    }
}


