package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.repository.dataSource.MediaDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.DataException
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify



import org.junit.jupiter.api.Test


class MenuRepositoryImplOLDTest {
    // private val menuDataStore: MenuDataStore = mockk(relaxed = true)
    private val menuDataStore: MenuDataStore = mockk(relaxed = true)
    private val mediaDataStore: MediaDataStore = mockk(relaxed = true)

    private val cut = MenuRepositoryImplOLD(
        menuDataStore,
        mediaDataStore,

    )

  /*  @Test
    fun `1 getMenu children handles api success and returns root MenuApiModel`() {
        // Given
        val expected = DomainResult.Success(RepositoryFixtures.getCategoriesMenu())
        // every { menuDataStore.getRoot() } returns DataSourceFixtures.findRoot()

        every {
            menuDataStore.getRootOld()
        } returns MenuDataStoreFixturesOLD.getRoot()
        every {
            menuDataStore.getChildren(0)
        } returns MenuDataStoreFixturesOLD.getCategoriesChildren()
        // When
        val actual = cut.getMenu()
        // Assert
        verify { menuDataStore.getRootOld() }
        assert(expected == actual)
       /* assertInstanceOf(DomainResult.Success::class.java, actual)
        assert(expected  == (actual as DomainResult.Success).data)*/
    }

    @Test
    fun `2 get albums children returns all albums`() {
        // Given
        val expected = DomainResult.Success(RepositoryFixtures.getAlbumsMenu())
        every {
            menuDataStore.getChildren(2)
        } returns MenuDataStoreFixturesOLD.getAlbumsChildren()
        every {
            menuDataStore.getItemOld(2)
        } returns MenuDataStoreFixturesOLD.getAlbumsOld()
        every {
            audioDataStore.getMediaItems(
                arrayOf("album"),
                null,
                emptyArray()
            )
        } returns AudioDataStoreFixturesOLD.getAlbums()

        // When
        val actual = cut.getMenu(2, "albums")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify (exactly = 1){ menuDataStore.getItemOld(any()) }
        verify { audioDataStore.getMediaItems(any(), any(), any()) }
        assert(expected == actual)
       /* assertInstanceOf(DomainResult.Success::class.java,actual)
        assert((expected as DomainResult.Success).data == (actual as DomainResult.Success).data)*/

    }

    @Test
    fun `3 get album children returns album tracks`() {
        // Given
        val expected = DomainResult.Success(RepositoryFixturesOld.getAlbumTracksMenu())
        every {
            menuDataStore.getChildren(5)
        } returns MenuDataStoreFixturesOLD.getAlbumChildren()
        every {
            menuDataStore.getItemOld(5)
        } returns MenuDataStoreFixturesOLD.getAlbum()
        every {
            audioDataStore.getMediaItems(
                arrayOf("track"),
                "album",
                arrayOf("Brothers in Arms")
            )
        } returns AudioDataStoreFixturesOLD.getTracks()

        // When
        val actual = cut.getMenu(5, "Brothers in Arms")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify { menuDataStore.getItemOld(any()) }
        verify { audioDataStore.getMediaItems(any(), any(), any()) }
        assert(expected == actual)
      /*  assertInstanceOf(DomainResult.Success::class.java,actual)
        assert((expected as DomainResult.Success).data == (actual as DomainResult.Success).data)*/
    }*/
    @Test
    fun `4 get track children returns DomainResultError`() {
        // Given
        val expected = DomainResult.Error(DomainFixtures.getError())
        every {
            menuDataStore.getChildren(8)
        } throws DataException

        // When
        val actual = cut.getMenu(8, "Money for Nothing")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify (exactly = 0){ menuDataStore.getItemOld(any()) }
        verify (exactly = 0){ mediaDataStore.getMediaItems(any(), any(), any()) }
        assert(expected == actual)
      /*  assertInstanceOf(DomainResult.Error::class.java,actual)
        assert((expected as DomainResult.Error).exception == (actual as DomainResult.Error).exception)*/
    }


}