package com.example.heysrealprojcet.ui.channel.dialog.capacity

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.example.heysrealprojcet.databinding.ChannelCapacityDialogBinding

class ChannelCapacityDialog(context: Context, private val viewModel: ChannelCapacityDialogViewModel) {
   private var binding: ChannelCapacityDialogBinding = ChannelCapacityDialogBinding.inflate(LayoutInflater.from(context))
   private val dialog = Dialog(context)
   private lateinit var listener: ChannelCapacityDialogOnClickListener

   init {
      binding.vm = viewModel
   }

   fun show() {
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog.setContentView(binding.root)
      dialog.setCancelable(false)
      dialog.show()

      binding.btnSave.setOnClickListener {
         val capacityString = binding.edtText.text.toString()
         listener.onClick(capacityString)
         viewModel.updateCapacity(capacityString.toInt())
         dialog.dismiss()
      }

      binding.closeButton.setOnClickListener {
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