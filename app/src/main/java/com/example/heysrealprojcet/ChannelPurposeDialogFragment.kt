package com.example.heysrealprojcet

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.heysrealprojcet.databinding.ChannelPurposeFragmentDialogBinding

class ChannelPurposeDialog(private val context: Context, private val viewModel: ChannelPurposeDialogViewModel) {
   private lateinit var binding: ChannelPurposeFragmentDialogBinding
   private val dialog = Dialog(context)
   private lateinit var listener: ChannelPurposeDialogOnClickListener

   fun show() {
      binding = ChannelPurposeFragmentDialogBinding.inflate(LayoutInflater.from(context))
      binding.vm = viewModel
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog.setContentView(binding.root)
      dialog.setCancelable(false)
      dialog.show()

      binding.btnSave.setOnClickListener {
         listener.onClick(viewModel.btnText)
         dialog.dismiss()
      }
   }

   fun setOnOKClickListener(listener: (String) -> Unit) {
      this.listener = object : ChannelPurposeDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelPurposeDialogOnClickListener {
      fun onClick(content: String)
   }
}