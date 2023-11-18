package com.example.heys.ui.user.setting.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heys.R
import com.example.heys.databinding.SettingPasswordForgetFragmentBinding
import com.example.heys.util.UserPreference

class SettingPasswordForgetFragment : Fragment() {
   private lateinit var binding: SettingPasswordForgetFragmentBinding
   private val viewModel: SettingPasswordForgetViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingPasswordForgetFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.timerStart()

      binding.btnNext.setOnClickListener {
         if (UserPreference.isAutoLogin) {
            goToPasswordChange()
         } else {
            goToLoginPasswordChange()
         }
      }
   }

   private fun goToLoginPasswordChange() {
      findNavController().navigate(R.id.action_settingPasswordForgetFragment2_to_settingPasswordChangeFragment2)
   }

   private fun goToPasswordChange() {
      findNavController().navigate(R.id.action_settingPasswordForgetFragment_to_settingPasswordChangeFragment)
   }
}