package com.example.heysrealprojcet.ui.channel.dialog.recruitmentMethod

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelRecruitmentMethodDialogBinding
import com.example.heysrealprojcet.enums.ChannelRecruitmentMethod
import com.example.heysrealprojcet.util.ChannelPreference

class ChannelRecruitmentMethodDialog : DialogFragment() {
   private lateinit var binding: ChannelRecruitmentMethodDialogBinding
   private val viewModel by viewModels<ChannelRecruitmentMethodDialogViewModel>()
   private lateinit var listener: ChannelRecruitmentMethodDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelRecruitmentMethodDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.setCancelable(false)

      setTextSize()
      viewModel.selectedRecruitmentMethod.observe(viewLifecycleOwner) {
         unselectAllRecruitMethodButton()
         when (it) {
            ChannelRecruitmentMethod.Decide.method -> selectDecideButton()
            else -> selectApprovalButton()
         }
      }
      binding.btnSave.setOnClickListener {
         ChannelPreference.channelRecruitmentMethod = viewModel.selectedRecruitmentMethod.value.toString()
         listener.onClick(ChannelPreference.channelRecruitmentMethod)
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }
      return binding.root
   }

   private fun selectDecideButton() {
      binding.btnDecide.text = buildSpannedString {
         bold { append("승인 결정" + "\n") }
         append("신청자의 프로필을 보고 참여를 결정합니다.")
      }
      binding.btnDecide.isSelected = true
      setTextSize()
   }

   private fun selectApprovalButton() {
      binding.btnApproval.text = buildSpannedString {
         bold { append("바로 승인" + "\n") }
         append("신청과 동시에 채널 입장이 가능합니다.")
      }
      binding.btnApproval.isSelected = true
      setTextSize()
   }

   private fun setTextSize() {
      var builder = SpannableStringBuilder(binding.btnApproval.text)
      builder.setSpan(RelativeSizeSpan(1.6f), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      builder.setSpan(RelativeSizeSpan(1.4f), 6, binding.btnApproval.text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      binding.btnApproval.text = builder

      builder = SpannableStringBuilder(binding.btnDecide.text)
      builder.setSpan(RelativeSizeSpan(1.6f), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      builder.setSpan(RelativeSizeSpan(1.4f), 6, binding.btnDecide.text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      binding.btnDecide.text = builder
   }

   private fun unselectAllRecruitMethodButton() {
      with(binding) {
         btnApproval.isSelected = false
         binding.btnApproval.text = buildSpannedString {
            append("바로 승인" + "\n")
            append("신청과 동시에 채널 입장이 가능합니다.")
         }

         btnDecide.isSelected = false
         btnDecide.text = buildSpannedString {
            append("승인 결정" + "\n")
            append("신청자의 프로필을 보고 참여를 결정합니다.")
         }
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