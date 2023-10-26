package com.yes.trackdialogfeature

import com.yes.core.domain.repository.IPlayListDao
import com.yes.trackdialogfeature.domain.repository.SettingsRepository
import com.yes.trackdialogfeature.presentation.PlayListDataBaseTrackEntityDialogTest
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.model.MenuUi
import junit.framework.TestCase.assertEquals

fun trackDialog(func: TrackDialogRobot.() -> Unit) = TrackDialogRobot().apply { func() }
class TrackDialogRobot() : BaseTestRobot() {

    fun matchTitleText(title: String) = matchText(view(com.yes.coreui.R.id.dialogTitle), title)
    fun matchTitleHasNoText() = hasNoText(view(com.yes.coreui.R.id.dialogTitle))
    fun matchProgressBarDisplayed(){
        matchLocalSourceListBlocked()
        isDisplayed(view(com.yes.coreui.R.id.progressBar))
    }
    fun matchProgressBarIsNotDisplayed() {
        isNotDisplayed(view(com.yes.coreui.R.id.progressBar))
        matchLocalSourceListUnlocked()
    }
    fun matchLocalSourceListBlocked(){
        isDisplayed(view(com.yes.coreui.R.id.disableView))
    }
    fun matchLocalSourceListUnlocked(){
        isNotDisplayed(view(com.yes.coreui.R.id.disableView))
    }
    fun matchNetworkPathDisabled(){
        isEnabled(
            view(R.id.networkPath)
        )
    }
    fun matchNetworkPathEnabled(){
        isEnabled(
            view(R.id.networkPath)
        )
    }

    fun matchTrackDialogItemAtPosition(position: Int, item: MenuUi.ItemUi) =
        matchRecyclerViewItemDescendantTextAtPosition(
            view(com.yes.coreui.R.id.recyclerView),
            position,
            R.id.item_title,
            item.name
        )
    fun notMatchTrackDialogItemAtPosition(position: Int, item: MenuUi.ItemUi) =
        notMatchRecyclerViewItemDescendantTextAtPosition(
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
    fun clickNetworkSourceButton()=clickView(
        view(R.id.network_btn)
    )
    fun enterAddressNetworkSource(text:String)=typeText(
        view(R.id.networkPath),
        text
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
    fun setEventToViewModel(state: TrackDialogContract.TrackDialogState,
                            viewModel: PlayListDataBaseTrackEntityDialogTest.TestViewModel
    ){
        viewModel.pushEvent (
           state
        )
    }
    fun matchSelectedTracksSavedToPlaylist(
        expectedTracks:List<MenuUi.ItemUi>,
        settingsRepository: SettingsRepository,
        playListRepository: IPlayListDao
    ){
        val playListName = settingsRepository.getCurrentPlayListId()
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
    fun matchNetworkSourceSavedToPlaylist(
        expectedTracks:MenuUi.ItemUi,
        settingsRepository: SettingsRepository,
        playListRepository: IPlayListDao
    ){
        val playListName = settingsRepository.getCurrentPlayListId()
        val actualTracks=readTracksFromPlaylistDB(
            playListName,
            playListRepository
        ).map {
            it.uri
        }
        val expected=expectedTracks


        assertEquals(expectedTracks.name, actualTracks[0])
    }

}