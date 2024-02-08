package com.hey.heys.ui.user.setting.password

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hey.heys.CustomSnackBar
import com.hey.heys.R
import com.hey.heys.databinding.SettingPasswordVerificationFragmentBinding
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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

      binding.passwordToggle.setOnClickListener {
         viewModel.togglePasswordVisible()
         changeInputType()
      }
      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.btnPasswordForgot.setOnClickListener { goToPasswordForget() }
      binding.btnOk.setOnClickListener { checkPassword(viewModel.password.value) }
   }

   private fun changeInputType() {
      if (viewModel.isPasswordVisible.value == true) {
         binding.password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
         binding.password.setSelection(binding.password.length())
      } else {
         binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
         binding.password.setSelection(binding.password.length())
      }
   }

   private fun checkPassword(password: String) {
      if (UserPreference.password == password) {
         goToPasswordChange()
      } else {
         com.hey.heys.CustomSnackBar(binding.root, "비밀번호가 일치하지 않아요!", binding.btnOk).show()
      }
   }

   private fun goToPasswordForget() {
      findNavController().navigate(R.id.action_settingPasswordVerificationFragment_to_settingPasswordForgetFragment)
   }

   private fun goToPasswordChange() {
      findNavController().navigate(R.id.action_settingPasswordVerificationFragment_to_settingPasswordChangeFragment, bundleOf("isFromLogin" to false))
   }
}