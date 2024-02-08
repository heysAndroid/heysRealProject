package com.hey.heys.ui.channel.dialog.capacity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.hey.heys.R
import com.hey.heys.databinding.ChannelCapacityDialogBinding
import com.hey.heys.util.ChannelPreference

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

      // Edittext 배경 변경
      viewModel.isEnabled.observe(this) {
         if (it) {
            setEditTextEnabled()
         } else {
            if (viewModel.capacity.value == "") {
               setEditTextEnabled()
            } else {
               setEditTextDisabled()
            }
         }
      }

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

   private fun setEditTextEnabled() {
      binding.capacityEdittext.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edittext)
      binding.capacityRangeText.visibility = View.GONE
   }

   private fun setEditTextDisabled() {
      binding.capacityEdittext.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edittext_disabled)
      binding.capacityRangeText.visibility = View.VISIBLE
   }
}