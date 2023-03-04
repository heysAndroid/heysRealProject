package com.example.heysrealprojcet.ui.user.myPage.edit

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.heysrealprojcet.databinding.AdditionalLinkViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdditionalLinkView : LinearLayout {
   private lateinit var binding: AdditionalLinkViewBinding

   constructor(context: Context) : super(context) {
      init()
   }

   constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
      init()
   }

   constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
      init()
   }

   private fun init() {
      binding = AdditionalLinkViewBinding.inflate(LayoutInflater.from(context), this, true)
   }

   fun setHint(hint: String) {
      binding.linkField.hint = hint
   }

   fun removeButtonClickListener(listener: () -> Unit) {
      binding.removeButton.setOnClickListener { listener.invoke() }
   }
}