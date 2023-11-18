package com.example.heys.ui.user.setting.withdrawal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heys.R
import com.example.heys.databinding.WithdrawalReasonFragmentBinding

class WithdrawalReasonFragment : Fragment() {
   private lateinit var binding: WithdrawalReasonFragmentBinding
   private val viewModel: WithdrawalReasonViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = WithdrawalReasonFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      setEditText()
      binding.btnNext.setOnClickListener { gotoDeleteDecide() }
   }

   private fun setEditText() {
      binding.rgReason.setOnCheckedChangeListener { group, checkedId ->
         if (checkedId == binding.btnCustom.id) {
            binding.edtWrite.visibility = View.VISIBLE
            binding.edtWrite.requestFocus()
            viewModel.reason.value = ""
         } else {
            binding.edtWrite.visibility = View.GONE
         }
      }
   }

   private fun gotoDeleteDecide() {
      findNavController().navigate(R.id.action_withdrawalReasonFragment_to_withdrawalConfirmFragment, bundleOf("reason" to viewModel.reason.value))
   }
}