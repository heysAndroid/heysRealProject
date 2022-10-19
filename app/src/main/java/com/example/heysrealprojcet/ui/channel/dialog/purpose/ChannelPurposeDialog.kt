package com.example.heysrealprojcet.ui.channel.dialog.purpose

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelPurposeDialogBinding

class ChannelPurposeDialog : DialogFragment() {
   private lateinit var binding: ChannelPurposeDialogBinding
   private val viewModel by viewModels<ChannelPurposeDialogViewModel>()

   private lateinit var listener: ChannelPurposeDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPurposeDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
      binding.closeButton.setOnClickListener { dismiss() }
      viewModel.selectedPurpose.observe(viewLifecycleOwner) {}
      return binding.root
   }

   /*
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
         binding.closeButton.setOnClickListener { dialog.dismiss() }
      }
   */

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