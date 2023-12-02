package com.yes.core.presentation

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs


class ItemTouchHelperCallback(
    private val enableSwipeToDelete: Boolean,
    private val enableDragAndDrop: Boolean,
    private val onSwipeCallback: (position: Int) -> Unit,
    private val onItemMove: (fromPosition: Int, toPosition: Int) -> Unit,
    private val deleteIconDrawable: Drawable?,
    private val deleteIconColor: Int,
    private val onDraggedItemDrop: (fromPosition: Int, toPosition: Int) -> Unit
) : ItemTouchHelper.Callback() {
    private var dragFromPosition = -1
    private var dragToPosition = -1
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
        val swipeFlags = ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        viewHolder.itemView.elevation = 16F
        val dragFromPosition = viewHolder.bindingAdapterPosition
        dragToPosition = target.bindingAdapterPosition
        onItemMove(dragFromPosition, dragToPosition)
        return true
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        when (actionState) {
            ItemTouchHelper.ACTION_STATE_DRAG -> {
                dragFromPosition = viewHolder?.bindingAdapterPosition?:-1
                viewHolder?.itemView?.let {
                    it.x = it.x+it.height/2
                }
            }

            ItemTouchHelper.ACTION_STATE_IDLE -> {
                if (dragFromPosition != -1 && dragToPosition != -1 && dragFromPosition != dragToPosition) {
                    onDraggedItemDrop(dragFromPosition, dragToPosition)
                    dragFromPosition = -1
                    dragToPosition = -1
                }
            }
        }
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition
        onSwipeCallback(position)
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
        if (actionState === ItemTouchHelper.ACTION_STATE_SWIPE) {
            val width = viewHolder.itemView.width.toFloat()
            val alpha = 1.0f - abs(dX) / width
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(
                canvas, recyclerView, viewHolder, dX, dY,
                actionState, isCurrentlyActive
            )
        }
        deleteIconDrawable?.let {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                val itemView = viewHolder.itemView
                val deleteIconMargin = (itemView.height - deleteIconDrawable.intrinsicHeight) / 2
                val deleteIconTop = itemView.top + deleteIconMargin
                val deleteIconBottom = deleteIconTop + deleteIconDrawable.intrinsicHeight
              /*  val background = colordrawable()
                val backgroundcolor = color.parsecolor("#f44336")
                background.color = backgroundcolor
                background.setbounds(itemview.right + dx.toint(), itemview.top, itemview.right, itemview.bottom)
                canvas.drawColor(Color.GRAY)
                background.draw(c)*/
                if (dX > 0) {
                    val deleteIconLeft = itemView.left + deleteIconMargin
                    val deleteIconRight = deleteIconLeft + deleteIconDrawable.intrinsicWidth
                    deleteIconDrawable.setBounds(
                        deleteIconLeft,
                        deleteIconTop,
                        deleteIconRight,
                        deleteIconBottom
                    )
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    deleteIconDrawable.colorFilter = BlendModeColorFilter(deleteIconColor, BlendMode.SRC_ATOP)
                } else {
                    deleteIconDrawable.setColorFilter(deleteIconColor, PorterDuff.Mode.SRC_ATOP)
                }
                deleteIconDrawable.draw(canvas)
            }
        }

    }

    override fun clearView(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) {
        val toPos = viewHolder.bindingAdapterPosition
        // onDraggedItemDrop(0, toPos)
        Log.e("DragDrop Example", "Unknown action type received by View.OnDragListener.")
    }

}
