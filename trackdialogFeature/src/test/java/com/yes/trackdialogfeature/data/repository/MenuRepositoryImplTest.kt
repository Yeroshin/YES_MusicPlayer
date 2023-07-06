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
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*

//import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

//@RunWith(Parameterized::class)
class MenuRepositoryImplTest(
    /*  private val currentMenuDataStoreFixture: Fixture<MenuDataStoreEntity>,
      private val menuDataStoreFixture: Fixture<List<MenuDataStoreEntity>>,
      private val listAudioDataStoreFixture: Fixture<List<AudioDataStoreEntity>>,
      private val menuDomainFixture: Fixture<Menu>,
      private val menuItemDomainFixture: Fixture<List<Item>>,
      private val resultMenuDomainFixture: Fixture<Menu>,*/
) {
    private val menuMapper: MenuMapper = mockk()
    private val menuDataStore: MenuDataStore = mockk()
    private val audioDataStore: AudioDataStore = mockk()
    private val cut = MenuRepositoryImpl(
        menuMapper,
        menuDataStore,
        audioDataStore
    )

    @ParameterizedTest
    @MethodSource("saveToPlayListData")
    fun saveToPlayList(
        menuDomainFixture: Fixture<Item>
    ){
        val expected=DomainResult.Success(true)
        val actual=cut.saveToPlayList(menuDomainFixture.result)
        assert(actual == expected)
    }
    @ParameterizedTest
    @MethodSource("getRootItemsData")
    fun getRootItems(
        menuDataStoreFixture: Fixture<MenuDataStoreEntity>,
        menuDomainFixture: Fixture<Menu>,
        menuListDataStoreFixture: Fixture<List<MenuDataStoreEntity>>,
        menuListDomainFixture: Fixture<List<Item>>,
        resultMenuDomainFixture: Fixture<Menu>
    ) {
        val expected = DomainResult.Success(resultMenuDomainFixture.result)
        every {
            menuDataStore.getItem(menuDataStoreFixture.params["id"] as Int)
        } returns menuDataStoreFixture.result
        every {
            menuMapper.map(menuDataStoreFixture.result)
        } returns menuDomainFixture.result
        every {
            menuDataStore.getItemsWithParentId(menuListDataStoreFixture.params["id"] as Int)
        } returns menuListDataStoreFixture.result
        every {
            menuMapper.mapToItem(any<MenuDataStoreEntity>())
        } returnsMany menuListDomainFixture.result
        //Act
        val actual = cut.getRootMenu()
        // Assert
        verify(exactly = 1) {
            menuDataStore.getItem(menuDataStoreFixture.params["id"] as Int)
        }
        verify(exactly = 1) {
            menuMapper.map(menuDataStoreFixture.result)
        }
        verify(exactly = 1) {
            menuDataStore.getItemsWithParentId(menuListDataStoreFixture.params["id"] as Int)
        }
        verify(exactly = 3) {
            menuMapper.mapToItem(any<MenuDataStoreEntity>())
        }
        assert(actual == expected)
    }

    @ParameterizedTest
    @MethodSource("getChildMenuData")
    fun getChildMenu(
        currentMenuDataStoreFixture: Fixture<MenuDataStoreEntity>,
        menuDataStoreFixture: Fixture<List<MenuDataStoreEntity>>,
        listAudioDataStoreFixture: Fixture<List<AudioDataStoreEntity>>,
        menuDomainFixture: Fixture<Menu>,
        menuItemDomainFixture: Fixture<List<Item>>,
        resultMenuDomainFixture: Fixture<Menu>,
    ) {
        val expected = DomainResult.Success(resultMenuDomainFixture.result)
        every {
            menuDataStore.getItem(currentMenuDataStoreFixture.params["id"] as Int)
        } returns currentMenuDataStoreFixture.result
        every {
            menuDataStore.getItemsWithParentId(
                menuDataStoreFixture.params["id"] as Int
            )
        } returns menuDataStoreFixture.result
        every {
            audioDataStore.getMediaItems(
                listAudioDataStoreFixture.params["projection"] as Array<String>,
                listAudioDataStoreFixture.params["selection"] as String?,
                listAudioDataStoreFixture.params["selectionArgs"] as Array<String>
            )
        } returns listAudioDataStoreFixture.result
        every {
            menuMapper.mapToItem(any<AudioDataStoreEntity>())
        } returnsMany menuItemDomainFixture.result
        every {
            menuMapper.map(any())
        } returns menuDomainFixture.result
        //Act
        val actual = cut.getChildMenu(
            resultMenuDomainFixture.params["id"] as Int,
            resultMenuDomainFixture.params["name"] as String
        )
        // Assert
        verify(exactly = 1) {
            menuDataStore.getItem(currentMenuDataStoreFixture.params["id"] as Int)
        }
        verify(exactly = 1) {
            menuDataStore.getItemsWithParentId(
                menuDataStoreFixture.params["id"] as Int
            )
        }
        verify(exactly = 1) {
            audioDataStore.getMediaItems(
                listAudioDataStoreFixture.params["projection"] as Array<String>,
                listAudioDataStoreFixture.params["selection"] as String?,
                listAudioDataStoreFixture.params["selectionArgs"] as Array<String>
            )
        }
        verify(exactly = 5) {
            menuMapper.mapToItem(any<AudioDataStoreEntity>())
        }
        verify(exactly = 1) { menuMapper.map(menuDataStoreFixture.result.last()) }

        assert(actual == expected)
    }

    companion object {
        @JvmStatic
        fun saveToPlayListData(): List<Array<Any?>>{
            return listOf(
                arrayOf()
            )
        }
        @JvmStatic
        fun getRootItemsData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    MenuDataStoreFixtures.getCategoriesMenu(),
                    RepositoryFixtures.getCategoriesMenu(),
                    MenuDataStoreFixtures.getCategoriesItemsMenu(),
                    RepositoryFixtures.getCategoriesItems(),
                    RepositoryFixtures.getCategoriesMenu(),

                )
            )
        }

        @JvmStatic
        // @Parameterized.Parameters
        fun getChildMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    MenuDataStoreFixtures.getArtistsMenuDataStore(),
                    MenuDataStoreFixtures.getArtistListMenuDataStore(),
                    AudioDataStoreFixtures.getArtistsListAudioDataStore(),
                    RepositoryFixtures.getPrimaryArtistsMenuDomain(),
                    RepositoryFixtures.getArtistsMenuItemDomain(),
                    RepositoryFixtures.getArtistsMenuDomain(),
                ),
                arrayOf(
                    MenuDataStoreFixtures.getArtistMenuDataStore(),
                    MenuDataStoreFixtures.getTracksMenuDataStore(),
                    AudioDataStoreFixtures.getTracksListAudioDataStore(),
                    RepositoryFixtures.getPrimaryTracksMenuDomain(),
                    RepositoryFixtures.getTracksMenuItemDomain(),
                    RepositoryFixtures.getTracksMenuDomain()
                ),
            )
        }
    }

}





