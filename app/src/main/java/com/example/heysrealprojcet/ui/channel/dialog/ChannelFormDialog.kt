package com.example.heysrealprojcet.ui.channel.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.heysrealprojcet.databinding.ChannelFormDialogBinding
import com.example.heysrealprojcet.enums.ChannelForm
import com.example.heysrealprojcet.ui.channel.dialog.viewModel.ChannelFormDialogViewModel

class ChannelFormDialog(private val context: Context, private val viewModel: ChannelFormDialogViewModel) {
   private var binding: ChannelFormDialogBinding = ChannelFormDialogBinding.inflate(LayoutInflater.from(context))
   private val dialog = Dialog(context)
   private lateinit var listener: ChannelFormDialogOnClickListener

   init {
      binding.vm = viewModel

      val button = when (viewModel.btnText) {
         ChannelForm.Offline.form -> {
            binding.btnOffline
         }
         ChannelForm.Online.form -> {
            binding.btnOnline
         }
         else -> {
            binding.btnBoth
         }
      }
      button.isSelected = true
      viewModel.selectedView = button
   }

   fun show() {
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
      this.listener = object : ChannelFormDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelFormDialogOnClickListener {
      fun onClick(content: String)
   }
}