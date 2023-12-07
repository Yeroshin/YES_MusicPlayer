package com.yes.alarmclockfeature.presentation.ui

import android.content.Context
import android.content.res.Resources.Theme
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.yes.alarmclockfeature.R
import com.yes.alarmclockfeature.databinding.AlarmSetScreenBinding
import com.yes.alarmclockfeature.presentation.ui.datepicker.DatePickerAdapter

class AlarmClockDialog: DialogFragment() {
    private lateinit var binding: ViewBinding

    private val binder by lazy {
        binding as AlarmSetScreenBinding
    }
    private val adapter by lazy {
        DatePickerAdapter(
            onListEnded= {count->
                binder.datepicker.scrollToPosition(count /2)
            }
        )
    }
    private val scrollList= object :()->Unit{
        override fun invoke() {
            binder.datepicker.scrollToPosition(adapter.itemCount /2)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, 0);
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlarmSetScreenBinding.inflate(inflater)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        setDialogSize()

    }
    private fun setDialogSize(){
        dialog?.window?.attributes?.dimAmount= 0.6f
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val screeSize = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val rect =(requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).currentWindowMetrics.bounds
            screeSize.x=rect.right
            screeSize.y=rect.bottom
        } else {
            val displayMetrics = DisplayMetrics()
            (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(displayMetrics)
            screeSize.x=displayMetrics.widthPixels
            screeSize.y=displayMetrics.heightPixels
        }
        dialog?.window?.setLayout(
            (screeSize.x*0.9).toInt(),
            (screeSize.y*0.9).toInt()
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupView()
    }

    private fun setupView(){

        binder.datepicker.layoutManager = LinearLayoutManager(context)
        binder.datepicker.adapter=adapter
        val values = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        adapter.setItems(values)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binder.datepicker)

       // binder.datepicker.scrollToPosition(adapter.itemCount /2)
    }
    class DimmedItemDecoration(private val dimColor: Int, private val dimHeight: Int) : RecyclerView.ItemDecoration() {

        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            super.onDraw(c, parent, state)

            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            for (i in 0 until parent.childCount) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + dimHeight

                val paint = Paint()
                paint.color = dimColor
                c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
            }
        }
    }
}