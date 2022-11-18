package com.yes.trackdiialogfeature.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.trackdiialogfeature.databinding.ItemMediaBinding
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.domain.Menu

class TrackDialogAdapter(): RecyclerView.Adapter<TrackDialogAdapter.TrackHolder>(),RecyclerItemListener  {
    private lateinit var vm:TrackDialogViewModel
    private var trackList= arrayListOf<MediaItem>()
    private lateinit var menu:Menu

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
       val binding = ItemMediaBinding
           .inflate(LayoutInflater.from(parent.context), parent, false)

       return TrackHolder(binding)


    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(trackList[position],this)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }
    fun setViewModel(vm:TrackDialogViewModel){
        this.vm=vm
    }
    fun setItems(menu: Menu){
        this.menu=menu
        trackList=menu.items
        notifyDataSetChanged()
    }

    inner class TrackHolder(private val binding:ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:MediaItem,recyclerItemListener: RecyclerItemListener){

            binding.root.isSelected=false
            binding.root.isActivated=false
            binding.itemTitle.setText(item.title)
            binding.root.setOnClickListener {
                recyclerItemListener.onItemSelected(adapterPosition)
            }
        }
    }

    override fun onItemSelected(position: Int) {
        menu.selected=position
        setItems(vm.getMenuItemContent(menu))

    }


}