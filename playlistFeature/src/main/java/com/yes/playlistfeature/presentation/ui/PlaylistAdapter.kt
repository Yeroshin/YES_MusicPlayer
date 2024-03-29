package com.yes.playlistfeature.presentation.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.playlistfeature.databinding.ItemTrackBinding
import com.yes.playlistfeature.presentation.model.TrackUI
import java.util.Collections


class PlaylistAdapter :
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

    fun removeItem(position: Int) {
        //  itemList.removeAt(position)
        itemList.removeAt(position)
        notifyItemRemoved(position)
        /*val size = itemList.size
        itemList.clear()
        notifyItemRangeRemoved(0, size)*/
        //notifyDataSetChanged()
        //  notifyItemRangeRemoved(0,itemList.size)
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

    inner class ViewHolder(private val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
            item: TrackUI,
        ) {
val show=binding.root.isShown
            binding.root.isSelected = item.selected
            binding.root.isActivated = item.current
            binding.playlistTitle.text = item.title
            binding.songInfo.text = item.info
            binding.duration.text = item.duration
            binding.position.text = (position + 1).toString() + "."
            binding.root.setOnClickListener {

            }

        }
    }
}