@file:Suppress("UNCHECKED_CAST")

package com.yes.trackdialogfeature.data.repository


import com.yes.core.Fixture
import com.yes.trackdialogfeature.data.dataSource.MediaDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.PlayListDataBaseFixtures
import com.yes.trackdialogfeature.data.dataSource.SharedPreferencesFixtures
import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.MediaDataStoreEntity
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.data.repository.entity.PlayListDao
import com.yes.trackdialogfeature.data.repository.entity.TrackEntity
import com.yes.trackdialogfeature.domain.DomainFixtures
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
class MenuRepositoryImplTest {
    private val menuRepositoryMapper: MenuRepositoryMapper = mockk()
    private val menuDataStore: MenuDataStore = mockk()
    private val mediaDataStore: MediaDataStore = mockk()
    private val playListDao: PlayListDao = mockk(relaxed = true)
    private val cut = MenuRepositoryImpl(
        menuRepositoryMapper,
        menuDataStore,
        mediaDataStore,
        playListDao
    )

    @ParameterizedTest
    @MethodSource("saveToPlayListData")
    fun saveToPlayList(
        menuItemFixture: Fixture<List<Item>>,
        menuDataStoreFixture: Fixture<List<MenuDataStoreEntity>>,
        mediaDataStoreFixture: Fixture<List<MediaDataStoreEntity>>,
        playListDataBaseFixtures: Fixture<List<TrackEntity>>
    ) {

        val expected = DomainResult.Success(true)
        val count = MediaDataStoreFixtures.getCount()
        val playlistName = SharedPreferencesFixtures.getPlayListName()
        val result = List(5){playListDataBaseFixtures.result}.flatten()


        every {
            menuDataStore.getItem(any())
        } returnsMany menuDataStoreFixture.result

        every {
            mediaDataStore.getAudioItems(
                any(),
                any()
            )
        } returns mediaDataStoreFixture.result
        every {
            menuRepositoryMapper.mapToTrackEntity(
                any()
            )
        } returnsMany playListDataBaseFixtures.result

        
        val actual = cut.saveToPlayList(
            playlistName,
            menuItemFixture.result,
        )

        verify(exactly = count) {
            menuDataStore.getItem(any())
        }
        verify(exactly = count) {
            mediaDataStore.getAudioItems(
                any(),
                any()
            )
        }
        verify(atLeast = count) {
            menuRepositoryMapper.mapToTrackEntity(
                any()
            )
        }
        verify(exactly = 1) {
            playListDao.saveTracks(any())
        }
        assert(actual == expected)
    }

    @ParameterizedTest
    @MethodSource("getRootItemsData")
    fun getRootMenu(
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
            menuRepositoryMapper.map(menuDataStoreFixture.result)
        } returns menuDomainFixture.result
        every {
            menuDataStore.getItemsWithParentId(menuListDataStoreFixture.params["id"] as Int)
        } returns menuListDataStoreFixture.result
        every {
            menuRepositoryMapper.mapToItem(any<MenuDataStoreEntity>())
        } returnsMany menuListDomainFixture.result
        //Act
        val actual = cut.getRootMenu()
        // Assert
        verify(exactly = 1) {
            menuDataStore.getItem(menuDataStoreFixture.params["id"] as Int)
        }
        verify(exactly = 1) {
            menuRepositoryMapper.map(menuDataStoreFixture.result)
        }
        verify(exactly = 1) {
            menuDataStore.getItemsWithParentId(menuListDataStoreFixture.params["id"] as Int)
        }
        verify(exactly = 3) {
            menuRepositoryMapper.mapToItem(any<MenuDataStoreEntity>())
        }
        assert(actual == expected)
    }

    @ParameterizedTest
    @MethodSource("getChildMenuData")
    fun getChildMenu(
        currentMenuDataStoreFixture: Fixture<MenuDataStoreEntity>,
        menuDataStoreFixture: Fixture<List<MenuDataStoreEntity>>,
        listAudioDataStoreFixture: Fixture<List<MediaDataStoreEntity>>,
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
            mediaDataStore.getMediaItems(
                listAudioDataStoreFixture.params["projection"] as Array<String>,
                listAudioDataStoreFixture.params["selection"] as String?,
                listAudioDataStoreFixture.params["selectionArgs"] as Array<String>
            )
        } returns listAudioDataStoreFixture.result
        every {
            menuRepositoryMapper.mapToItem(any<MediaDataStoreEntity>())
        } returnsMany menuItemDomainFixture.result
        every {
            menuRepositoryMapper.map(any())
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
            mediaDataStore.getMediaItems(
                listAudioDataStoreFixture.params["projection"] as Array<String>,
                listAudioDataStoreFixture.params["selection"] as String?,
                listAudioDataStoreFixture.params["selectionArgs"] as Array<String>
            )
        }
        verify(atLeast = 5) {
            menuRepositoryMapper.mapToItem(any<MediaDataStoreEntity>())
        }
        verify(exactly = 1) { menuRepositoryMapper.map(menuDataStoreFixture.result.last()) }

        assert(actual == expected)
    }

    companion object {
        @JvmStatic
        fun saveToPlayListData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        mapOf(

                        ),
                        DomainFixtures.getArtistsMenuItemsList()
                    ),
                    Fixture(
                        mapOf(

                        ),
                        List(
                            MediaDataStoreFixtures.getCount()
                        ) {
                            MenuDataStoreFixtures.getArtistMenu()
                        }
                    ),
                    Fixture(
                        mapOf(

                        ),
                        MediaDataStoreFixtures.getTracksList()
                    ),
                    Fixture(
                        mapOf(

                        ),
                       PlayListDataBaseFixtures.getTracksList()
                    )
                )

            )
        }

        @JvmStatic
        fun getRootItemsData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        mapOf(
                            "id" to 0
                        ),
                        MenuDataStoreFixtures.getCategoriesMenu()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 0,
                            "name" to ""
                        ),
                        DomainFixtures.getCategoriesMenu()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 0
                        ),
                        MenuDataStoreFixtures.getCategoriesItemsMenu()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 0,
                            "name" to ""
                        ),
                        DomainFixtures.getCategoriesItemsList()
                    ),
                    Fixture(
                        mapOf(

                        ),
                        DomainFixtures.getCategoriesMenu()
                    )
                )
            )
        }

        @JvmStatic
        // @Parameterized.Parameters
        fun getChildMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        mapOf(
                            "id" to 1
                        ),
                        MenuDataStoreFixtures.getArtistsMenu()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 1
                        ),
                        MenuDataStoreFixtures.getArtistListMenu()
                    ),
                    Fixture(
                        mapOf(
                            "projection" to arrayOf("artist"),
                            "selection" to null,
                            "selectionArgs" to emptyArray()
                        ),
                        MediaDataStoreFixtures.getArtistsListMedia()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 0,
                            "name" to ""
                        ),
                        DomainFixtures.getPrimaryArtistsMenu()
                    ),
                    Fixture(
                        mapOf(

                        ),
                        DomainFixtures.getArtistsMenuItemsList()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 1,
                            "name" to "artists"
                        ),
                        DomainFixtures.getArtistsMenu()
                    ),
                ),
                arrayOf(
                    Fixture(
                        mapOf(
                            "id" to 4
                        ),
                        MenuDataStoreFixtures.getArtistMenu()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 4
                        ),
                        MenuDataStoreFixtures.getTracksMenu()
                    ),
                    Fixture(
                        mapOf(
                            "projection" to arrayOf("track"),
                            "selection" to "artist",
                            "selectionArgs" to arrayOf(MediaDataStoreFixtures.getArtistsListMedia()[0].title)
                        ),
                        MediaDataStoreFixtures.getTracksListMedia()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 4,
                            "name" to DomainFixtures.artists[0].name
                        ),
                        DomainFixtures.getPrimaryTracksMenu()
                    ),
                    Fixture(
                        mapOf(

                        ),
                        DomainFixtures.getTracksMenuItemsList()
                    ),
                    Fixture(
                        mapOf(
                            "id" to 4,
                            "name" to DomainFixtures.artists[0].name
                        ),
                        DomainFixtures.getTracksMenu()
                    )

                ),
            )
        }
    }

}





