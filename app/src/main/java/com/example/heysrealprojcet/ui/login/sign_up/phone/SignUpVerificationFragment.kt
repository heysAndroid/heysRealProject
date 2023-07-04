package com.example.heysrealprojcet.ui.login.sign_up.phone

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignUpPhoneVerificationFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.Phone
import com.example.heysrealprojcet.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpVerificationFragment : Fragment() {
   private lateinit var binding: SignUpPhoneVerificationFragmentBinding
   private val viewModel: SignUpVerificationViewModel by viewModels()
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

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpPhoneVerificationFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.phoneNumber.value = arguments?.getString("phoneNumber")
      setKeyboard()

      countDownTimer = startTimer(3 * 60 * 1000L)
      countDownTimer?.start()

      viewModel.phoneNumber.observe(viewLifecycleOwner) { it?.let { postPhoneVerification(it) } }

      binding.btnOk.setOnClickListener { goToJoinPassword() }
      binding.btnResned.setOnClickListener {
         countDownTimer?.cancel()
         countDownTimer = startTimer(3 * 60 * 1000L)
         countDownTimer?.start()

         viewModel.phoneNumber.observe(viewLifecycleOwner) { it?.let { postPhoneVerification(it) } }
      }
   }

   private fun goToJoinPassword() {
      findNavController().navigate(R.id.action_signUpVerificationFragment_to_signUpPasswordFragment)
   }

   private fun setKeyboard() {
      // 화면 들어오자마자 키보드 보이기
      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      binding.etCode.requestFocus()
      inputMethodManager.showSoftInput(binding.etCode, 0)
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