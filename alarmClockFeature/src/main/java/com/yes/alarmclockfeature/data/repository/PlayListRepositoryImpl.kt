package com.yes.alarmclockfeature.data.repository



import com.yes.alarmclockfeature.data.mapper.Mapper
import com.yes.alarmclockfeature.domain.model.Track
import com.yes.core.domain.repository.IPlayListDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayListRepositoryImpl(
    private val mapper: Mapper,
    private val playListDao: IPlayListDao,
) {
    fun subscribeTracksWithPlaylistId(playlistId: Long): Flow<List<Track>> {
        return playListDao.subscribeTracksWithPlaylistId(playlistId).map {
            it.map { item ->
                mapper.map(item)
            }
        }
    }

    fun deleteTrack(track: Track) {
        playListDao.deleteTrack(
            mapper.map(track)
        )
    }

    fun updateTrack(track: Track) {
       val t= playListDao.updateTrack(
            mapper.map(track)
        )
    }
}