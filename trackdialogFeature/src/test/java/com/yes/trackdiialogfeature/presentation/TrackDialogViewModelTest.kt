package com.yes.trackdiialogfeature.presentation

import com.yes.trackdiialogfeature.domain.MediaQuery
import com.yes.trackdiialogfeature.domain.common.BaseResult
import com.yes.trackdiialogfeature.domain.usecase.UseCase
import com.yes.trackdiialogfeature.domain.usecase.UseCaseWithParam
import com.yes.trackdiialogfeature.presentation.mapper.MenuMapper
import org.junit.Test
import org.mockito.kotlin.mock

class TrackDialogViewModelTest{
    private val showChildMenu = mock<UseCaseWithParam<MediaQuery, BaseResult<Any?>>>()
    private val getRootMenu= mock<UseCase<BaseResult<Any?>>>()
    private val menuMapper: MenuMapper

    private lateinit var viewModel:TrackDialogViewModel
    @Test
    fun `creating a viewmodel exposes loading ui state`() {

    }
}