package com.example.heys

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceSize: Int, private val itemSize: Int) : RecyclerView.ItemDecoration() {
   override fun getItemOffsets(
      outRect: Rect, view: View,
      parent: RecyclerView,
      state: RecyclerView.State
   ) {
      if(parent.getChildAdapterPosition(view) != itemSize) {
         outRect.right = spaceSize
      }
   }
}