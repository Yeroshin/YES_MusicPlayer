package com.yes.trackdialogfeature.presentation.model

import com.yes.core.presentation.UiEvent
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract


data class MenuUi(
    val title: String,
    val items: List<MediaItem>
) {
    data class MediaItem(
        val id:Int,
        val name:String,
        val iconType:Int=1,
        val onClick: ((TrackDialogContract.Event) -> Unit)

    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as MediaItem

            if (id != other.id) return false
            if (name != other.name) return false
            if (iconType != other.iconType) return false
            if (onClick != other.onClick) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + name.hashCode()
            result = 31 * result + iconType
            result = 31 * result + onClick.hashCode()
            return result
        }
    }
}