package com.example.heysrealprojcet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelWayFragmentDialogBinding

class ChannelWayDialogFragment: DialogFragment() {
   private lateinit var binding: ChannelWayFragmentDialogBinding
   private val viewModel : ChannelWayDialogViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelWayFragmentDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnApproval.isSelected = true
      viewModel.choiceWay = binding.btnApproval

      binding.btnSave.setOnClickListener {
         dialog!!.dismiss()
      }
   }
}