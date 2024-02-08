package com.hey.heys.ui.channel.dialog.period

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.hey.heys.databinding.ChannelTimeDialogBinding
import com.hey.heys.util.ChannelPreference
import java.time.LocalTime

class ChannelTimeDialog : DialogFragment() {
   private lateinit var binding: ChannelTimeDialogBinding
   private lateinit var listener: ChannelTimeDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelTimeDialogBinding.inflate(inflater, container, false)

      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.setCancelable(false)

      binding.timePicker.setOnTimeChangedListener { _, hour, minute ->
         val dueDateTime = LocalTime.of(hour, minute)
         ChannelPreference.channelRecruitEndTime = dueDateTime.toString()
         enableSaveButton()
      }

      binding.btnSave.setOnClickListener {
         listener.onClick("ok")
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
   }

   private fun enableSaveButton() {
      binding.btnSave.isEnabled = true
   }

   fun setOnOKClickListener(listener: (String) -> Unit) {
      this.listener = object : ChannelTimeDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelTimeDialogOnClickListener {
      fun onClick(time: String)
   }
}