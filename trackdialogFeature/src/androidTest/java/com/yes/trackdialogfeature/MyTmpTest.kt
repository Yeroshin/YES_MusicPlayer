package com.yes.trackdialogfeature

import com.yes.trackdialogfeature.domain.entity.Menu
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class MyTmpTest {
   /* val final = mockk<MyTmp> {
        every {
            meaningOfLife() } returns 1
    }*/
   /* val viewModel = mockk<TrackDialogViewModel> {
        every {
            test(1) } returns 10
    }*/
   //private var tmp: MyTmp = mockk()
   // private var viewModel: TrackDialogViewModel = mockk()
  /*  @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
    }*/
    @Test
    fun name() {

        val tmp = mockk<MyTmp> {
            every {
                mytest() } returns DomainResultFactory.createSuccess(Menu("default", listOf()))
        }
      /*  every {
            tmp.mytest() } returns 10*/
        val c=tmp.mytest()
        assert(tmp.mytest() == DomainResultFactory.createSuccess(Menu("default", listOf())))
      /*  every {
            viewModel.test(1) } returns 10
        val v=viewModel.test(1)*/
     //   assert(viewModel.test(1) == 10)
       // val final:MyTmp=mockk(relaxed = true)
      /*  every {
            final.meaningOfLife()
        }returns 10
        val c=final.meaningOfLife()
        assert(final.meaningOfLife() == 1)*/
    }
}