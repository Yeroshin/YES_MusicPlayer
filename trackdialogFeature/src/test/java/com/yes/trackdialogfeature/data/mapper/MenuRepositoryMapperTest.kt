package com.yes.trackdialogfeature.data.mapper

import com.yes.core.Fixture
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
        dataStoreEntityFixture: Fixture<MenuDataStoreEntity>,
        expectedDomainFixture: Fixture<Menu>
    ) {
        val actual = cut.mapToMenu(dataStoreEntityFixture.data)
        assert(actual == expectedDomainFixture.data)
    }

    @ParameterizedTest
    @MethodSource("mapToItemData")
    fun mapToItem(
        dataStoreEntityFixture: Fixture<MenuDataStoreEntity>,
        expectedDomainFixture: Fixture<Item>
    ) {

        val actual = cut.mapToItem(dataStoreEntityFixture.data)
        assert(actual == expectedDomainFixture.data)
    }

    companion object {
        @JvmStatic
        fun mapToItemData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        MenuDataStoreFixtures.getArtistsMenu()
                    ),
                    Fixture(
                        Item(
                            1,
                            "artists",
                            null,
                            false
                        )
                    ),
                ),
                arrayOf(
                    Fixture(
                        MenuDataStoreFixtures.getArtistMenu()
                    ),
                    Fixture(
                        Item(
                            4,
                            "",
                            "artist",
                            false
                        )
                    ),
                ),
            )
        }

        @JvmStatic
        fun mapToMenuData(): List<Array<Any?>> {
            return listOf(
                arrayOf(
                    Fixture(
                        MenuDataStoreFixtures.getCategoriesMenu()
                    ),
                    Fixture(
                        Menu(
                            "categories",
                        )
                    )
                ),
                arrayOf(
                    Fixture(
                        MenuDataStoreFixtures.getArtistMenu()
                    ),
                    Fixture(
                        null
                    )
                ),
            )
        }
    }

}