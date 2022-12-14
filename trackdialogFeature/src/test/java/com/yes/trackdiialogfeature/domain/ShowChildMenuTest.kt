package com.yes.trackdiialogfeature.domain

import com.yes.trackdiialogfeature.data.repository.MenuRepository
import com.yes.trackdiialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdiialogfeature.domain.common.BaseResult
import com.yes.trackdiialogfeature.domain.entity.Menu
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
internal class ShowChildMenuTest {


    private lateinit var repository: FakeMediaRepository

    @Before
    fun setUp() {
        repository = FakeMediaRepository()
    }

    /* @Test
     fun `With Menu = null should Return Root Menu`() {
         //arrange
         val parentMenu: Menu? = null
         //act
         val childMenu = runBlocking {
             ShowChildMenu(repository).run(parentMenu)
         }
         //assert
         assertNotNull(childMenu)
         assertNull(childMenu.parent)
     }*/

    @Test
    fun `Root Menu should Return category Menu from selected item`() {
        //arrange
        /* val categoriesMenu = Menu("categories", null)
         categoriesMenu.selected = 1
         val item1 = MediaItem("Media.ARTIST", "genres")
         val item2 = MediaItem("Media.ARTIST", "artists")
         categoriesMenu.children.add(item1)
         categoriesMenu.children.add(item2)
         ///
         val expectedMenu = Menu("artists", categoriesMenu)
         val item3 = MediaItem("Media.ARTIST", "dire straits")
         expectedMenu.children.add(item3)
         //act
         val artistsMenu = runBlocking {
             ShowChildMenu(repository).run(categoriesMenu)
         }
         //assert
         assertNotNull(artistsMenu)
         assert(artistsMenu.parent!!.name.equals(categoriesMenu.name))
         assertEquals(1, artistsMenu.children.size)

         assert(artistsMenu.name.equals(expectedMenu.name))
         assert(artistsMenu.children[0].type.equals(item3.type))
         assert(artistsMenu.children[0].title.equals(item3.title))*/
    }

    @Test
    fun `category Menu should return track Menu from selected item `() {
        //arrange
        /* val categoriesMenu = Menu("Categories", null)
         categoriesMenu.selected=0
         val item1 = MediaItem("artists","Media.ARTIST")
         categoriesMenu.children.add(item1)

         val artistsMenu = Menu("artists", categoriesMenu )
         artistsMenu.selected=0
         val item3 = MediaItem("dire straits","Media.TITLE")
         artistsMenu.children.add(item3)


         val tracksMenu = Menu("tracks", artistsMenu)
         val item = MediaItem("money for nothing","Media.TITLE")
         tracksMenu.children.add(item)
         //act
         val trackMenu = runBlocking {
             ShowChildMenu(repository).run(artistsMenu)
         }*/
        //assert
        /*  assertEquals(1,trackMenu.items.size)
          assert(trackMenu.items[0].type.equals(expectedMenu.items[0].type))
          assert(trackMenu.name.equals(expectedMenu.name))*/

    }

    /* @Test
     fun shouldCreateTreeMenu() {

         val menuDataStore = MenuDataStore()
         val menuRepository = MenuRepository(menuDataStore)
         val mediaRepository = FakeMediaRepository()
         ///////////////root menu
         val rootMenu = menuRepository.getMenu()
         //////////////artists menu

         val artistsMenu = rootMenu.children[0]

         val query = MediaQuery(
             artistsMenu.type,
             artistsMenu.parent?.type,
             artistsMenu.title
         )
         val childrenArtists = mediaRepository.getMedia(query)
         for (item in childrenArtists) {
             val itemMenu = menuRepository.getMenuChild(artistsMenu.name)
             itemMenu.parent = artistsMenu
             itemMenu.title = item.title
             artistsMenu.children.add(itemMenu)
         }
         ///////////////////tracks menu
         val trackMenu = artistsMenu.children[0]
         val query2 = MediaQuery(
             trackMenu.type,
             trackMenu.parent?.type,
             trackMenu.title
         )
         val childrenTracks = mediaRepository.getMedia(query2)
         for (item in childrenTracks) {
             val itemMenu = menuRepository.getMenuChild(artistsMenu.name)
             itemMenu.parent = trackMenu
             itemMenu.title = item.title
             trackMenu.children.add(itemMenu)
         }
         //act
         val actualMenu = menuRepository.getMenu()
         //assert
         assertNull(null)


     }*/

    /* @Test
     fun useCaseTest() {
         val menuDataStore = MenuDataStore()
         val menuRepository = MenuRepository(menuDataStore)
         val mediaRepository = FakeMediaRepository()
         ///////////////root menu
         val rootMenu = menuRepository.getMenu()
         /////////////////////////////////////
         //act
         val artistMenu = runBlocking {
             ShowChildMenu(
                 menuRepository
             ).run(rootMenu.children[0])
         }
         val trackMenu = runBlocking {
             ShowChildMenu(
                 menuRepository
             ).run(artistMenu.children[0])
         }
         //assert
         assertNull(null)
     }*/
    @Test
    fun shouldCreateTreeMenu() {

        val menuDataStore = MenuDataStore()
        val mediaRepository = FakeMediaRepository()
        val menuRepository = MenuRepository(menuDataStore, mediaRepository)

        ///////////////root menu
        val rootMenu = menuRepository.getMenu()
        //////////////artists menu


    }

    @Test
    fun shouldGetArtistsMenu() {

        val menuDataStore = MenuDataStore()
        val mediaRepository = FakeMediaRepository()
        val menuRepository = MenuRepository(menuDataStore, mediaRepository)
        ///////////////root menu
        val rootMenu = menuRepository.getMenu()
        //////////////artists menu
        val artistsMenu = menuRepository.getMenu(
            MediaQuery(
                "artists",
                null,
                null
            )
        )
        //assert
        assertEquals(
            ( (artistsMenu as BaseResult.Success).data as Menu).name,
            "artists"
        )
        assertEquals(
            ((artistsMenu as BaseResult.Success).data as Menu).children[0].name,
            "artistTracks"
        )
        assertEquals(
            ((artistsMenu as BaseResult.Success).data as Menu).children[0].title,
            "Dire Straits"
        )


    }
    @Test
    fun shouldGetArtistsTracksMenu() {

        val menuDataStore = MenuDataStore()
        val mediaRepository = FakeMediaRepository()
        val menuRepository = MenuRepository(menuDataStore, mediaRepository)
        ///////////////root menu
        val rootMenu = menuRepository.getMenu()
        //////////////artists menu
        val artistsTracksMenu = menuRepository.getMenu(
            MediaQuery(
                "artistTracks",
                "artists",
                "Dire Straits"
            )
        )
        //assert
        assertEquals(
            ((artistsTracksMenu as BaseResult.Success).data as Menu).name,
            "artistTracks"
        )
        assertEquals(
            ((artistsTracksMenu as BaseResult.Success).data as Menu).children[0].name,
            ""
        )
        assertEquals(
            ((artistsTracksMenu as BaseResult.Success).data as Menu).children[0].title,
            "Money for Nothing"
        )


    }

}


