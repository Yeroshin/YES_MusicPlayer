package com.yes.trackdiialogfeature.presentation

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.yes.coreui.UniversalDialog
import com.yes.trackdiialogfeature.R
import com.yes.trackdiialogfeature.databinding.TrackDialogBinding

class TrackDialog():UniversalDialog() {
    override var layout: Int= R.layout.track_dialog


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

       /* binding.root.minimumWidth=ViewGroup.LayoutParams.MATCH_PARENT
        binding.root.minimumHeight = ViewGroup.LayoutParams.MATCH_PARENT*/
        return binding.root
    }


}