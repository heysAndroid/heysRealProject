package com.example.heysrealprojcet.ui.channel.dialog.form

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.heysrealprojcet.databinding.ChannelFormDialogBinding
import com.example.heysrealprojcet.enums.ChannelForm
import com.example.heysrealprojcet.enums.ChannelRegion

class ChannelFormDialog(private val context: Context, private val viewModel: ChannelFormDialogViewModel) {
   private var binding: ChannelFormDialogBinding = ChannelFormDialogBinding.inflate(LayoutInflater.from(context))
   private val dialog = Dialog(context)
   private lateinit var listener: ChannelFormDialogOnClickListener

   init {
      binding.vm = viewModel

      val button = when (viewModel.formText) {
         ChannelForm.Offline.form -> binding.btnOffline
         ChannelForm.Online.form -> binding.btnOnline
         else -> binding.btnBoth
      }

      val button2 = when (viewModel.regionText) {
         ChannelRegion.Whole.region -> binding.btnWhole
         ChannelRegion.Seoul.region -> binding.btnSeoul
         ChannelRegion.Gyeonggi.region -> binding.btnGyeonggi
         ChannelRegion.Incheon.region -> binding.btnIncheon
         ChannelRegion.Gangwon.region -> binding.btnGangwon
         ChannelRegion.Chungcheong.region -> binding.btnChungcheong
         ChannelRegion.Jeolla.region -> binding.btnJeolla
         ChannelRegion.Gyeongsang.region -> binding.btnGyeongsang
         else -> binding.btnJeju
      }

      button.isSelected = true
      button2.isSelected = true
      button.setTypeface(null, Typeface.BOLD)
      button2.setTypeface(null, Typeface.BOLD)
      viewModel.selectedView = button
      viewModel.choiceRegion = button2
      viewModel.region = binding.region

      if (button.text.contains("온")) {
         binding.region.visibility = View.VISIBLE
      } else {
         binding.region.visibility = View.GONE
      }
   }

   fun show() {
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
      dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog.setContentView(binding.root)
      dialog.setCancelable(false)
      dialog.show()

      binding.btnSave.setOnClickListener {
         val button = when (viewModel.formText) {
            ChannelForm.Offline.form -> binding.btnOffline
            ChannelForm.Online.form -> binding.btnOnline
            else -> binding.btnBoth
         }

         if (button.text.contains("온")) {
            listener.onClick("${viewModel.formText}/${viewModel.regionText}")
         } else {
            listener.onClick("${viewModel.formText}")
         }
         dialog.dismiss()
      }

      binding.closeButton.setOnClickListener { dialog.dismiss() }
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