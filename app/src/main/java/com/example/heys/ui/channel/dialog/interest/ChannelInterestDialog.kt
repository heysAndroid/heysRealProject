package com.example.heys.ui.channel.dialog.interest

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heys.databinding.ChannelInterestDialogBinding
import com.example.heys.enums.ChannelInterest
import com.example.heys.enums.ChannelThumbnailUrl
import com.example.heys.util.ChannelPreference

class ChannelInterestDialog : DialogFragment() {
   private lateinit var binding: ChannelInterestDialogBinding
   private val viewModel by viewModels<ChannelInterestDialogViewModel>()
   private lateinit var listener: ChannelInterestDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelInterestDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
      dialog?.setCancelable(false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.selectedInterest.observe(viewLifecycleOwner) { array ->
         ChannelPreference.channelInterestArray = array

         if (array.isNotEmpty()) {
            setThumbnailUrl(array.first())
         }

         unselectAllButton()
         array.forEach {
            when (it) {
               ChannelInterest.Planning.interest -> selectButton(binding.btnPlanning)
               ChannelInterest.Design.interest -> selectButton(binding.btnDesign)
               ChannelInterest.Programming.interest -> selectButton(binding.btnProgramming)
               ChannelInterest.IT.interest -> selectButton(binding.btnIT)
               ChannelInterest.Data.interest -> selectButton(binding.btnData)
               ChannelInterest.Game.interest -> selectButton(binding.btnGame)
               ChannelInterest.Marketing.interest -> selectButton(binding.btnMarketing)
               ChannelInterest.Business.interest -> selectButton(binding.btnBusiness)
               ChannelInterest.Economics.interest -> selectButton(binding.btnEconomics)
               ChannelInterest.Engineering.interest -> selectButton(binding.btnEngineering)
               ChannelInterest.Art.interest -> selectButton(binding.btnArt)
               ChannelInterest.Novel.interest -> selectButton(binding.btnNovel)
               ChannelInterest.Lifestyle.interest -> selectButton(binding.btnLifestyle)
               ChannelInterest.Picture.interest -> selectButton(binding.btnPicture)
               ChannelInterest.Culture.interest -> selectButton(binding.btnCulture)
               ChannelInterest.Travel.interest -> selectButton(binding.btnTravel)
               ChannelInterest.Environment.interest -> selectButton(binding.btnEnvironment)
               ChannelInterest.Language.interest -> selectButton(binding.btnLanguage)
               ChannelInterest.MediaContents.interest -> selectButton(binding.btnMediaContents)
               ChannelInterest.Paper.interest -> selectButton(binding.btnPaper)
               ChannelInterest.Sports.interest -> selectButton(binding.btnSports)
               ChannelInterest.Dance.interest -> selectButton(binding.btnDance)
               ChannelInterest.Service.interest -> selectButton(binding.btnPublic)
               else -> {}
            }
         }
      }

      binding.btnSave.setOnClickListener {
         val size = ChannelPreference.channelInterestArray.size
         if (size == 1) {
            listener.onClick("${ChannelPreference.channelInterestArray[0]}")
         } else {
            listener.onClick("${ChannelPreference.channelInterestArray[0]} 외 ${size - 1}개")
         }
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }
   }

   private fun selectButton(view: View) {
      val button = view as Button
      button.isSelected = true
      button.setTypeface(null, Typeface.BOLD)
   }

   private fun unselectButton(view: View) {
      val button = view as Button
      button.isSelected = false
      button.setTypeface(null, Typeface.NORMAL)
   }

   private fun unselectAllButton() {
      unselectButton(binding.btnPlanning)
      unselectButton(binding.btnDesign)
      unselectButton(binding.btnProgramming)
      unselectButton(binding.btnIT)
      unselectButton(binding.btnData)
      unselectButton(binding.btnGame)
      unselectButton(binding.btnMarketing)
      unselectButton(binding.btnBusiness)
      unselectButton(binding.btnEconomics)
      unselectButton(binding.btnEngineering)
      unselectButton(binding.btnArt)
      unselectButton(binding.btnNovel)
      unselectButton(binding.btnLifestyle)
      unselectButton(binding.btnPicture)
      unselectButton(binding.btnCulture)
      unselectButton(binding.btnTravel)
      unselectButton(binding.btnEnvironment)
      unselectButton(binding.btnLanguage)
      unselectButton(binding.btnMediaContents)
      unselectButton(binding.btnPaper)
      unselectButton(binding.btnSports)
      unselectButton(binding.btnDance)
      unselectButton(binding.btnPublic)
   }

   private fun setThumbnailUrl(interest: String) {
      when (interest) {
         ChannelInterest.Service.interest,
         ChannelInterest.Planning.interest,
         ChannelInterest.Novel.interest,
         ChannelInterest.Paper.interest,
         ChannelInterest.Culture.interest,
         ChannelInterest.Engineering.interest,
         ChannelInterest.Environment.interest -> ChannelPreference.channelThumbnailUrl = ChannelThumbnailUrl.MainGreen.url

         ChannelInterest.MediaContents.interest,
         ChannelInterest.Programming.interest,
         ChannelInterest.Design.interest,
         ChannelInterest.Art.interest,
         ChannelInterest.Picture.interest,
         ChannelInterest.Game.interest -> ChannelPreference.channelThumbnailUrl = ChannelThumbnailUrl.MainYellow.url

         ChannelInterest.Language.interest,
         ChannelInterest.Lifestyle.interest,
         ChannelInterest.Travel.interest -> ChannelPreference.channelThumbnailUrl = ChannelThumbnailUrl.MainBlue.url

         ChannelInterest.Marketing.interest,
         ChannelInterest.Data.interest,
         ChannelInterest.IT.interest -> ChannelPreference.channelThumbnailUrl = ChannelThumbnailUrl.SubGreen.url

         ChannelInterest.Economics.interest,
         ChannelInterest.Business.interest -> ChannelPreference.channelThumbnailUrl = ChannelThumbnailUrl.MainPurple.url

         ChannelInterest.Sports.interest,
         ChannelInterest.Dance.interest -> ChannelPreference.channelThumbnailUrl = ChannelThumbnailUrl.MainRed.url
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