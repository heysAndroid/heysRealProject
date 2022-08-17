package com.example.heysrealprojcet.ui.channel.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.heysrealprojcet.databinding.ChannelJoinDialogBinding

class ChannelCapacityDialog(private val context: Context) {
   private var binding: ChannelJoinDialogBinding = ChannelJoinDialogBinding.inflate(LayoutInflater.from(context))
   private val dialog = Dialog(context)
   private lateinit var listener: ChannelCapacityDialogOnClickListener

   fun show() {
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog.setContentView(binding.root)
      dialog.setCancelable(false)
      dialog.show()

      binding.btnSave.setOnClickListener {
         // TODO
         //listener.onClick(viewModel.btnText)
         dialog.dismiss()
      }
   }

   fun setOnOKClickListener(listener: (String) -> Unit) {
      this.listener = object : ChannelCapacityDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelCapacityDialogOnClickListener {
      fun onClick(content: String)
   }
}