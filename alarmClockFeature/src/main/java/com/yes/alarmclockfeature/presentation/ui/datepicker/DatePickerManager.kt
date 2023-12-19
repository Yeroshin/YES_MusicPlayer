package com.yes.alarmclockfeature.presentation.ui.datepicker

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class DatePickerManager(
    val context: Context,
    private val datePickerHour: RecyclerView,
    private val datePickerMinute: RecyclerView
) {
    private val adapterHour by lazy {
        DatePickerAdapter()
    }
    private val adapterMinute by lazy {
        DatePickerAdapter()
    }
    private val snapHelperHour by lazy {
        LinearSnapHelper()
    }
    private val snapHelperMinute by lazy {
        LinearSnapHelper()
    }


    private fun createOnGlobalLayoutListener(
        layoutManager: LinearLayoutManager,
        adapter: RecyclerView.Adapter<*>,
        view: View
    ): ViewTreeObserver.OnGlobalLayoutListener {
        return object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                layoutManager.findViewByPosition(0)?.height?.let { itemHeight ->
                    layoutManager.scrollToPositionWithOffset(
                        adapter.itemCount / 2 + 1,
                        view.height / 2 + itemHeight / 2
                    )
                }

            }
        }
    }

    private fun createScrollListener(
        adapter: DatePickerAdapter,
        callbackUp: (() -> Unit)?,
        callbackDown: (() -> Unit)?
    ): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            var prevTopMinutePos = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemIndex = layoutManager.findFirstVisibleItemPosition()

                val lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition()

                if (firstVisibleItemIndex == 0) {
                    layoutManager.findViewByPosition(firstVisibleItemIndex)?.top?.let {
                        layoutManager.scrollToPositionWithOffset(adapter.itemCount / 2 + 2, -it)
                    }
                }
                if (lastVisibleItemIndex == adapter.itemCount - 1) {
                    layoutManager.findViewByPosition(lastVisibleItemIndex)?.top?.let {
                        layoutManager.scrollToPositionWithOffset(adapter.itemCount / 2 - 1, it)
                    }
                }
                for (i in firstVisibleItemIndex..lastVisibleItemIndex) {
                    val item = adapter.getItem(i)
                    if (item == 1) {
                        val topPos = layoutManager.findViewByPosition(i)?.top!!

                        val center = recyclerView.height / 2
                        if (topPos < center && prevTopMinutePos >= center && topPos > 0) {
                            callbackUp?.invoke()
                        }
                        if (topPos >= center && prevTopMinutePos < center && prevTopMinutePos > 0) {
                            callbackDown?.invoke()
                        }
                        prevTopMinutePos = topPos
                    }
                }
            }
        }
    }

    fun setupView() {
        val valuesHour = List(24) { i -> i + 1 }
        val valuesMinute = List(60) { i -> i + 1 }
        val layoutManagerHour = LinearLayoutManager(context)
        val layoutManagerMinute = LinearLayoutManager(context)

        datePickerHour.layoutManager = layoutManagerHour
        datePickerMinute.layoutManager = layoutManagerMinute
        datePickerHour.adapter = adapterHour
        datePickerMinute.adapter = adapterMinute
        adapterHour.setItems(valuesHour)
        adapterMinute.setItems(valuesMinute)

        snapHelperHour.attachToRecyclerView(datePickerHour)
        snapHelperMinute.attachToRecyclerView(datePickerMinute)

        datePickerHour.addOnScrollListener(
            createScrollListener(adapterHour, null, null)
        )
        datePickerMinute.addOnScrollListener(
            createScrollListener(
                adapterMinute,
                {
                    datePickerHour.smoothScrollBy(
                        0,
                        datePickerHour.computeVerticalScrollRange() / datePickerHour.adapter!!.itemCount
                    )
                },
                {
                    datePickerHour.smoothScrollBy(
                        0,
                        datePickerHour.computeVerticalScrollRange() / datePickerHour.adapter!!.itemCount * -1
                    )
                }
            )
        )
        datePickerHour.rootView.viewTreeObserver.addOnGlobalLayoutListener(
            createOnGlobalLayoutListener(
                layoutManagerHour,
                adapterHour,
                datePickerHour
            )
        )
        datePickerHour.rootView.viewTreeObserver.addOnGlobalLayoutListener(
            createOnGlobalLayoutListener(
                layoutManagerMinute,
                adapterMinute,
                datePickerMinute
            )
        )


    }
    fun getTime():Time{
        val layoutManagerHour = datePickerHour.layoutManager as LinearLayoutManager
        val firstVisibleItemIndexHour = layoutManagerHour.findFirstVisibleItemPosition()
        val lastVisibleItemIndexHour = layoutManagerHour.findLastVisibleItemPosition()
        val middleItemIndexHour =
            (lastVisibleItemIndexHour - firstVisibleItemIndexHour) / 2 + firstVisibleItemIndexHour
        val hour = adapterHour.getItem(middleItemIndexHour)
        val layoutManagerMinute = datePickerMinute.layoutManager as LinearLayoutManager
        val firstVisibleItemIndexMinute = layoutManagerMinute.findFirstVisibleItemPosition()
        val lastVisibleItemIndexMinute = layoutManagerMinute.findLastVisibleItemPosition()
        val middleItemIndexMinute =
            (lastVisibleItemIndexMinute - firstVisibleItemIndexMinute) / 2 + firstVisibleItemIndexMinute
        val minute = adapterMinute.getItem(middleItemIndexMinute)
        return Time(hour,minute)
    }
    data class Time(val hour:Int,val minute:Int)
}