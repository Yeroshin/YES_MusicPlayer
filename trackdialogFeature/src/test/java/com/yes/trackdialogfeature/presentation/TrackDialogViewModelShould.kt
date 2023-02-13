package com.yes.trackdialogfeature.presentation

import com.yes.trackdialogfeature.domain.usecase.GetRootMenuUseCase
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.mapper.MenuMapper
import com.yes.trackdialogfeature.presentation.vm.TrackDialogState
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import org.junit.Test
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking

import io.mockk.mockk


class TrackDialogViewModelShould {
    private val getChildMenuUseCase: GetChildMenuUseCase = mockk()
    private val getRootMenuUseCase: GetRootMenuUseCase = mockk()
    private val menuMapper: MenuMapper = mockk()

    private lateinit var viewModel: TrackDialogViewModel

    @Test
    fun `emitsLoadingStateInitially`() = runBlocking {

        viewModel = TrackDialogViewModel(getChildMenuUseCase, getRootMenuUseCase, menuMapper)
        val actual = viewModel.uiState.value
        assertEquals(TrackDialogState.Loading, actual)
        /* viewModel.uiState.test{
             assertEquals(
                 TrackDialogViewModelState.Init,
                 awaitItem()
             )
             assertEquals(
                 TrackDialogViewModelState.IsLoading,
                 awaitItem()
             )
         }*/
    }


}