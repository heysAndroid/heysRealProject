package com.example.heysrealprojcet.ui.channel

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.heysrealprojcet.databinding.ProfileDialogBinding

class ProfileDialog(context: Context, private val okClickListener: () -> Unit) : Dialog(context) {
   private lateinit var binding: ProfileDialogBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ProfileDialogBinding.inflate(layoutInflater)
      setContentView(binding.root)
      initView()
   }

   private fun initView() {
      with(binding) {
         setCancelable(false)
         // 배경 투명하게
         window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         closeButton.setOnClickListener { dismiss() }
         chatButton.setOnClickListener {
            okClickListener()
            dismiss()
         }
      }
   }
}