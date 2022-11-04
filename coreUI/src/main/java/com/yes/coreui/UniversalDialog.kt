package com.yes.coreui

import android.R
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView


abstract class UniversalDialog : DialogFragment() {
    abstract val recyclerView:RecyclerView
    abstract val cancel_btn:ImageButton
    abstract val ok_btn:ImageButton
    abstract var dialog_layout: FrameLayout
    abstract var myDialog: Dialog?
    abstract val layout:Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDialog = dialog
        dialog_layout = inflater.inflate(layout, null, false) as FrameLayout //files_list

        /////////////////////
        /////////////////////
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        ///////////////////

        ///////////////////
        dialog!!.setContentView(layout)
        /////////////////////
        /////////////////////
        val disp: Display = (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val outSmallestSize = Point()
        val outLargestSize = Point()
        disp.getCurrentSizeRange(outSmallestSize, outLargestSize)
        // Point screeSize = new Point();
        // Point screeSize = new Point();
        val screeSize1 = Point()
        //  disp.getRealSize(screeSize);///Not Matched!
        //  disp.getRealSize(screeSize);///Not Matched!
        disp.getSize(screeSize1) ///Matched!

        ////////////notused?
        ////////////notused?
        dialog_layout.minimumWidth = (screeSize1.x * 0.9f)as Int
        dialog_layout.minimumHeight = (screeSize1.y * 0.9f)as Int
        dialog!!.window!!.setLayout(
            (screeSize1.x * 0.92f) as Int,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        ////////////
        ////////////
      /*  recyclerView = dialog_layout.findViewById<View>(R.id.recyclerView)
        cancel_btn = dialog_layout.findViewById<View>(R.id.cancel_btn)
        ok_btn = dialog_layout.findViewById<View>(R.id.ok_btn)*/

        /////////////////////////
        /////////////////////////
        dialog!!.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        isCancelable = false

        /////////////////////////////
        return dialog_layout
    }
}