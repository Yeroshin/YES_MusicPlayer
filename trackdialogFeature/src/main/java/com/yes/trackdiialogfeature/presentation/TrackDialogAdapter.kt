package com.yes.trackdiialogfeature.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.trackdiialogfeature.databinding.ItemMediaBinding
import com.yes.trackdiialogfeature.domain.entity.Menu

class TrackDialogAdapter():
    RecyclerView.Adapter<TrackDialogAdapter.TrackHolder>(),
    RecyclerItemListener  {
    private lateinit var viewModel:TrackDialogViewModel
    private var trackList= arrayListOf<Menu>()
    private lateinit var menu: Menu

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
       val binding = ItemMediaBinding
           .inflate(LayoutInflater.from(parent.context), parent, false)

       return TrackHolder(binding)


    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        var iconType=0
        when(menu.type){
            "Media.TITLE"->iconType=2
            else->1
        }
        holder.bind(trackList[position],iconType,this)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }
    fun setViewModel(vm:TrackDialogViewModel){
        this.viewModel=vm
    }
    fun setItems(menu: Menu){
        this.menu=menu
        trackList= menu.children
        notifyDataSetChanged()
    }

    inner class TrackHolder(private val binding:ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Menu, iconType:Int, recyclerItemListener: RecyclerItemListener){

            binding.root.isSelected=false
            binding.root.isActivated=false
            binding.itemTitle.text = item.title
            binding.icon.setImageLevel(iconType)
            binding.root.setOnClickListener {
                recyclerItemListener.onItemSelected(adapterPosition)
            }
        }
    }

    override fun onItemSelected(position: Int) {
       //menu.selected=position
        //setItems(vm.getMenuItemContent(menu))

            viewModel.getMenuItemContent(menu.children[position])



    }


}