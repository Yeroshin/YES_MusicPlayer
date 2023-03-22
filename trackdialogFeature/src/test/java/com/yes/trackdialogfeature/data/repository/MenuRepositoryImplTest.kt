package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.dataSource.AudioDataStoreFixtures
import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.mapper.MenuMapper
import com.yes.trackdialogfeature.data.repository.dataSource.AudioDataStore
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.domain.entity.DomainResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat


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
    fun `1 getMenu children handles api success and returns root MenuApiModel`() {
        // Given
        val expected = RepositoryFixtures.getCategoriesMenu()
        // every { menuDataStore.getRoot() } returns DataSourceFixtures.findRoot()
        every {
            menuDataStore.getRoot()
        } returns MenuDataStoreFixtures.getRoot()
        every {
            menuDataStore.getChildren(0)
        } returns MenuDataStoreFixtures.getCategoriesChildren()
        // When
        val actual = cut.getMenu()
        // Assert
        verify { menuDataStore.getRoot() }
        assertThat(expected,instanceOf(DomainResult.Success::class.java))
        assert((expected as DomainResult.Success).data == (actual as DomainResult.Success).data)
    }

    @Test
    fun `2 get albums children returns all albums`() {
        // Given
        val expected = RepositoryFixtures.getAlbumsMenu()
        every {
            menuDataStore.getChildren(2)
        } returns MenuDataStoreFixtures.getAlbumsChildren()
        every {
            menuDataStore.getItem(2)
        } returns MenuDataStoreFixtures.getAlbums()
        every {
            audioDataStore.getMediaItems(
                arrayOf("album"),
                null,
                emptyArray()
            )
        } returns AudioDataStoreFixtures.getAlbums()

        // When
        val actual = cut.getMenu(2, "albums")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify { audioDataStore.getMediaItems(any(), any(), any()) }
        assertThat(expected,instanceOf(DomainResult.Success::class.java))
        assert((expected as DomainResult.Success).data == (actual as DomainResult.Success).data)

    }

    @Test
    fun `3 get album children returns album tracks`() {
        // Given
        val expected = RepositoryFixtures.getAlbumTracksMenu()
        every {
            menuDataStore.getChildren(5)
        } returns MenuDataStoreFixtures.getAlbumChildren()
        every {
            menuDataStore.getItem(5)
        } returns MenuDataStoreFixtures.getAlbum()
        every {
            audioDataStore.getMediaItems(
                arrayOf("track"),
                "album",
                arrayOf("Brothers in Arms")
            )
        } returns AudioDataStoreFixtures.getTracks()

        // When
        val actual = cut.getMenu(5, "Brothers in Arms")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify { audioDataStore.getMediaItems(any(), any(), any()) }
        assertThat(expected,instanceOf(DomainResult.Success::class.java))
        assert((expected as DomainResult.Success).data == (actual as DomainResult.Success).data)
    }
    @Test
    fun `4 get track children returns DomainResultError`() {
        // Given
        val expected = RepositoryFixtures.getAlbumTracksMenu()
        every {
            menuDataStore.getChildren(5)
        } throws
        every {
            menuDataStore.getItem(5)
        } returns MenuDataStoreFixtures.getAlbum()
        every {
            audioDataStore.getMediaItems(
                arrayOf("track"),
                "album",
                arrayOf("Brothers in Arms")
            )
        } returns AudioDataStoreFixtures.getTracks()

        // When
        val actual = cut.getMenu(5, "Brothers in Arms")
        // Assert
        verify { menuDataStore.getChildren(any()) }
        verify { audioDataStore.getMediaItems(any(), any(), any()) }
        assertThat(expected,instanceOf(DomainResult.Success::class.java))
        assert((expected as DomainResult.Success).data == (actual as DomainResult.Success).data)
    }


}