package com.example.heysrealprojcet.ui.user.setting.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SettingPhoneVerificationFragmentBinding

class SettingPhoneVerificationFragment : Fragment() {
   private lateinit var binding: SettingPhoneVerificationFragmentBinding
   private val viewModel: SettingPhoneVerificationViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingPhoneVerificationFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.phoneNumber.value = arguments?.getString("phoneNumber")

      if (viewModel.phoneNumber.value!!.isNotEmpty()) {
         viewModel.timerStart()
      }

      binding.okButton.setOnClickListener { goToSetting() }
   }

   private fun goToSetting() {
      findNavController().navigate(R.id.action_settingPhoneVerificationFragment_to_settingFragment)
   }
}