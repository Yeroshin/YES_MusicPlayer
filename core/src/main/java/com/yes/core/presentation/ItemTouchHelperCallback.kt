package com.yes.core.presentation

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val enableSwipeToDelete: Boolean,
    private val enableDragAndDrop: Boolean,
    private val onSwipeCallback: (position: Int) -> Unit,
    private val onDragAndDropCallback: (fromPosition: Int, toPosition: Int) -> Unit,
    private val deleteIconDrawable: Drawable?
) : ItemTouchHelper.Callback() {

    override fun isItemViewSwipeEnabled(): Boolean {
        return enableSwipeToDelete
    }

    override fun isLongPressDragEnabled(): Boolean {
        return enableDragAndDrop
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.bindingAdapterPosition
        val toPosition = target.bindingAdapterPosition

         onDragAndDropCallback.invoke(fromPosition, toPosition)
        return true
    }
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder?.itemView?.translationX = 20f // Сдвигаем элемент на 20 пикселей вправо
        }
        super.onSelectedChanged(viewHolder, actionState)
    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition
        onSwipeCallback.invoke(position)
    }
    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        deleteIconDrawable?.let {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                val itemView = viewHolder.itemView
                val deleteIconMargin = (itemView.height - deleteIconDrawable.intrinsicHeight) / 2
                val deleteIconTop = itemView.top + deleteIconMargin
                val deleteIconBottom = deleteIconTop + deleteIconDrawable.intrinsicHeight

                if (dX > 0) {
                    val deleteIconLeft = itemView.left + deleteIconMargin
                    val deleteIconRight = deleteIconLeft + deleteIconDrawable.intrinsicWidth
                    deleteIconDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                } else {
                    val deleteIconRight = itemView.right - deleteIconMargin
                    val deleteIconLeft = deleteIconRight - deleteIconDrawable.intrinsicWidth
                    deleteIconDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                }
                deleteIconDrawable.draw(canvas)
            }
        }

    }

}
