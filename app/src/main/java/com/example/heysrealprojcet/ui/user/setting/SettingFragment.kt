package com.example.heysrealprojcet.ui.user.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SettingFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.intro.IntroActivity
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
   private lateinit var binding: SettingFragmentBinding
   private val viewModel: SettingViewModel by viewModels()
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SettingFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.phoneChange.setOnClickListener { gotoPhoneChange() }
      binding.passwordChange.setOnClickListener { gotoPasswordVerification() }
      binding.deleteAccount.setOnClickListener { gotoDeleteAccount() }
      binding.tvLogout.setOnClickListener { logout() }
   }

   private fun gotoDeleteAccount() {
      findNavController().navigate(R.id.action_settingFragment_to_settingDeleteAccountFragment)
   }

   private fun gotoPhoneChange() {
      findNavController().navigate(R.id.action_settingFragment_to_settingPhoneChangeFragment)
   }

   private fun gotoPasswordVerification() {
      findNavController().navigate(R.id.action_settingFragment_to_settingPasswordVerificationFragment)
   }

   private fun logout() {
      deleteDeviceToken()

      UserPreference.init()
      val intent = Intent(requireActivity(), IntroActivity::class.java)
      startActivity(intent)
      requireActivity().finish()
   }

   private fun deleteDeviceToken() {
      viewModel.deleteDeviceToken("Bearer ${UserPreference.accessToken}", UserPreference.deviceToken).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("deleteDeviceToken: ", response.data?.message.toString())
            }

            is NetworkResult.Error -> {
               Log.w("deleteDeviceToken: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("deleteDeviceToken: ", "loading")
            }
         }
      }
   }
}