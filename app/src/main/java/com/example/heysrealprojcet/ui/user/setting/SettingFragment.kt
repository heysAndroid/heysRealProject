package com.example.heysrealprojcet.ui.user.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SettingFragmentBinding
import com.example.heysrealprojcet.ui.intro.IntroActivity
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference

class SettingFragment : Fragment() {
   private lateinit var binding: SettingFragmentBinding

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
      binding.tvLogout.setOnClickListener { goToIntro() }
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

   private fun goToIntro() {
      UserPreference.isAutoLogin = false
      val intent = Intent(requireActivity(), IntroActivity::class.java)
      startActivity(intent)
      requireActivity().finish()
   }
}