package com.example.heysrealprojcet.ui.user.setting.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
   }
}