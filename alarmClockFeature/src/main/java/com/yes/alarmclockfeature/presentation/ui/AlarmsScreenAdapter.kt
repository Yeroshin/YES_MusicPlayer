package com.yes.alarmclockfeature.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.alarmclockfeature.databinding.ItemAlarmlistBinding
import com.yes.alarmclockfeature.presentation.model.AlarmClockUI
import java.util.Collections

class AlarmsScreenAdapter :
    RecyclerView.Adapter<AlarmsScreenAdapter.ViewHolder>() {
    private val itemList = mutableListOf<AlarmClockUI>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmsScreenAdapter.ViewHolder {
        val binding = ItemAlarmlistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setItems(items: List<AlarmClockUI>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): AlarmClockUI {
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
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onBindViewHolder(holder: AlarmsScreenAdapter.ViewHolder, position: Int) {
        holder.bind(position, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: ItemAlarmlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
            item: AlarmClockUI,
        ) {


        }
    }
}