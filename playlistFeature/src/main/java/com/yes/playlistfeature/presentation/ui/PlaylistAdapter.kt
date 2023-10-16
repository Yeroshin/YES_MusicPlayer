package com.yes.playlistfeature.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.playlistfeature.databinding.ItemTrackBinding
import com.yes.playlistfeature.presentation.model.TrackUI

class PlaylistAdapter:
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>()  {
    private var itemsList = mutableListOf<TrackUI>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistAdapter.ViewHolder {
        val binding = ItemTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    fun setItems(items: List<TrackUI>) {
        itemsList = items.toMutableList()
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: PlaylistAdapter.ViewHolder, position: Int) {
        holder.bind(position, itemsList[position])
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
    inner class ViewHolder(private val binding: ItemTrackBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
            item: TrackUI,
        ) {

            binding.root.isSelected = item.selected
            binding.root.isActivated = item.current
            binding.playlistTitle.text = item.title
            binding.songInfo.text = item.info
            binding.duration.text = item.duration
            binding.position.text=(position+1).toString()+"."
            binding.root.setOnClickListener {

            }
        }
    }
}