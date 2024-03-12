package com.yes.alarmclockfeature.data.mapper

import android.widget.ToggleButton
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.yes.alarmclockfeature.domain.model.Alarm
import com.yes.alarmclockfeature.domain.model.Track
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import com.yes.core.data.entity.AlarmDataBaseEntity
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import java.util.Calendar

class Mapper {
    fun map(track: PlayListDataBaseTrackEntity): Track {
        return Track(
            track.id ?: 0,
            track.playlistId,
            track.artist,
            track.title,
            track.uri,
            track.duration,
            track.album,
            track.size,
            track.position
        )
    }

    fun map(track: Track): PlayListDataBaseTrackEntity {
        return PlayListDataBaseTrackEntity(
            track.id,
            track.playlistId,
            track.artist,
            track.title,
            track.uri,
            track.duration,
            track.album,
            track.size,
            track.position
        )
    }
    fun mapToMediaItem(item: Track): MediaItem {
        val mediaMetadata = MediaMetadata.Builder()
            .setAlbumTitle(item.album)
            .setArtist(item.artist)
            .setTitle(item.title)
            .build()
        return MediaItem.Builder()
            .setUri(item.uri)
            .setMediaMetadata(mediaMetadata)
            .build()
    }
    fun map(alarm: Alarm): AlarmDataBaseEntity {


        return AlarmDataBaseEntity(
            alarm.id,
            alarm.timeHour,
            alarm.timeMinute,
            alarm.daysOfWeek.contains(Calendar.SUNDAY),
            alarm.daysOfWeek.contains(Calendar.MONDAY),
            alarm.daysOfWeek.contains(Calendar.TUESDAY),
            alarm.daysOfWeek.contains(Calendar.WEDNESDAY),
            alarm.daysOfWeek.contains(Calendar.THURSDAY),
            alarm.daysOfWeek.contains(Calendar.FRIDAY),
            alarm.daysOfWeek.contains(Calendar.SATURDAY),
            alarm.enabled,
        )
    }
    fun map(alarm:AlarmDataBaseEntity ): Alarm {
        val days: List<Boolean> = listOf(
            alarm.sun,
            alarm.mon,
            alarm.tue,
            alarm.wed,
            alarm.thu,
            alarm.fri,
            alarm.sat
        )
        val selectedDays: MutableSet<Int> = mutableSetOf()
        for ((index, day) in days.withIndex()) {
            if (day) {
                when (index) {
                    0 -> selectedDays.add(Calendar.SUNDAY)
                    1 -> selectedDays.add(Calendar.MONDAY)
                    2 -> selectedDays.add(Calendar.TUESDAY)
                    3 -> selectedDays.add(Calendar.WEDNESDAY)
                    4 -> selectedDays.add(Calendar.THURSDAY)
                    5 -> selectedDays.add(Calendar.FRIDAY)
                    6 -> selectedDays.add(Calendar.SATURDAY)
                }
            }
        }
        return Alarm(
            alarm.id?:0,
            alarm.hour,
            alarm.minute,
            selectedDays,
            alarm.enabled,
        )
    }
}