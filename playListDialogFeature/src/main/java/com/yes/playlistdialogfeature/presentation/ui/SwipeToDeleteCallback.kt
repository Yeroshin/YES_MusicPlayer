package com.yes.playlistdialogfeature.presentation.ui

import android.graphics.Canvas;
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;



class SwipeToDeleteCallback(private val adapter:SwipeToDeleteAdapter) : ItemTouchHelper.Callback() {
    interface SwipeToDeleteAdapter{
        fun deleteItem(position:Int)
    }
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // Не реализуется, так как мы не поддерживаем перемещение элементов
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.deleteItem(position)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val deleteIcon = ContextCompat.getDrawable(recyclerView.context, com.yes.coreui.R.drawable.trash_can_outline)!!
        val itemView = viewHolder.itemView
        val iconMargin = (itemView.height - iconSize) / 2
        val iconTop = itemView.top + (itemView.height - iconSize) / 2
        val iconBottom = iconTop + iconSize

        when {
            dX > 0 -> { // Swipe to right
                val iconLeft = itemView.left + iconMargin
                val iconRight = iconLeft + iconSize
                swipeBackground.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
                swipeBackground.draw(c)
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(c)
            }
            dX < 0 -> { // Swipe to left
               /* val iconRight = itemView.right - iconMargin
                val iconLeft = iconRight - iconSize
                swipeBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                swipeBackground.draw(c)
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(c)*/
            }
            else -> { // No swipe
                swipeBackground.setBounds(0, 0, 0, 0)
                deleteIcon.setBounds(0, 0, 0, 0)
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    companion object {
        private const val iconSize = 56 // Размер иконки в пикселях
        private val swipeBackground = ColorDrawable(Color.parseColor("#FF0000")) // Цвет фона при свайпе
    }
}