package com.example.heysrealprojcet.ui.user.myPage.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.BookmarkEditItemViewBinding
import com.example.heysrealprojcet.model.BookmarkData

class BookmarkEditRecyclerViewAdapter(
   private val viewModel: BookmarkEditViewModel,
   private val list: MutableList<BookmarkData>
) : RecyclerView.Adapter<BookmarkEditRecyclerViewAdapter.ViewHolder>() {
   private lateinit var binding: BookmarkEditItemViewBinding

   inner class ViewHolder(private val binding: BookmarkEditItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(item: BookmarkData) {
         binding.image.setImageResource(item.image)
         binding.title.text = item.title
         binding.content.text = item.content
         binding.see.text = item.see.toString()

         binding.imageFrameLayout.setOnClickListener {
            if (binding.imageFrameLayout.isSelected) {
               binding.imageFrameLayout.isSelected = false
               viewModel.removeSelectedBookmark(item)
            } else {
               binding.imageFrameLayout.isSelected = true
               viewModel.addSelectedBookmark(item)
            }
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      binding = BookmarkEditItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ViewHolder(binding)
   }

   override fun getItemCount() = list.size

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(list[position])
   }
}