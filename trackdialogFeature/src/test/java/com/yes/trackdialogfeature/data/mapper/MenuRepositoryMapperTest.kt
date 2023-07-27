package com.yes.trackdialogfeature.data.mapper


import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.data.repository.entity.MenuDataStoreEntity
import com.yes.trackdialogfeature.domain.entity.Menu
import com.yes.trackdialogfeature.domain.entity.Menu.Item
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


internal class MenuRepositoryMapperTest {
    private val cut = MenuRepositoryMapper()

    @ParameterizedTest
    @MethodSource("mapToMenuData")
    fun mapToMenu(
        dataStoreEntityFixture: MenuDataStoreEntity,
        expectedDomainFixture: Menu
    ) {
        val actual = cut.mapToMenu(dataStoreEntityFixture)
        assert(actual == expectedDomainFixture)
    }

    @ParameterizedTest
    @MethodSource("mapToItemData")
    fun mapToItem(
        dataStoreEntityFixture: MenuDataStoreEntity,
        expectedDomainFixture: Item
    ) {

        val actual = cut.mapToItem(dataStoreEntityFixture)
        assert(actual == expectedDomainFixture)
    }

    companion object {
        @JvmStatic
        fun mapToItemData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    MenuDataStoreFixtures.getArtistsMenu(),
                    Item(
                        1,
                        "artists",
                        null,
                        false
                    ),
                ),
                arrayOf(
                    MenuDataStoreFixtures.getArtistMenu(),
                    Item(
                        4,
                        "",
                        "artist",
                        false
                    ),
                ),
            )
        }

        @JvmStatic
        fun mapToMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    MenuDataStoreFixtures.getCategoriesMenu(),
                    Menu(
                        "categories",
                        listOf()
                    )
                ),
            )
        }
    }

}