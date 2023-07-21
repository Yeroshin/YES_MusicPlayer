package com.yes.trackdialogfeature.data.repository

import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.mapper.MenuRepositoryMapper
import com.yes.trackdialogfeature.data.repository.dataSource.MenuDataStore
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import com.yes.trackdialogfeature.domain.entity.Menu
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class MenuRepositoryImplTest {
    private val menuRepositoryMapper: MenuRepositoryMapper = mockk()
    private val menuDataStore: MenuDataStore = mockk()
    private lateinit var cut: MenuRepositoryImpl

    @BeforeEach
    fun setUp()  {
        // @MockK
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        cut = MenuRepositoryImpl(
            menuRepositoryMapper,
            menuDataStore,
        )
    }

    @ParameterizedTest
    @MethodSource("getRootMenuData")
    fun getRootMenu(
        expected: Menu?,
        rootMenuItemId: Int,
        menuEntity: MenuDataStoreEntity?,
        itemsEntity: List<MenuDataStoreEntity>,
        itemsDomain: List<Item>,
        menuDomain: Menu
    ) {
        every {
            menuDataStore.getRootMenuId()
        } returns rootMenuItemId
        every {
            menuDataStore.getItem(rootMenuItemId)
        } returns menuEntity
        every {
            menuDataStore.getItemsWithParentId(rootMenuItemId)
        } returns itemsEntity
        every {
            menuRepositoryMapper.mapToItem(any())
        } returnsMany itemsDomain
        menuEntity?.let {
            every {
                menuRepositoryMapper.mapToMenu(it)
            } returns menuDomain
        }

        val actual = cut.getRootMenu()
        assert(expected == actual)
    }

    @ParameterizedTest
    @MethodSource("getChildMenuData")
    fun getChildMenu(
        expected: Menu?,
        inputParam: Int,
        childItemsEntity: List<MenuDataStoreEntity>,
        childMenu: Menu
    ) {
        every {
            menuDataStore.getItemsWithParentId(inputParam)
        } returns childItemsEntity
        every {
            menuRepositoryMapper.mapToMenu(any())
        } returns childMenu
        val actual = cut.getChildMenu(inputParam)
        assert(expected == actual)
    }

    @ParameterizedTest
    @MethodSource("getChildItemData")
    fun getChildItem(
        expected: Item?,
        inputParam: Int,
        childItemsEntity: List<List<MenuDataStoreEntity>>,
        item:Item
        ) {
        every {
            menuDataStore.getItemsWithParentId(inputParam)
        } returnsMany childItemsEntity
        every {
            menuRepositoryMapper.mapToItem(any())
        }returns item
        val actual = cut.getChildItem(inputParam)
        assert(expected == actual)
    }

    companion object {
        @JvmStatic
        fun getRootMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    DomainFixtures.getCategoriesMenu(),
                    MenuDataStoreFixtures.getRootMenuId(),
                    MenuDataStoreFixtures.getCategoriesMenu(),
                    MenuDataStoreFixtures.getCategoryItems(),
                    DomainFixtures.getCategoryItems(),
                    DomainFixtures.getPrimaryCategoriesMenu()
                ),
                arrayOf(
                    null,
                    MenuDataStoreFixtures.getRootMenuId(),
                    null,
                    MenuDataStoreFixtures.getCategoryItems(),
                    DomainFixtures.getCategoryItems(),
                    DomainFixtures.getPrimaryCategoriesMenu()
                ),
            )
        }

        @JvmStatic
        fun getChildMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    DomainFixtures.getArtistsMenu(),
                    MenuDataStoreFixtures.getArtistMenuId(),
                    listOf(MenuDataStoreFixtures.getArtistMenu()),
                    DomainFixtures.getArtistsMenu()
                ),
                arrayOf(
                    null,
                    MenuDataStoreFixtures.getArtistMenuId(),
                    listOf<MenuDataStoreEntity>(),
                    DomainFixtures.getArtistsMenu()
                ),
            )
        }

        @JvmStatic
        fun getChildItemData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    DomainFixtures.getTracksItems().last(),
                    MenuDataStoreFixtures.getArtistMenuId(),
                    listOf(
                        listOf( MenuDataStoreFixtures.getArtistMenu()),
                       listOf( MenuDataStoreFixtures.getTrackMenu())
                    ),
                    DomainFixtures.getTracksItems().last()
                ),
                arrayOf(
                    null,
                    MenuDataStoreFixtures.getArtistMenuId(),
                    listOf(
                        listOf(),
                        listOf( MenuDataStoreFixtures.getTrackMenu())
                    ),
                    DomainFixtures.getTracksItems().last()
                ),
                arrayOf(
                    null,
                    MenuDataStoreFixtures.getArtistMenuId(),
                    listOf(
                        listOf( MenuDataStoreFixtures.getArtistMenu()),
                        listOf(),
                    ),
                    DomainFixtures.getTracksItems().last()
                ),
            )
        }
    }
}