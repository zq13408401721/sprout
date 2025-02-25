package com.sprout.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListDecoration: RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view).toInt()%2 == 0){
            outRect.set(10,10,10,10)
        }else{
            outRect.set(0,10,10,10)
        }
    }
}