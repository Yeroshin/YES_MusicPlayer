package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import org.junit.Test

class MenuUIDomainMapperTest {

    private val cut=MenuUiDomainMapper()
    @Test
    fun `mapper maps DomainModel to UiModel`(){

        // arrange
        val domainModel = PresentationFixtures.getDomainModel()
        val onClick=PresentationFixtures.onClick
        val expected=PresentationFixtures.getUiModel()

        // act
        val actual = cut.map(domainModel,onClick)
        // Assert
        assert(actual == expected)
    }

}