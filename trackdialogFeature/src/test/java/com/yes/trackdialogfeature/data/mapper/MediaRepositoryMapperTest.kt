package com.yes.trackdialogfeature.data.mapper

import com.yes.core.Fixture
import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import org.junit.Assert.*

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MediaRepositoryMapperTest {
    private val cut = MediaRepositoryMapper()

    @ParameterizedTest
    @MethodSource("mapToItemData")
    fun map(
        dataStoreEntityFixture: Fixture<Map<String, Any>>,
        expectedDomainFixture: Fixture<Item>
    ) {
        val actual = cut.map(
            dataStoreEntityFixture.data["id"] as Int,
            dataStoreEntityFixture.data["type"] as String?,
            dataStoreEntityFixture.data["entity"] as MediaDataStoreEntity
        )
        assert(actual == expectedDomainFixture.data)
    }

    companion object {
        @JvmStatic
        fun mapToItemData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        mapOf(
                            "id" to MenuDataStoreFixtures.getArtistsMenu().id,
                            "type" to MenuDataStoreFixtures.getArtistsMenu().type,
                            "entity" to MediaDataStoreFixtures.getArtists()[0]
                        )
                    ),
                    Fixture(
                        Item(
                            MenuDataStoreFixtures.getArtistsMenu().id,
                            MediaDataStoreFixtures.getArtists()[0].title,
                            MenuDataStoreFixtures.getArtistsMenu().type,
                            false
                        )
                    )
                ),
            )
        }
    }
}