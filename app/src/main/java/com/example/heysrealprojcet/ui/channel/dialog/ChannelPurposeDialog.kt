package com.example.heysrealprojcet.ui.channel.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import com.example.heysrealprojcet.databinding.ChannelPurposeDialogBinding
import com.example.heysrealprojcet.enums.ChannelPurpose
import com.example.heysrealprojcet.ui.channel.dialog.viewModel.ChannelPurposeDialogViewModel

class ChannelPurposeDialog(private val context: Context, private val viewModel: ChannelPurposeDialogViewModel) {
   private var binding: ChannelPurposeDialogBinding = ChannelPurposeDialogBinding.inflate(LayoutInflater.from(context))
   private val dialog = Dialog(context)
   private lateinit var listener: ChannelPurposeDialogOnClickListener

   init {
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
      button.setTypeface(null, Typeface.BOLD)
      viewModel.selectedView = button
   }

   fun show() {
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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