package com.example.heysrealprojcet.ui.user.setting.phone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SettingPhoneChangeFragmentBinding

class SettingPhoneChangeFragment : Fragment() {
   private lateinit var binding: SettingPhoneChangeFragmentBinding
   private val viewModel: SettingPhoneChangeViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingPhoneChangeFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnNext.setOnClickListener { goToPhoneVerification() }

      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      inputMethodManager.showSoftInput(binding.phoneInput, 0)
   }

   private fun goToPhoneVerification() {
      findNavController().navigate(
         R.id.action_settingPhoneChangeFragment_to_settingPhoneVerificationFragment,
         bundleOf("phoneNumber" to viewModel.phoneNumber.value))
   }
}