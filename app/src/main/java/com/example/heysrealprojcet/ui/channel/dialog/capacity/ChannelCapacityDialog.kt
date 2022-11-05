package com.example.heysrealprojcet.ui.channel.dialog.capacity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelCapacityDialogBinding
import com.example.heysrealprojcet.util.ChannelPreference

class ChannelCapacityDialog : DialogFragment() {
   private lateinit var binding: ChannelCapacityDialogBinding
   private val viewModel by viewModels<ChannelCapacityDialogViewModel>()
   private lateinit var listener: ChannelCapacityDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelCapacityDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.setCancelable(false)

      binding.btnSave.setOnClickListener {
         viewModel.capacity.value?.toInt()?.let { ChannelPreference.channelCapacity = it }
         viewModel.capacity.value?.let { listener.onClick(it) }
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }

      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
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