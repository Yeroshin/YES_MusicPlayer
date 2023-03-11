package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.DataSourceFixtures
import com.yes.trackdialogfeature.data.RepositoryFixtures
import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify


import org.junit.Test

class MenuRepositoryImplTest {
    // private val menuDataStore: MenuDataStore = mockk(relaxed = true)
    private val menuDataStore: MenuDataStore = mockk(relaxed = true)
    private val audioDataStore: AudioDataStore = mockk(relaxed = true)
    private val menuMapper: MenuMapper = MenuMapper()
    private val cut = MenuRepositoryImpl(
        menuDataStore,
        audioDataStore,
        menuMapper
    )

    @Test
    fun `1 getMenu handles api success and returns root MenuApiModel`() {
        // Given
     /*   val expected = RepositoryFixtures.getRootMenu()
       // every { menuDataStore.getRoot() } returns DataSourceFixtures.findRoot()
        every {
            menuDataStore.getChildren(any())
        } returns DataSourceFixtures.getRootChildren()
        // When
        val actual = cut.getMenu()
        // Assert
        verify { menuDataStore.getRoot() }
        assert(compareMenuApiModel(expected, actual))*/
        TODO("Not yet implemented")
    }

    @Test
    fun `2 get artists returns all artists`() {
        // Given
     /*   val expected = RepositoryFixtures.getArtistsMenu()
        every {
            menuDataStore.getChildren("artists")
        } returns arrayOf("artist")
        every {
            audioDataStore.getMediaItems(
                arrayOf("artist"),
                null,
                emptyArray()
            )
        } returns arrayOf("Chris Rea", "Dire Straits")

        // When
        val actual = cut.getMenu("artists", "artists")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify { audioDataStore.getMediaItems(any(), any(), any()) }
        assert(compareMenuApiModel(expected, actual))*/
        TODO("Not yet implemented")
    }

    @Test
    fun `3 get artist returns artist tracks & artist albums`() {
        // Given
     /*   val expected = RepositoryFixtures.getArtistMenu()
        every {
            menuDataStore.getChildren("artist")
        } returns arrayOf("artistTracks", "artistAlbums")
        /*  every {
              audioDataStore.getMediaItems(
                  arrayOf("artists"),
                  null,
                  null
              )
          } returns DataSourceFixtures.getArtistsChildren()*/

        // When
        val actual = cut.getMenu("artist", "Dire Straits")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify(exactly = 0) { audioDataStore.getMediaItems(any(), any(), any()) }
        assert(compareMenuApiModel(expected, actual))*/
        TODO("Not yet implemented")
    }

    @Test
    fun `4 get artistTracks returns artist tracks`() {
        // Given
     /*   val expected = RepositoryFixtures.getArtistAlbumsMenu()
        every {
            menuDataStore.getChildren("artistAlbums")
        } returns arrayOf("album")
          every {
              audioDataStore.getMediaItems(
                  arrayOf("album"),
                  "artist",
                  arrayOf("Dire Straits")
              )
          } returns arrayOf("Brothers in Arms", "Love over Gold")

        // When
        val actual = cut.getMenu("artistAlbums", "Dire Straits")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify { audioDataStore.getMediaItems(any(), any(), any()) }
        assert(compareMenuApiModel(expected, actual))*/
        TODO("Not yet implemented")
    }

    private fun compareMenuApiModel(
        firstModel: MenuDataStoreEntity,
        secondModel: MenuDataStoreEntity
    ): Boolean {
    /*    var result = false
        if (firstModel.type == secondModel.type &&
            firstModel.name == secondModel.name &&
            firstModel.children.size == secondModel.children.size
        ) {
            if (firstModel.children.isNotEmpty()) {
                for (x in 0..firstModel.children.size - 1) {
                    if (!firstModel.children[x].name.equals(secondModel.children[x].name) &&
                        !firstModel.children[x].type.equals(secondModel.children[x].type)) {
                        result = false
                        break
                    }
                    result = true
                }
            } else {
                result = true
            }
        }
        return result*/
        TODO("Not yet implemented")

    }


}