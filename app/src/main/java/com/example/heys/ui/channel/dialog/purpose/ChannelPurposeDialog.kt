package com.example.heys.ui.channel.dialog.purpose

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
import com.example.heys.databinding.ChannelPurposeDialogBinding
import com.example.heys.enums.ChannelPurpose
import com.example.heys.util.ChannelPreference

class ChannelPurposeDialog : DialogFragment() {
   private lateinit var binding: ChannelPurposeDialogBinding
   private val viewModel by viewModels<ChannelPurposeDialogViewModel>()
   private lateinit var listener: ChannelPurposeDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPurposeDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
      dialog?.setCancelable(false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.selectedPurpose.observe(viewLifecycleOwner) { array ->
         ChannelPreference.channelPurposeArray = array

         unselectAllButton()
         array.forEach {
            when (it) {
               ChannelPurpose.Capability.purpose -> selectCapabilityButton()
               ChannelPurpose.Networking.purpose -> selectNetworkingButton()
               ChannelPurpose.JobSeeking.purpose -> selectJobSeekingButton()
               ChannelPurpose.Skill.purpose -> selectSkillButton()
               ChannelPurpose.Experience.purpose -> selectExperienceButton()
               else -> selectPortfolioButton()
            }
         }
      }

      binding.btnSave.setOnClickListener {
         // 채널 정보 fragment 로 선택값 전달
         if (viewModel.selectedNum.value == 1) {
            listener.onClick("${viewModel.selectedPurpose.value?.first()}")
         } else {
            listener.onClick("${viewModel.selectedPurpose.value?.first()} 외 1개")
         }
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }
   }

   private fun selectCapabilityButton() {
      binding.btnCapability.isSelected = true
      binding.btnCapability.setTypeface(null, Typeface.BOLD)
   }

   private fun selectNetworkingButton() {
      binding.btnNetworking.isSelected = true
      binding.btnNetworking.setTypeface(null, Typeface.BOLD)
   }

   private fun selectJobSeekingButton() {
      binding.btnJobSeeking.isSelected = true
      binding.btnJobSeeking.setTypeface(null, Typeface.BOLD)
   }

   private fun selectSkillButton() {
      binding.btnSkill.isSelected = true
      binding.btnSkill.setTypeface(null, Typeface.BOLD)
   }

   private fun selectExperienceButton() {
      binding.btnExperience.isSelected = true
      binding.btnExperience.setTypeface(null, Typeface.BOLD)
   }

   private fun selectPortfolioButton() {
      binding.btnPortfolio.isSelected = true
      binding.btnPortfolio.setTypeface(null, Typeface.BOLD)
   }

   private fun unselectAllButton() {
      with(binding) {
         btnCapability.isSelected = false
         btnCapability.setTypeface(null, Typeface.NORMAL)

         btnNetworking.isSelected = false
         btnNetworking.setTypeface(null, Typeface.NORMAL)

         btnJobSeeking.isSelected = false
         btnJobSeeking.setTypeface(null, Typeface.NORMAL)

         btnSkill.isSelected = false
         btnSkill.setTypeface(null, Typeface.NORMAL)

         btnExperience.isSelected = false
         btnExperience.setTypeface(null, Typeface.NORMAL)

         btnPortfolio.isSelected = false
         btnPortfolio.setTypeface(null, Typeface.NORMAL)
      }
   }

   private fun previousSelectedPurpose() {
      ChannelPreference.channelPurposeArray.forEach {
         when (it) {
            ChannelPurpose.Skill.purpose -> selectSkillButton()
            ChannelPurpose.Networking.purpose -> selectNetworkingButton()
            ChannelPurpose.JobSeeking.purpose -> selectJobSeekingButton()
            ChannelPurpose.Capability.purpose -> selectCapabilityButton()
            ChannelPurpose.Experience.purpose -> selectExperienceButton()
            ChannelPurpose.Portfolio.purpose -> selectPortfolioButton()
            else -> {}
         }
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