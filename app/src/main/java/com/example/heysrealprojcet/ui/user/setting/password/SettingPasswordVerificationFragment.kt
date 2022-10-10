package com.example.heysrealprojcet.ui.user.setting.password

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SettingPasswordVerificationFragmentBinding

class SettingPasswordVerificationFragment : Fragment() {
   private lateinit var binding: SettingPasswordVerificationFragmentBinding
   private val viewModel: SettingPasswordVerificationViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingPasswordVerificationFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.passwordForget.setOnClickListener { goToPasswordForget() }
      binding.btnNext.setOnClickListener { goToPasswordChange() }

      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      inputMethodManager.showSoftInput(binding.password, 0)
   }

   private fun goToPasswordForget() {
      findNavController().navigate(R.id.action_settingPasswordVerificationFragment_to_settingPasswordForgetFragment)
   }

   private fun goToPasswordChange() {
      findNavController().navigate(R.id.action_settingPasswordVerificationFragment_to_settingPasswordChangeFragment)
   }
}