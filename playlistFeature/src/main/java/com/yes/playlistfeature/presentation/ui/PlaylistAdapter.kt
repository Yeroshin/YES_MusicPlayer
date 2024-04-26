package com.yes.playlistfeature.presentation.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.playlistfeature.databinding.ItemTrackBinding
import com.yes.playlistfeature.presentation.model.TrackUI
import java.util.Collections


class PlaylistAdapter(
    val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {
    private val itemList = mutableListOf<TrackUI>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistAdapter.ViewHolder {
        val binding = ItemTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    fun setItems(items: List<TrackUI>) {
        // itemList=items
        //  val size = itemList.size
        itemList.clear()
        // notifyItemRangeRemoved(0, size)
        itemList.addAll(items)
        //  notifyItemRangeInserted(0, items.size)
        notifyDataSetChanged()
//notifyItemRangeChanged(0,itemList.size)
    }

    fun clearItewms() {
        itemList.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): TrackUI {
        return itemList[position]
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(itemList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(itemList, i, i - 1)
            }
        }
        // Collections.swap(itemList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onBindViewHolder(holder: PlaylistAdapter.ViewHolder, position: Int) {
        holder.bind(position, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setCurrent(position: Int) {
        val oldItem = itemList.indexOfFirst { it.current }
        if (oldItem != -1) {
            itemList[oldItem] = itemList[oldItem].copy(current = false)
            notifyItemChanged(oldItem)
        }
        if (position != -1) {
            itemList.getOrNull(position)?.let {
                itemList[position] = itemList[position].copy(current = true)
            }
            notifyItemChanged(position)
        }

    }

    inner class ViewHolder(private val binding: ItemTrackBinding) :
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
            binding.position.text = (position + 1).toString() + "."
            binding.root.setOnClickListener {
                onItemClick(position)
            }

        }
    }
}