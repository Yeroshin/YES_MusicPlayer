package com.yes.trackdiialogfeature.presentation

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.yes.coreui.UniversalDialog
import com.yes.trackdiialogfeature.R
import com.yes.trackdiialogfeature.databinding.TrackDialogBinding

class TrackDialog():UniversalDialog() {
    override var layout: Int= R.layout.track_dialog
    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=TrackDialogBinding.inflate(inflater)
        super.onCreateView(inflater, container, savedInstanceState)

        (binding as TrackDialogBinding ).buttons.okBtn.setOnClickListener{
            dismiss()
        }

        return binding.root
    }


}