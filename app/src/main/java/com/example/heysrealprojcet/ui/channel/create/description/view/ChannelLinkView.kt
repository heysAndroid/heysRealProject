package com.example.heysrealprojcet.ui.channel.create.description.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.heysrealprojcet.databinding.ChannelLinkViewBinding

class ChannelLinkView : LinearLayout {
   private lateinit var binding: ChannelLinkViewBinding

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
      binding = ChannelLinkViewBinding.inflate(LayoutInflater.from(context), this, true)
   }
}