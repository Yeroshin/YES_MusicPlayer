@file:Suppress("UNCHECKED_CAST")

package com.yes.trackdialogfeature.data.repository

import com.yes.core.Fixture
import com.yes.trackdialogfeature.data.dataSource.AudioDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.AudioDataStoreEntity
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Menu
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MenuRepositoryImplTest(
    private val getChildMenuFixture: Fixture<Menu>,
    private val getItemsWithParentIdFixture: Fixture<MenuDataStoreEntity>,
    private val getMediaItemsFixture: Fixture<List<AudioDataStoreEntity>>
) {
    private val menuMapper: MenuMapper = mockk()
    private val menuDataStore: MenuDataStore = mockk()
    private val audioDataStore: AudioDataStore = mockk()
    private val cut = MenuRepositoryImpl(
        menuMapper,
        menuDataStore,
        audioDataStore
    )


    @Test
    fun getRootItems() {
    }

    @Test
    fun getChildMenu() {
        val expected = getChildMenuFixture.result
        val actual = cut.getChildMenu(
            getChildMenuFixture.params["id"] as Int,
            getChildMenuFixture.params["name"] as String
        )
        // Assert
        verify(exactly = 1) {
            menuDataStore.getItemsWithParentId(
                getItemsWithParentIdFixture.params["id"] as Int
            )
        }
        verify(exactly = 1) { menuMapper.map(getItemsWithParentIdFixture.result) }
        verify(exactly = 1) {
            audioDataStore.getMediaItems(
                getMediaItemsFixture.params["projection"] as Array<String>,
                getMediaItemsFixture.params["selection"] as String?,
                getMediaItemsFixture.params["selectionArgs"] as Array<String>?
            )
        }
        assert(actual == expected)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    RepositoryFixtures.getArtistsMenuDomain(),
                    MenuDataStoreFixtures.getArtistsMenuDataStore(),
                    AudioDataStoreFixtures.getArtists()
                ),
            )
        }
    }

}





