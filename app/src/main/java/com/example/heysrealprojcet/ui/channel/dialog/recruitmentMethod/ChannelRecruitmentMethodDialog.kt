package com.example.heysrealprojcet.ui.channel.dialog.recruitmentMethod

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.Window
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.example.heysrealprojcet.databinding.ChannelRecruitmentMethodDialogBinding
import com.example.heysrealprojcet.enums.ChannelRecruitmentMethod


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

      binding.btnApproval.text = buildSpannedString {
         bold { append("바로 승인" + "\n") }
         append("신청과 동시에 채널 입장이 가능합니다.")
      }
      var builder = SpannableStringBuilder(binding.btnApproval.text)
      builder.setSpan(RelativeSizeSpan(1.6f), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      builder.setSpan(RelativeSizeSpan(1.4f), 6, binding.btnApproval.text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      binding.btnApproval.text = builder

      binding.btnDecide.text = buildSpannedString {
         bold { append("승인 결정" + "\n") }
         append("신청자의 프로필을 보고 참여를 결정합니다.")
      }
      builder = SpannableStringBuilder(binding.btnDecide.text)
      builder.setSpan(RelativeSizeSpan(1.6f), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      builder.setSpan(RelativeSizeSpan(1.4f), 6, binding.btnDecide.text.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
      binding.btnDecide.text = builder
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