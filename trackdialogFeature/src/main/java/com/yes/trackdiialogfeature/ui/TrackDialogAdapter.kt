package com.yes.trackdiialogfeature.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.trackdiialogfeature.databinding.ItemMediaBinding
import com.yes.trackdiialogfeature.domain.MediaItem

class TrackDialogAdapter(private val vm:TrackDialogViewModel): RecyclerView.Adapter<TrackDialogAdapter.TrackHolder>(),RecyclerItemListener  {

    private var trackList= arrayListOf<MediaItem>()

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

    fun setItems(items:ArrayList<MediaItem>){
        trackList=items
    }

    inner class TrackHolder(private val binding:ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:MediaItem,recyclerItemListener: RecyclerItemListener){

            binding.root.isSelected=false
            binding.root.isActivated=false
            binding.itemTitle.setText(item.title)
            binding.root.setOnClickListener {
                recyclerItemListener.onItemSelected(item)
            }
        }
    }

    override fun onItemSelected(item: MediaItem) {
        TODO("Not yet implemented")
    }


}