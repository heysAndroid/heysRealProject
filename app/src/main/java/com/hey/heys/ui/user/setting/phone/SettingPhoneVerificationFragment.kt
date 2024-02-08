package com.hey.heys.ui.user.setting.phone

import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.hey.heys.R
import com.hey.heys.databinding.SettingPhoneVerificationFragmentBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.Phone
import com.hey.heys.model.network.PhoneVerification
import com.hey.heys.model.network.UserEdit
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SettingPhoneVerificationFragment : Fragment() {
   private lateinit var binding: SettingPhoneVerificationFragmentBinding
   private val viewModel: SettingPhoneVerificationViewModel by viewModels()
   private lateinit var countDownTimer: CountDownTimer

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

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = SettingPhoneVerificationFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      val phoneNumber = arguments?.getString("phoneNumber")
      viewModel.phoneNumber.value = PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().country)

      countDownTimer = startTimer(3 * 60 * 1000L)
      countDownTimer?.start()

      viewModel.phoneNumber.observe(viewLifecycleOwner) { phone ->
         viewModel.code.asLiveData().observe(viewLifecycleOwner) { code ->
            binding.btnOk.setOnClickListener { code?.let { deletePhoneVerification(code, phone) } }
         }
      }

      binding.btnResend.setOnClickListener {
         countDownTimer?.cancel()
         countDownTimer = startTimer(3 * 60 * 1000L)
         countDownTimer?.start()

         viewModel.phoneNumber.observe(viewLifecycleOwner) { it?.let { postPhoneVerification(it) } }
      }
   }

   private fun goToSetting() {
      findNavController().navigate(R.id.action_settingPhoneVerificationFragment_to_settingFragment)
   }

   private fun postPhoneVerification(phoneNumber: String) {
      viewModel.postPhoneVerification(Phone(phone = phoneNumber.replace("-", ""))).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("postPhoneVerification: ", response.data?.message.toString())
            }

            is NetworkResult.Error -> {
               Log.w("postPhoneVerification: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("postPhoneVerification: ", "loading")
            }
         }
      }
   }

   private fun deletePhoneVerification(code: String, phoneNumber: String) {
      val phoneVerification = PhoneVerification(code, phoneNumber.replace("-", ""))

      viewModel.deletePhoneVerification(phoneVerification).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("deletePhoneVeri: ", response.data?.message.toString())

               if (response.data?.data?.isVerification == true) {
                  val user = UserEdit(
                     phone = viewModel.phoneNumber.value.toString(),
                     gender = UserPreference.gender,
                     name = UserPreference.name,
                     job = UserPreference.job,
                     capability = UserPreference.capability,
                     introduce = UserPreference.introduce,
                     userPersonality = UserPreference.mbti,
                     interests = ArrayList(UserPreference.interests.split(",")),
                     profileLinks = ArrayList(UserPreference.links.split(",")))

                  changePhoneNumber(user)
               } else {
                  com.hey.heys.CustomSnackBar(binding.root, "인증번호가 일치하지 않아요! \n SMS 문자를 확인해주세요.", binding.btnOk).show()
               }
            }

            is NetworkResult.Error -> {
               Log.w("deletePhoneVeri: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("deletePhoneVeri: ", "loading")
            }
         }
      }
   }

   private fun changePhoneNumber(user: UserEdit) {
      viewModel.changePhoneNumber("Bearer ${UserPreference.accessToken}", user).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("postPhoneVerification: ", response.data?.message.toString())
               viewModel.phoneNumber.value?.let { UserPreference.phoneNumber = it }
               goToSetting()
            }

            is NetworkResult.Error -> {
               Log.w("postPhoneVerification: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("postPhoneVerification: ", "loading")
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
}