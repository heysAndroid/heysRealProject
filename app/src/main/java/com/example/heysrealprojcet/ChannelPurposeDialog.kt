package com.example.heysrealprojcet

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.heysrealprojcet.databinding.ChannelPurposeDialogBinding
import com.example.heysrealprojcet.enums.ChannelPurpose

class ChannelPurposeDialog(private val context: Context, private val viewModel: ChannelPurposeDialogViewModel) {
   private lateinit var binding: ChannelPurposeDialogBinding
   private val dialog = Dialog(context)
   private lateinit var listener: ChannelPurposeDialogOnClickListener

   init {
      binding = ChannelPurposeDialogBinding.inflate(LayoutInflater.from(context))
      binding.vm = viewModel

      val button = when (viewModel.btnText) {
         ChannelPurpose.Capability.purpose -> {
            binding.btnCapability
         }
         ChannelPurpose.Skill.purpose -> {
            binding.btnSkill
         }
         ChannelPurpose.Foundation.purpose -> {
            binding.btnExperience
         }
         else -> {
            binding.btnPortfolio
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