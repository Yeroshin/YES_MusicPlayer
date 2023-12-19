package com.yes.alarmclockfeature.presentation.ui.datepicker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yes.alarmclockfeature.R
import java.util.Collections


class DatePickerAdapter(
    private val onListEnded: (count: Int) -> Unit
) : RecyclerView.Adapter<DatePickerAdapter.ViewHolder>() {

    private var items = mutableListOf<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        /* val binding = DatePickerItemBinding
             .inflate(LayoutInflater.from(parent.context), parent, false)*/

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.date_picker_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
    fun getItem(position:Int):Int{
        return items[position]
    }

    fun setItems(items: List<Int>) {
        this.items.clear()
        this.items.addAll(items)
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.text)

        fun bind(value: Int) {
            textView.text = value.toString()
        }
    }
}