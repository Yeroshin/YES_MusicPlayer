package com.yes.trackdialogfeature

fun trackDialog(func: TrackDialogRobot.() -> Unit) = TrackDialogRobot().apply { func() }
class TrackDialogRobot: BaseTestRobot() {

}