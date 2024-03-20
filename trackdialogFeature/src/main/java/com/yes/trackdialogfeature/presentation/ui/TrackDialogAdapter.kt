package com.yes.trackdialogfeature.presentation.ui

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.trackdialogfeature.databinding.ItemMediaBinding
import com.yes.trackdialogfeature.presentation.model.MenuUi

class TrackDialogAdapter :
    RecyclerView.Adapter<TrackDialogAdapter.TrackHolder>() {
    private var mediaList = listOf<MenuUi.ItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val binding = ItemMediaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    fun setItems(items: List<MenuUi.ItemUi>) {
        mediaList = items
        notifyDataSetChanged()
    }

    fun getItems(): List<MenuUi.ItemUi> {
        return mediaList
    }

    inner class TrackHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
        ) {

            binding.root.isSelected = false
            binding.root.isActivated = false
            binding.itemTitle.text = mediaList[position].name
            //TODO think about how to refactor this
            if(mediaList[position].name==".."){
                binding.checkBox.visibility=GONE
            }else{
                binding.checkBox.visibility= VISIBLE
            }
            binding.checkBox.setOnCheckedChangeListener{ buttonView, isChecked ->
                mediaList[position].selected=isChecked
            }
            binding.icon.setImageLevel(
                mediaList[position].iconType
            )
            binding.root.setOnClickListener {
                mediaList[position].onClick(mediaList[position].param)
            }
        }
    }


}