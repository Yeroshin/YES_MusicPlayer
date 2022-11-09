package com.yes.trackdiialogfeature.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.trackdiialogfeature.R
import com.yes.trackdiialogfeature.databinding.ItemMediaBinding
import com.yes.trackdiialogfeature.domain.MediaItem
import com.yes.trackdiialogfeature.presentation.TrackDialogAdapter.TrackHolder

class TrackDialogAdapter: RecyclerView.Adapter<TrackHolder>()  {


    private var trackList= arrayListOf<MediaItem>()


   /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.item_media, parent, false)
        val viewHolder: TrackHolder = TrackHolder(view)

        return viewHolder
    }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
       val binding = ItemMediaBinding
           .inflate(LayoutInflater.from(parent.context), parent, false)
       return TrackHolder(binding)


    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int, payloads: MutableList<Any>) {

        holder.itemView.isSelected=true
       // holder.binding.itemTitle.isActivated=false

       // holder.itemView.isActivated = true
       // holder.itemView.isSelected = false
        holder.binding.itemTitle.setText(trackList[position].title)
    }




    override fun getItemCount(): Int {
        return trackList.size
    }

    fun setItems(items:ArrayList<MediaItem>){
        trackList=items
    }

    inner class TrackHolder(binding:ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}