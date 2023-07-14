package com.yes.trackdialogfeature

import com.yes.trackdialogfeature.presentation.model.MenuUi

fun trackDialog(func: TrackDialogRobot.() -> Unit) = TrackDialogRobot().apply { func() }
class TrackDialogRobot : BaseTestRobot() {
    fun matchTitleText(title: String) = matchText(view(com.yes.coreui.R.id.dialogTitle), title)
    fun matchTitleHasNoText() = hasNoText(view(com.yes.coreui.R.id.dialogTitle))
    fun matchProgressBarDisplayed() = isDisplayed(view(com.yes.coreui.R.id.progressBar))
    fun matchProgressBarIsNotDisplayed() = isNotDisplayed(view(com.yes.coreui.R.id.progressBar))
    fun matchDisableViewDisplayed() = isDisplayed(view(com.yes.coreui.R.id.disableView))
    fun matchDisableViewIsNotDisplayed() = isNotDisplayed(view(com.yes.coreui.R.id.disableView))
    fun matchTrackDialogItemAtPosition(position: Int, item: MenuUi.ItemUi) =
        matchRecyclerViewItemDescendantTextAtPosition(
            view(com.yes.coreui.R.id.recyclerView),
            position,
            R.id.item_title,
            item.name
        )
}