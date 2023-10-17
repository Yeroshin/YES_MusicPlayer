package com.yes.core.presentation

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val enableSwipeToDelete: Boolean,
    private val enableDragAndDrop: Boolean,
    private val onSwipeCallback: (position: Int) -> Unit,
    private val onDragAndDropCallback: (fromPosition: Int, toPosition: Int) -> Boolean,
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
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        return onDragAndDropCallback.invoke(fromPosition, toPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        onSwipeCallback.invoke(position)
    }
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            val deleteIconMargin = (itemView.height - deleteIconDrawable!!.intrinsicHeight) / 2
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

            deleteIconDrawable.draw(c)
        }
    }

}
