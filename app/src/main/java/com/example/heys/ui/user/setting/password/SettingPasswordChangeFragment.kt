package com.example.heys.ui.user.setting.password

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.heys.R
import com.example.heys.databinding.SettingPasswordChangeFragmentBinding
import com.example.heys.model.network.NetworkResult
import com.example.heys.model.network.Password
import com.example.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingPasswordChangeFragment : Fragment() {
   private lateinit var binding: SettingPasswordChangeFragmentBinding
   private val viewModel: SettingPasswordChangeViewModel by viewModels()
   val args: SettingPasswordChangeFragmentArgs by navArgs()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingPasswordChangeFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.btnOk.setOnClickListener { changePassword(viewModel.password.value) }
      binding.btnOk.text = if (args.isFromLogin) {
         "자동 로그인하기"
      } else {
         "새 비밀번호로 변경하기"
      }
      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      inputMethodManager.showSoftInput(binding.password, 0)
   }

   private fun changePassword(password: String) {
      viewModel.changePassword("Bearer ${UserPreference.accessToken}", Password(password)).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               goToMain()
            }

            is NetworkResult.Loading -> {
               Log.w("change password", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("change password", response.message.toString())
            }
         }
      }
   }

   private fun goToMain() {
      findNavController().navigate(R.id.action_settingPasswordChangeFragment_to_mainFragment)
   }
}