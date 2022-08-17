package com.example.heysrealprojcet.ui.channel.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.heysrealprojcet.databinding.ChannelRecruitmentMethodDialogBinding
import com.example.heysrealprojcet.enums.ChannelRecruitmentMethod
import com.example.heysrealprojcet.ui.channel.dialog.viewModel.ChannelRecruitmentMethodDialogViewModel

class ChannelRecruitmentMethodDialog(private val context: Context, private val viewModel: ChannelRecruitmentMethodDialogViewModel) {
   private var binding: ChannelRecruitmentMethodDialogBinding = ChannelRecruitmentMethodDialogBinding.inflate(LayoutInflater.from(context))
   private val dialog = Dialog(context)
   private lateinit var listener: ChannelRecruitmentMethodDialogOnClickListener

   init {
      binding.vm = viewModel

      val button = when (viewModel.btnText) {
         ChannelRecruitmentMethod.Decide.method -> {
            binding.btnDecide
         }
         else -> {
            binding.btnApproval
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
      this.listener = object : ChannelRecruitmentMethodDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelRecruitmentMethodDialogOnClickListener {
      fun onClick(content: String)
   }
}