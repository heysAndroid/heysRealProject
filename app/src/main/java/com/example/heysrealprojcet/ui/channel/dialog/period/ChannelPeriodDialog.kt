package com.example.heysrealprojcet.ui.channel.dialog.period

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelPeriodDialogBinding
import com.example.heysrealprojcet.enums.ChannelPeriod
import com.example.heysrealprojcet.util.ChannelPreference

class ChannelPeriodDialog : DialogFragment() {
   private lateinit var binding: ChannelPeriodDialogBinding
   private val viewModel by viewModels<ChannelPeriodDialogViewModel>()
   private lateinit var listener: ChannelPeriodDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPeriodDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
      dialog?.setCancelable(false)

      previousSelectedPeriod()
      viewModel.selectedPeriod.observe(viewLifecycleOwner) {
         unselectAllButton()
         when (it) {
            ChannelPeriod.SevenDays.period -> select7DayButton()
            ChannelPeriod.OneMonth.period -> select1MonthButton()
            ChannelPeriod.ThreeMonth.period -> select3MonthButton()
            else -> select6MonthButton()
         }
      }

      binding.btnSave.setOnClickListener {
         viewModel.selectedPeriod.value?.let { ChannelPreference.channelRecruitPeriod = it }
         // 채널 정보 fragment 로 선택값 전달
         listener.onClick(ChannelPreference.channelRecruitPeriod)
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }

      return binding.root
   }

   private fun select7DayButton() {
      binding.btn7Day.isSelected = true
      binding.btn7Day.setTypeface(null, Typeface.BOLD)
   }

   private fun select1MonthButton() {
      binding.btn1Month.isSelected = true
      binding.btn1Month.setTypeface(null, Typeface.BOLD)
   }

   private fun select3MonthButton() {
      binding.btn3Month.isSelected = true
      binding.btn3Month.setTypeface(null, Typeface.BOLD)
   }

   private fun select6MonthButton() {
      binding.btn6Month.isSelected = true
      binding.btn6Month.setTypeface(null, Typeface.BOLD)
   }

   private fun unselectAllButton() {
      with(binding) {
         btn7Day.isSelected = false
         btn7Day.setTypeface(null, Typeface.NORMAL)

         btn1Month.isSelected = false
         btn1Month.setTypeface(null, Typeface.NORMAL)

         btn3Month.isSelected = false
         btn3Month.setTypeface(null, Typeface.NORMAL)

         btn6Month.isSelected = false
         btn6Month.setTypeface(null, Typeface.NORMAL)
      }
   }

   private fun previousSelectedPeriod() {
      when (ChannelPreference.channelRecruitPeriod) {
         ChannelPeriod.SevenDays.period -> select7DayButton()
         ChannelPeriod.OneMonth.period -> select1MonthButton()
         ChannelPeriod.ThreeMonth.period -> select3MonthButton()
         ChannelPeriod.SixMonth.period -> select6MonthButton()
         else -> {}
      }
   }

   fun setOnOKClickListener(listener: (String) -> Unit) {
      this.listener = object : ChannelPeriodDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelPeriodDialogOnClickListener {
      fun onClick(content: String)
   }
}