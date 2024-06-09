package com.hey.heys.ui.channel.dialog.form

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
import com.hey.heys.databinding.ChannelFormDialogBinding
import com.hey.heys.enums.ChannelForm
import com.hey.heys.enums.ChannelRegion
import com.hey.heys.util.ChannelPreference

class ChannelFormDialog : DialogFragment() {
   private lateinit var binding: ChannelFormDialogBinding
   private val viewModel by viewModels<ChannelFormDialogViewModel>()
   private lateinit var listener: ChannelFormDialogOnClickListener

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelFormDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.setCancelable(false)

      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      previousSelectedForm()
      previousSelectedRegion()
      viewModel.selectedForm.observe(viewLifecycleOwner) {
         unselectAllFormButton()
         when (it) {
            ChannelForm.Offline.form -> {
               selectOfflineButton()
               binding.regionContainer.visibility = View.VISIBLE
            }
            ChannelForm.Online.form -> {
               selectOnlineButton()
               binding.regionContainer.visibility = View.GONE
            }
            else -> {
               selectBothButton()
               binding.regionContainer.visibility = View.GONE
            }
         }
      }

      viewModel.selectedRegion.observe(viewLifecycleOwner) {
         unselectAllRegionButton()
         when (it) {
            ChannelRegion.Whole.region -> selectWholeButton()
            ChannelRegion.Seoul.region -> selectSeoulButton()
            ChannelRegion.Gyeonggi.region -> selectGyeonggiButton()
            ChannelRegion.Incheon.region -> selectIncheonButton()
            ChannelRegion.Gangwon.region -> selectGangwonButton()
            ChannelRegion.Chungcheong.region -> selectChungcheongButton()
            ChannelRegion.Jeolla.region -> selectJeollaButton()
            ChannelRegion.Gyeongsang.region -> selectGyeongsangButton()
            else -> selectJejuButton()
         }
      }

      binding.btnSave.setOnClickListener {
         if (viewModel.selectedForm.value != null) {
            ChannelPreference.channelForm = viewModel.selectedForm.value.toString()
            when(ChannelPreference.channelForm){
               ChannelForm.Online.form -> ChannelPreference.channelFormEng = ChannelForm.Online.engForm
               ChannelForm.Offline.form -> ChannelPreference.channelFormEng = ChannelForm.Offline.engForm
               ChannelForm.Both.form -> ChannelPreference.channelFormEng = ChannelForm.Both.engForm
            }
         }
         if (viewModel.selectedRegion.value != null) {
            ChannelPreference.channelRegion = viewModel.selectedRegion.value.toString()
         }

         when (viewModel.selectedForm.value) {
            ChannelForm.Online.form -> listener.onClick("${ChannelPreference.channelForm}")
            else -> listener.onClick("${ChannelPreference.channelForm}/${ChannelPreference.channelRegion}")
         }
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }
   }

   private fun unselectAllFormButton() {
      with(binding) {
         btnOffline.isSelected = false
         btnOffline.setTypeface(null, Typeface.NORMAL)

         btnOnline.isSelected = false
         btnOnline.setTypeface(null, Typeface.NORMAL)

         btnBoth.isSelected = false
         btnBoth.setTypeface(null, Typeface.NORMAL)
      }
   }

   private fun unselectAllRegionButton() {
      with(binding) {
         btnWhole.isSelected = false
         btnWhole.setTypeface(null, Typeface.NORMAL)

         btnSeoul.isSelected = false
         btnSeoul.setTypeface(null, Typeface.NORMAL)

         btnGyeonggi.isSelected = false
         btnGyeonggi.setTypeface(null, Typeface.NORMAL)

         btnIncheon.isSelected = false
         btnIncheon.setTypeface(null, Typeface.NORMAL)

         btnGangwon.isSelected = false
         btnGangwon.setTypeface(null, Typeface.NORMAL)

         btnChungcheong.isSelected = false
         btnChungcheong.setTypeface(null, Typeface.NORMAL)

         btnJeolla.isSelected = false
         btnJeolla.setTypeface(null, Typeface.NORMAL)

         btnGyeongsang.isSelected = false
         btnGyeongsang.setTypeface(null, Typeface.NORMAL)

         btnJeju.isSelected = false
         btnJeju.setTypeface(null, Typeface.NORMAL)
      }
   }

   private fun selectOfflineButton() {
      binding.btnOffline.isSelected = true
      binding.btnOffline.setTypeface(null, Typeface.BOLD)
   }

   private fun selectOnlineButton() {
      binding.btnOnline.isSelected = true
      binding.btnOnline.setTypeface(null, Typeface.BOLD)
   }

   private fun selectBothButton() {
      binding.btnBoth.isSelected = true
      binding.btnBoth.setTypeface(null, Typeface.BOLD)
   }

   private fun selectWholeButton() {
      binding.btnWhole.isSelected = true
      binding.btnWhole.setTypeface(null, Typeface.BOLD)
   }

   private fun selectSeoulButton() {
      binding.btnSeoul.isSelected = true
      binding.btnSeoul.setTypeface(null, Typeface.BOLD)
   }

   private fun selectGyeonggiButton() {
      binding.btnGyeonggi.isSelected = true
      binding.btnGyeonggi.setTypeface(null, Typeface.BOLD)
   }

   private fun selectIncheonButton() {
      binding.btnIncheon.isSelected = true
      binding.btnIncheon.setTypeface(null, Typeface.BOLD)
   }

   private fun selectGangwonButton() {
      binding.btnGangwon.isSelected = true
      binding.btnGangwon.setTypeface(null, Typeface.BOLD)
   }

   private fun selectChungcheongButton() {
      binding.btnChungcheong.isSelected = true
      binding.btnChungcheong.setTypeface(null, Typeface.BOLD)
   }

   private fun selectJeollaButton() {
      binding.btnJeolla.isSelected = true
      binding.btnJeolla.setTypeface(null, Typeface.BOLD)
   }

   private fun selectGyeongsangButton() {
      binding.btnGyeongsang.isSelected = true
      binding.btnGyeongsang.setTypeface(null, Typeface.BOLD)
   }

   private fun selectJejuButton() {
      binding.btnJeju.isSelected = true
      binding.btnJeju.setTypeface(null, Typeface.BOLD)
   }

   private fun previousSelectedForm() {
      when (ChannelPreference.channelForm) {
         ChannelForm.Online.form -> {
            selectOnlineButton()
            binding.regionContainer.visibility = View.GONE
         }
         ChannelForm.Offline.form -> {
            selectOfflineButton()
            binding.regionContainer.visibility = View.VISIBLE
         }
         ChannelForm.Both.form -> {
            selectBothButton()
            binding.regionContainer.visibility = View.VISIBLE
         }
         else -> {}
      }
   }

   private fun previousSelectedRegion() {
      when (ChannelPreference.channelRegion) {
         ChannelRegion.Whole.region -> selectWholeButton()
         ChannelRegion.Seoul.region -> selectSeoulButton()
         ChannelRegion.Gyeonggi.region -> selectGyeonggiButton()
         ChannelRegion.Incheon.region -> selectIncheonButton()
         ChannelRegion.Gangwon.region -> selectGangwonButton()
         ChannelRegion.Chungcheong.region -> selectChungcheongButton()
         ChannelRegion.Jeolla.region -> selectJeollaButton()
         ChannelRegion.Gyeongsang.region -> selectGyeongsangButton()
         ChannelRegion.Jeju.region -> selectJejuButton()
         // 처음에 아무것도 선택하지 않았을 때
         else -> {}
      }
   }

   fun setOnOKClickListener(listener: (String) -> Unit) {
      this.listener = object : ChannelFormDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelFormDialogOnClickListener {
      fun onClick(content: String)
   }
}