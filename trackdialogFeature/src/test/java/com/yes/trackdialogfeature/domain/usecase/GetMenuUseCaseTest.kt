package com.yes.trackdialogfeature.domain.usecase

import com.yes.trackdialogfeature.utils.CoroutineRule
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Rule
import org.junit.Test


internal class GetMenuUseCaseTest{
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val cut = GetMenuUseCase(
        testDispatcher
    )

    @get:Rule
    val coroutineRule = CoroutineRule(testDispatcher)

    @Test
    fun `loads categories menu`(){

    }
    @Test
    fun `loads albums menu`(){

    }
    @Test
    fun `loads tracks menu`(){

    }
    @Test
    fun `when subcategory not exist returns MenuException_Empty`(){

    }
}