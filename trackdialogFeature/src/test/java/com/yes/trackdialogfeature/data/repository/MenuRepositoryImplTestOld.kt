package com.yes.trackdialogfeature.data.repository


internal class MenuRepositoryImplTestOld {
   /* private val menuMapper: MenuMapper = mockk()
    private val menuDataSource: MenuDataStore = mockk()
    private val cut = MenuRepositoryImpl(menuMapper, menuDataSource)

    @Test
    fun `when getChildMenu param null returns root menu`() {
        // arrange
        val expected = MenuRepositoryFixturesOLD.getRootMenu()
        val menuDataStoreEntity = MenuDataStoreFixturesOLD.getItem()
        val param = 0
        every {
            menuDataSource.getItem(0)
        } returns menuDataStoreEntity
        every {
            menuMapper.map(menuDataStoreEntity)
        } returns expected
        // act
        val actual = cut.getChildMenu(param,"")
        // Assert
        verify(exactly = 1) { menuDataSource.getItem(0) }
        verify(exactly = 1) { menuMapper.map(menuDataStoreEntity) }
        assert(actual == expected)
    }

    @Test
    fun `when getChildMenu with specified param returns correct menu`() {
        // arrange
        val expected = MenuRepositoryFixturesOLD.getRootMenu()
        val menuDataStoreEntity = MenuDataStoreFixturesOLD.getItem()
        val param = 0
        every {
            menuDataSource.getItem(param)
        } returns menuDataStoreEntity
        every {
            menuMapper.map(menuDataStoreEntity)
        } returns expected
        // act
        val actual = cut.getChildMenu(param,"")
        // Assert
        verify(exactly = 1) { menuDataSource.getItem(param) }
        verify(exactly = 1) { menuMapper.map(menuDataStoreEntity) }
        assert(actual == expected)
    }

    @Test
    fun `when getRootItems returns root items`() {
        // arrange
        val expected = MenuRepositoryFixturesOLD.getRootItems()
        val menuDataStoreEntity = MenuDataStoreFixturesOLD.getRootItems()
        val param = 0
        every {
            menuDataSource.getItemsWithParentId(null)
        } returns menuDataStoreEntity
        every {
            menuMapper.mapToItem(any())
        } answers { expected.iterator().next() }
        // act
        val actual = cut.getRootItems()
        // Assert
        verify(exactly = 1) { menuDataSource.getItem(param) }
        verify(exactly = 3) { menuMapper.map(any()) }
        assert(actual == expected)
    }*/
}