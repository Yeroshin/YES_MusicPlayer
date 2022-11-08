package com.yes.trackdiialogfeature.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.trackdiialogfeature.databinding.ItemMediaBinding
import com.yes.trackdiialogfeature.domain.MediaItem

class TrackDialogAdapter: RecyclerView.Adapter<TrackDialogAdapter.TrackHolder>() {
    private val trackList= arrayListOf<MediaItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val binding=ItemMediaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TrackHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.binding.itemTitle.setText(trackList[position].title)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    inner class TrackHolder(val binding:ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}