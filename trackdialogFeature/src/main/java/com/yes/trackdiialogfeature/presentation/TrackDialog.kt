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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yes.coreui.UniversalDialog
import com.yes.trackdiialogfeature.R
import com.yes.trackdiialogfeature.databinding.TrackDialogBinding
import com.yes.trackdiialogfeature.di.components.DaggerTrackDialogComponent
import com.yes.trackdiialogfeature.di.module.TrackDialogModule
import com.yes.trackdiialogfeature.domain.MediaItem
import javax.inject.Inject

class TrackDialog() : UniversalDialog() {
    @Inject
    lateinit var adapter: TrackDialogAdapter
    override var layout: Int = R.layout.track_dialog
    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerTrackDialogComponent.builder()
            .trackDialogModule(TrackDialogModule())
            .build()
            .inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TrackDialogBinding.inflate(inflater)
        super.onCreateView(inflater, container, savedInstanceState)
        /////////////////////
        val layoutManager:LinearLayoutManager= LinearLayoutManager(context)
        (binding as TrackDialogBinding).recyclerViewContainer.recyclerView.layoutManager=layoutManager
        (binding as TrackDialogBinding).recyclerViewContainer.recyclerView.adapter=adapter
        /////////////////
        val items= arrayListOf<MediaItem>()
        for (i in 0..10){
            val item:MediaItem= MediaItem()
            item.title="message"
            items.add(item)
        }
        adapter.setItems(items)

        /////////////////
        (binding as TrackDialogBinding).buttons.okBtn.setOnClickListener {
            dismiss()
        }

        return binding.root
    }


}