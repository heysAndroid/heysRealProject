package com.hey.heys.ui.user.setting.password

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hey.heys.R
import com.hey.heys.databinding.SettingPasswordForgetFragmentBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.Phone
import com.hey.heys.model.network.PhoneVerification
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingPasswordForgetFragment : Fragment() {
   private lateinit var binding: SettingPasswordForgetFragmentBinding
   private val viewModel: SettingPasswordForgetViewModel by viewModels()
   private lateinit var countDownTimer: CountDownTimer
   private val args: SettingPasswordChangeFragmentArgs by navArgs()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingPasswordForgetFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      sendVerificationNumber()

      countDownTimer = startTimer(3 * 60 * 1000L)
      countDownTimer?.start()

      binding.btnResend.setOnClickListener {
         countDownTimer?.cancel()
         countDownTimer = startTimer(3 * 60 * 1000L)
         countDownTimer?.start()

         sendVerificationNumber()
      }

      viewModel.code.asLiveData().observe(viewLifecycleOwner) { code ->
         binding.btnOk.setOnClickListener { code?.let { deletePhoneVerification(code) } }
      }
   }

   private fun sendVerificationNumber() {
      viewModel.postPhoneVerification(Phone(phone = UserPreference.phoneNumber)).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("phone Verification: ", response.data?.message.toString())
            }

            is NetworkResult.Error -> {
               Log.w("phone Verification: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("phone Verification: ", "loading")
            }
         }
      }
   }

   private fun deletePhoneVerification(code: String) {
      val phoneVerification = PhoneVerification(code, UserPreference.phoneNumber)

      viewModel.deletePhoneVerification(phoneVerification).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("delete Verification: ", response.data?.message.toString())
               if (response.data?.data?.isVerification == true) {
                  if (args.isFromLogin) {
                     goToPasswordChangeSignIn()
                  } else {
                     goToPasswordChange()
                  }
               } else {
                  com.hey.heys.CustomSnackBar(binding.root, "인증번호가 일치하지 않아요! \n SMS 문자를 확인해주세요.", binding.btnOk).show()
               }
            }

            is NetworkResult.Error -> {
               Log.w("delete Verification: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("delete Verification: ", "loading")
            }
         }
      }
   }

   private fun startTimer(initialMillis: Long): CountDownTimer = object : CountDownTimer(initialMillis, 1000L) {
      override fun onTick(millisUntilFinished: Long) {
         updateTime(millisUntilFinished)
      }

      override fun onFinish() {

      }
   }

   private fun updateTime(remainMillis: Long) {
      var remainSeconds = remainMillis / 1000
      binding.tvMinute.text = "%02d".format(remainSeconds / 60)
      binding.tvSecond.text = "%02d".format(remainSeconds % 60)
   }

   private fun goToPasswordChange() {
      findNavController().navigate(R.id.action_settingPasswordForgetFragment_to_settingPasswordChangeFragment, bundleOf("isFromLogin" to false))
   }

   private fun goToPasswordChangeSignIn() {
      findNavController().navigate(R.id.action_settingPasswordForgetFragment2_to_settingPasswordChangeFragment2)
   }
}