package com.yes.trackdialogfeature.data.mapper

import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.PlayListDAOFixtures
import com.yes.core.data.entity.PlayListDataBaseTrackEntity
import com.yes.core.data.entity.MediaDataStoreEntity
import io.mockk.MockKAnnotations
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class IYESDataBaseEntityDaoMapperTest {
    private lateinit var cut:PlayListDaoMapper
    @BeforeEach
    fun setUp()  {
        // @MockK
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        cut = PlayListDaoMapper()
    }
    @ParameterizedTest
    @MethodSource("mapToItemData")
    fun map(
        expected: PlayListDataBaseTrackEntity,
        track: MediaDataStoreEntity
    ) {
        val actual=cut.map(track)
        assert(actual == expected)
    }

    companion object {
        @JvmStatic
        fun mapToItemData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    PlayListDAOFixtures.getTracks()[0],
                    MediaDataStoreFixtures.getTracksAudio()[0]
                )
            )
        }
    }
}