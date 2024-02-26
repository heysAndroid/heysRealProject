package com.hey.heys.ui.user.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hey.heys.BuildConfig
import com.hey.heys.R
import com.hey.heys.databinding.SettingFragmentBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.ui.intro.IntroActivity
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.UserPreference
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

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.btnPhoneChange.setOnClickListener { gotoPhoneChange() }
      binding.btnPasswordChange.setOnClickListener { gotoPasswordVerification() }
      binding.llPrivacy.setOnClickListener { goToPrivacyPolicy() }
      binding.llServiceTerm.setOnClickListener { goToServiceTerm() }
      binding.btnDeleteAccount.setOnClickListener { gotoDeleteAccount() }
      binding.tvLogout.setOnClickListener { logout() }
      binding.tvVersion.text = "현재 ${BuildConfig.VERSION_NAME}"
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

   private fun goToPrivacyPolicy(){
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://caring-fig-dc9.notion.site/715dc699691e4a80994c32e04261e38b")))
   }

   private fun goToServiceTerm(){
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://caring-fig-dc9.notion.site/e92a574c9a0345589f3d652c55c84349?pvs=4")))
   }
}