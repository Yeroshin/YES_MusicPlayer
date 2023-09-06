package com.yes.trackdialogfeature

import com.yes.trackdialogfeature.data.mapper.MediaRepositoryMapper
import com.yes.trackdialogfeature.domain.entity.Track
import com.yes.trackdialogfeature.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.ISettingsRepository
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import com.yes.trackdialogfeature.presentation.model.MenuUi
import junit.framework.TestCase.assertEquals

fun trackDialog(func: TrackDialogRobot.() -> Unit) = TrackDialogRobot().apply { func() }
class TrackDialogRobot() : BaseTestRobot() {

    fun matchTitleText(title: String) = matchText(view(com.yes.coreui.R.id.dialogTitle), title)
    fun matchTitleHasNoText() = hasNoText(view(com.yes.coreui.R.id.dialogTitle))
    fun matchProgressBarDisplayed(){
        isDisplayed(view(com.yes.coreui.R.id.progressBar))
        isDisplayed(view(com.yes.coreui.R.id.disableView))
    }
    fun matchProgressBarIsNotDisplayed() {
        isNotDisplayed(view(com.yes.coreui.R.id.progressBar))
        isNotDisplayed(view(com.yes.coreui.R.id.disableView))
    }

    fun matchTrackDialogItemAtPosition(position: Int, item: MenuUi.ItemUi) =
        matchRecyclerViewItemDescendantTextAtPosition(
            view(com.yes.coreui.R.id.recyclerView),
            position,
            R.id.item_title,
            item.name
        )
    fun clickItemCheckBoxInMediaList(
        position:Int
    )=clickRecyclerViewItemViewChildView(
        view(com.yes.coreui.R.id.recyclerView),
        position,
        R.id.checkBox
    )
    fun clickOkButton()=clickView(
        view(com.yes.coreui.R.id.ok_btn)
    )
    fun clickItemInMediaList(
        position:Int
    )=clickRecyclerViewItemView(
        view(com.yes.coreui.R.id.recyclerView),
        position,
    )
    fun matchSelectedArtistTracksSavedToPlaylist(
        expectedTracks:List<MenuUi.ItemUi>,
        settingsRepository: ISettingsRepository,
        playListRepository: IPlayListDao
    ){
        val playListName = settingsRepository.getCurrentPlayListName()
        val actualTracks=readTracksFromPlaylistDB(
            playListName,
            playListRepository
        ).map {
            it.title
        }
        val expected=expectedTracks.map {
            it.name
        }

        assertEquals(expected, actualTracks)
    }
}