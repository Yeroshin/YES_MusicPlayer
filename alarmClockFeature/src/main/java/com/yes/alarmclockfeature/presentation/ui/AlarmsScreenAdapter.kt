package com.yes.alarmclockfeature.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yes.alarmclockfeature.R
import com.yes.alarmclockfeature.databinding.ItemAlarmlistBinding
import com.yes.alarmclockfeature.presentation.model.AlarmUI
import com.yes.alarmclockfeature.presentation.model.DayOfWeek
import java.util.Collections
import java.util.concurrent.TimeUnit

class AlarmsScreenAdapter(
    val onItemOnCheckedChange: (alarm: AlarmUI) -> Unit
) : RecyclerView.Adapter<AlarmsScreenAdapter.ViewHolder>() {
    private val itemList = mutableListOf<AlarmUI>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlarmsScreenAdapter.ViewHolder {
        val binding = ItemAlarmlistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(parent.context,binding)
    }

    fun setItems(items: List<AlarmUI>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): AlarmUI {
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
       /*  val currentTime = System.currentTimeMillis()
         val remainingTime =  itemList[position].alarmTime - currentTime

         val hours = TimeUnit.MILLISECONDS.toHours(remainingTime)
         val minutes = TimeUnit.MILLISECONDS.toMinutes(remainingTime) % 60
        itemList[position].alarmTimeLeft=*/
        holder.bind(position, itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(
        private val context: Context,
        private val binding: ItemAlarmlistBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
            item: AlarmUI,
        ) {
            binding.alarmSwitch.setOnCheckedChangeListener(null)//Important shit in some cases could lead to unnecesary aditional fake calls
            binding.alarmTime.text = item.alarmTime
            binding.alarmSwitch.isChecked = item.enabled
            binding.alarmSwitch.setOnCheckedChangeListener { _, isChecked ->
                onItemOnCheckedChange(item.copy(enabled = isChecked))
            }
            binding.alarmTimeLeft.text = context.resources.getString(
                com.yes.coreui.R.string.time_left,
                item.alarmHourLeft,
                item.alarmMinutesLeft
            )
            binding.alarmRepeatDays.text=formatDaysOfWeek(item.daysOfWeek,context)

        }
    }
    private fun formatDaysOfWeek(selectedDays: Set<Int>, context: Context): String {
        val daysArray = context.resources.getStringArray(com.yes.coreui.R.array.days_array)
        val dayAbbreviations = daysArray.map { it.substring(0, 3) } // Получаем сокращенные названия дней недели

        val selectedAbbreviations = selectedDays.map { dayOfWeek ->
            dayAbbreviations[dayOfWeek - 1] // Выбираем сокращенные названия для выбранных дней
        }

        return selectedAbbreviations.joinToString(", ") // Соединяем сокращенные названия дней через запятую
    }
}