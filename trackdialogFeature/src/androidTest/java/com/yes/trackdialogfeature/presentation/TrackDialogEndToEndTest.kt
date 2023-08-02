package com.yes.trackdialogfeature.presentation

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import org.junit.Before

class TrackDialogEndToEndTest {
    private lateinit var viewModel:TrackDialogViewModel
    private lateinit var adapter:TrackDialogAdapter
 /*   private val dependency = TrackDialog.TrackDialogDependency(
        viewModel,
        adapter
    )
    private val trackDialogFactory = MockFragmentFactoryImpl(
        dependency
    )*/
    private lateinit var scenario: FragmentScenario<TrackDialog>
    @Before
    fun setUp() {
        //  MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks

     /*   scenario = launchFragment(
            factory = trackDialogFactory
        )*/

    }

}