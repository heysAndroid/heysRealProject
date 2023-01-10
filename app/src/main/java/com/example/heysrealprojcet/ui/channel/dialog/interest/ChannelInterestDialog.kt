package com.example.heysrealprojcet.ui.channel.dialog.interest

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelInterestDialogBinding
import com.example.heysrealprojcet.enums.ChannelInterest
import com.example.heysrealprojcet.util.ChannelPreference

class ChannelInterestDialog : DialogFragment() {
   private lateinit var binding: ChannelInterestDialogBinding
   private val viewModel by viewModels<ChannelInterestDialogViewModel>()
   private lateinit var listener: ChannelInterestDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelInterestDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.setCancelable(false)

      previousSelectedInterest()
      binding.selectText.text = "${ChannelPreference.channelInterest.size}/3"

      viewModel.selectedInterest.observe(viewLifecycleOwner) {
         when (it) {
            ChannelInterest.Planning.interest -> {
               if (!binding.btnPlanning.isSelected && ChannelPreference.channelInterest.size != 3) selectPlanningButton()
               else unselectPlanningButton()
            }
            ChannelInterest.Design.interest -> {
               if (!binding.btnDesign.isSelected && ChannelPreference.channelInterest.size != 3) selectDesignButton()
               else unselectDesignButton()
            }
            ChannelInterest.It.interest -> {
               if (!binding.btnIt.isSelected && ChannelPreference.channelInterest.size != 3) selectItButton()
               else unselectItButton()
            }
            ChannelInterest.Economics.interest -> {
               if (!binding.btnEconomics.isSelected && ChannelPreference.channelInterest.size != 3) selectEconomicsButton()
               else unselectEconomicsButton()
            }
            ChannelInterest.Data.interest -> {
               if (!binding.btnData.isSelected && ChannelPreference.channelInterest.size != 3) selectDataButton()
               else unselectDataButton()
            }
            ChannelInterest.Language.interest -> {
               if (!binding.btnLanguage.isSelected && ChannelPreference.channelInterest.size != 3) selectLanguageButton()
               else unselectLanguageButton()
            }
            ChannelInterest.Education.interest -> {
               if (!binding.btnEducation.isSelected && ChannelPreference.channelInterest.size != 3) selectEducationButton()
               else unselectEducationButton()
            }
            ChannelInterest.Public.interest -> {
               if (!binding.btnPublic.isSelected && ChannelPreference.channelInterest.size != 3) selectPublicButton()
               else unselectPublicButton()
            }
            ChannelInterest.Business.interest -> {
               if (!binding.btnBusiness.isSelected && ChannelPreference.channelInterest.size != 3) selectBusinessButton()
               else unselectBusinessButton()
            }
            ChannelInterest.Engineering.interest -> {
               if (!binding.btnEngineering.isSelected && ChannelPreference.channelInterest.size != 3) selectEngineeringButton()
               else unselectEngineeringButton()
            }
            ChannelInterest.MediaContents.interest -> {
               if (!binding.btnMediaContents.isSelected && ChannelPreference.channelInterest.size != 3) selectMediaContentsButton()
               else unselectMediaContentsButton()
            }
            ChannelInterest.Marketing.interest -> {
               if (!binding.btnMarketing.isSelected && ChannelPreference.channelInterest.size != 3) selectMarketingButton()
               else unselectMarketingButton()
            }
            ChannelInterest.Etc.interest -> {
               if (!binding.btnEtc.isSelected && ChannelPreference.channelInterest.size != 3) selectEtcButton()
               else unselectEtcButton()
            }
            else -> {}
         }
      }

      viewModel.isSelected.observe(viewLifecycleOwner) {
         when (it) {
            true -> ChannelPreference.channelInterest.add(viewModel.selectedInterest.value.toString())
            false -> ChannelPreference.channelInterest.remove(viewModel.selectedInterest.value.toString())
         }
         binding.selectText.text = "${ChannelPreference.channelInterest.size}/3"
         Log.e("태그", ChannelPreference.channelInterest.toString())
      }

      binding.btnSave.setOnClickListener {
         when (ChannelPreference.channelInterest.size) {
            1 -> listener.onClick("${ChannelPreference.channelInterest[0]}")
            2 -> listener.onClick("${ChannelPreference.channelInterest[0]} 외 1개")
            3 -> listener.onClick("${ChannelPreference.channelInterest[0]} 외 2개")
         }
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }

      return binding.root
   }

   private fun unselectBusinessButton() {
      binding.btnBusiness.isSelected = false
      binding.btnBusiness.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectPublicButton() {
      binding.btnPublic.isSelected = false
      binding.btnPublic.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectEducationButton() {
      binding.btnEducation.isSelected = false
      binding.btnEducation.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectLanguageButton() {
      binding.btnLanguage.isSelected = false
      binding.btnLanguage.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectDataButton() {
      binding.btnData.isSelected = false
      binding.btnData.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectEngineeringButton() {
      binding.btnEngineering.isSelected = false
      binding.btnEngineering.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectMediaContentsButton() {
      binding.btnMediaContents.isSelected = false
      binding.btnMediaContents.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectMarketingButton() {
      binding.btnMarketing.isSelected = false
      binding.btnMarketing.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectEtcButton() {
      binding.btnEtc.isSelected = false
      binding.btnEtc.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectEconomicsButton() {
      binding.btnEconomics.isSelected = false
      binding.btnEconomics.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectItButton() {
      binding.btnIt.isSelected = false
      binding.btnIt.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectDesignButton() {
      binding.btnDesign.isSelected = false
      binding.btnDesign.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectPlanningButton() {
      binding.btnPlanning.isSelected = false
      binding.btnPlanning.setTypeface(null, Typeface.NORMAL)
   }

   private fun selectEtcButton() {
      binding.btnEtc.isSelected = true
      binding.btnEtc.setTypeface(null, Typeface.BOLD)
   }

   private fun selectMarketingButton() {
      binding.btnMarketing.isSelected = true
      binding.btnMarketing.setTypeface(null, Typeface.BOLD)
   }

   private fun selectMediaContentsButton() {
      binding.btnMediaContents.isSelected = true
      binding.btnMediaContents.setTypeface(null, Typeface.BOLD)
   }

   private fun selectEngineeringButton() {
      binding.btnEngineering.isSelected = true
      binding.btnEngineering.setTypeface(null, Typeface.BOLD)
   }

   private fun selectBusinessButton() {
      binding.btnBusiness.isSelected = true
      binding.btnBusiness.setTypeface(null, Typeface.BOLD)
   }

   private fun selectPublicButton() {
      binding.btnPublic.isSelected = true
      binding.btnPublic.setTypeface(null, Typeface.BOLD)
   }

   private fun selectEducationButton() {
      binding.btnEducation.isSelected = true
      binding.btnEducation.setTypeface(null, Typeface.NORMAL)
   }

   private fun selectLanguageButton() {
      binding.btnLanguage.isSelected = true
      binding.btnLanguage.setTypeface(null, Typeface.NORMAL)
   }

   private fun selectDataButton() {
      binding.btnData.isSelected = true
      binding.btnData.setTypeface(null, Typeface.NORMAL)
   }

   private fun selectEconomicsButton() {
      binding.btnEconomics.isSelected = true
      binding.btnEconomics.setTypeface(null, Typeface.BOLD)
   }

   private fun selectItButton() {
      binding.btnIt.isSelected = true
      binding.btnIt.setTypeface(null, Typeface.BOLD)
   }

   private fun selectDesignButton() {
      binding.btnDesign.isSelected = true
      binding.btnDesign.setTypeface(null, Typeface.BOLD)
   }

   private fun selectPlanningButton() {
      binding.btnPlanning.isSelected = true
      binding.btnPlanning.setTypeface(null, Typeface.BOLD)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
   }

   private fun previousSelectedInterest() {
      for (i in ChannelPreference.channelInterest.indices) {
         when (ChannelPreference.channelInterest[i]) {
            ChannelInterest.Planning.interest -> selectPlanningButton()
            ChannelInterest.Design.interest -> selectDesignButton()
            ChannelInterest.It.interest -> selectItButton()
            ChannelInterest.Economics.interest -> selectEconomicsButton()
            ChannelInterest.Data.interest -> selectDataButton()
            ChannelInterest.Language.interest -> selectLanguageButton()
            ChannelInterest.Education.interest -> selectEducationButton()
            ChannelInterest.Public.interest -> selectPublicButton()
            ChannelInterest.Business.interest -> selectBusinessButton()
            ChannelInterest.Engineering.interest -> selectEngineeringButton()
            ChannelInterest.MediaContents.interest -> selectMediaContentsButton()
            ChannelInterest.Marketing.interest -> selectMarketingButton()
            ChannelInterest.Etc.interest -> selectEtcButton()
            else -> {}
         }
      }
   }

   fun setOnOKClickListener(listener: (String) -> Unit) {
      this.listener = object : ChannelInterestDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelInterestDialogOnClickListener {
      fun onClick(content: String)
   }
}