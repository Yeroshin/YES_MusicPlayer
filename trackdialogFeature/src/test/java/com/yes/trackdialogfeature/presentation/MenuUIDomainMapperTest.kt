package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.domain.DomainFixtures
import com.yes.trackdialogfeature.presentation.mapper.UiMapper
import org.junit.jupiter.api.Test

class MenuUIDomainMapperTest {

    private val cut=UiMapper()
    @Test
    fun `mapper maps DomainModel to UiModel`(){

        // arrange
        val domainModel = DomainFixtures.getArtistsMenu()
        val onClick=PresentationFixtures.onClick
        val expected=PresentationFixtures.getArtistMenu()

        // act
        val actual = cut.map(domainModel,onClick)
        // Assert
        assert(actual == expected)
    }

}