package com.yes.trackdialogfeature


import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import com.github.tmurakami.dexopener.repackaged.com.google.common.collect.Iterables.any
import com.yes.core.presentation.BaseViewModel
import com.yes.trackdialogfeature.api.Dependency
import com.yes.trackdialogfeature.domain.usecase.GetChildMenuUseCase
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.mapper.MenuUiDomainMapper
import com.yes.trackdialogfeature.presentation.ui.TrackDialog
import com.yes.trackdialogfeature.presentation.ui.TrackDialogAdapter
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import io.mockk.*
import org.junit.Before
import org.junit.Test


class TrackDialogTest {
  /*  private val getChildMenuUseCase: GetChildMenuUseCase=mockk()

    private val viewModel = spyk(
        TrackDialogViewModel(getChildMenuUseCase, menuUiDomainMapper),
        recordPrivateCalls = true
    ){

    }
   // private val viewModel = spyk()

   // private val menuUiDomainMapper: MenuUiDomainMapper = mockk()
    /*   val viewModel = spyk(TrackDialogViewModel(any)) {
           every {
               test()
              /* setEvent(
                   any()
                  // TrackDialogContract.Event.OnItemClicked(0, "")
               )*/
           } returns 10
       }*/

   */
    private val viewModel=mockk<TrackDialogViewModel>()
  private val menuUiDomainMapper: MenuUiDomainMapper=mockk()
    private val adapter: TrackDialogAdapter = mockk(relaxed = true)
    private val dependency = TrackDialog.TrackDialogDependency(
        viewModel,
        menuUiDomainMapper,
        adapter
    )
    private val trackDialogFactory = MockFragmentFactoryImpl(
        dependency
    )
    private lateinit var scenario: FragmentScenario<TrackDialog>

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        every {
            viewModel.setEvent(any())
        } answers { }
        scenario = launchFragment(
            factory = trackDialogFactory
        )
    }

    @Test
    fun onInitShowsTrackDialogStateIdle() {

        val expected = TrackDialogContract.State(
            TrackDialogContract.TrackDialogState.Idle
        )
        /*  every {
              adapter.setViewModel(any())
          } returns Unit*/

        scenario.onFragment { fragment ->
            assert(fragment.requireDialog().isShowing)
            verify {
                viewModel.setEvent(
                    TrackDialogContract.Event.OnItemClicked(0, "")
                )
            }
        }
        // justRun { viewModel.setEvent(any()) }
        /* every {
             viewModel.test(1) } returns 10*/
        /*  val v=viewModel.test(1)
          assert(viewModel.test(1) == 10)*/
        /* every {
             viewModel.setEvent(TrackDialogContract.Event.OnItemClicked(0, ""))
         } returns Unit*/


        /* val scenario = launchFragment<TrackDialog>()
           scenario.moveToState(Lifecycle.State.STARTED)
           scenario.onFragment { fragment ->
               assert(fragment.requireDialog().isShowing)
           }*/
    }
}