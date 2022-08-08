package com.example.heysrealprojcet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelFormFragmentDialogBinding


class ChannelFormDialogFragment : DialogFragment() {
   private lateinit var binding: ChannelFormFragmentDialogBinding
   private val viewModel: ChannelFormDialogViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelFormFragmentDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnOnOff.isSelected = true
      viewModel.choiceForm = binding.btnOnOff

      binding.btnSave.setOnClickListener {
         dialog!!.dismiss()
      }
   }
}