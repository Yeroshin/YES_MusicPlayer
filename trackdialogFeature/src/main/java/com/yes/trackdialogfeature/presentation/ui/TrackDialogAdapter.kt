package com.yes.trackdialogfeature.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.trackdialogfeature.databinding.ItemMediaBinding
import com.yes.trackdialogfeature.presentation.contract.TrackDialogContract
import com.yes.trackdialogfeature.presentation.vm.TrackDialogViewModel
import com.yes.trackdialogfeature.presentation.model.MenuUi

class TrackDialogAdapter :
    RecyclerView.Adapter<TrackDialogAdapter.TrackHolder>() {
   // private lateinit var viewModel: TrackDialogViewModel
    private var trackList = listOf<MenuUi.MediaItem>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val binding = ItemMediaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return TrackHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {

        /* when(menu.type){
             "Media.TITLE"->iconType=2
             else->1
         }*/
        holder.bind(position,trackList[position], trackList[position].iconType)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

   /* fun setViewModel(vm: TrackDialogViewModel) {
        this.viewModel = vm
    }*/

    fun setItems(items: List<MenuUi.MediaItem>) {

        trackList = items
        notifyDataSetChanged()
    }

    inner class TrackHolder(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position:Int,
            item: MenuUi.MediaItem,
            iconType: Int
        ) {

            binding.root.isSelected = false
            binding.root.isActivated = false
            binding.itemTitle.text = item.name
            binding.icon.setImageLevel(iconType)
            binding.root.setOnClickListener {
                trackList[position].onClick(trackList[position].param)
              /*  trackList[position].onClick(
                    TrackDialogContract.Event.OnItemClicked(
                        item.id,
                        item.name
                    )
                )*/
            }
        }
    }


}