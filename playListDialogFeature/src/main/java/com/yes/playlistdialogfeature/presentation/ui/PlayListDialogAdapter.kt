package com.yes.playlistdialogfeature.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.playlistdialogfeature.databinding.ItemPlaylistBinding
import com.yes.playlistdialogfeature.presentation.model.ItemUi

class PlayListDialogAdapter:
    RecyclerView.Adapter<PlayListDialogAdapter.PlayListHolder>() {

    private var itemsList = listOf<ItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListHolder {
        val binding = ItemPlaylistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayListHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
    fun setItems(items: List<ItemUi>) {
        itemsList = items
        notifyDataSetChanged()
    }
    fun getItems():List<ItemUi>{
        return itemsList
    }


    override fun onBindViewHolder(holder: PlayListHolder, position: Int) {
        holder.bind(position, itemsList[position])
    }
    inner class PlayListHolder(private val binding:ItemPlaylistBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(
            position: Int,
            item: ItemUi,
        ) {
            binding.root.isSelected = item.selected
            binding.root.isActivated = item.current
            binding.playlistTitle.text = item.name
            binding.id.text=position.toString()
            binding.root.setOnClickListener {
                itemsList.find { it.selected }?.let {
                    it.selected=false
                    notifyItemChanged(itemsList.indexOf(it))

                }
                item.selected=true
                notifyItemChanged(position)
            }
        }
    }
}