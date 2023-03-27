package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.data.dataSource.MenuDataStoreFixtures
import com.yes.trackdialogfeature.domain.entity.DomainResult
import com.yes.trackdialogfeature.domain.entity.MenuException
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import io.mockk.mockkStatic
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue

class MenuUIDomainMapperTest {

    private val cut=MenuUiDomainMapper()
    @Test
    fun `mapper maps to correct model`(){

        // arrange
        val domainModel = PresentationFixtures.getDomainModel()
        val expected=PresentationFixtures.getUiModel()

        val tmp=expected.hashCode()

        // act
        val actual = cut.map(domainModel){}
        val tmp1=actual.hashCode()
        // Assert
        assertTrue(tmp == tmp1)
       // assert(actual == expected)
    }

}