package com.hey.heys.ui.login.sign_in.phone

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hey.heys.CustomSnackBar
import com.hey.heys.R
import com.hey.heys.databinding.SignInPhoneFragmentBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.Phone
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInPhoneFragment : Fragment() {
   private lateinit var binding: SignInPhoneFragmentBinding
   private val viewModel: SignInPhoneViewModel by viewModels()

   override fun onResume() {
      super.onResume()
      (requireActivity() as MainActivity).hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      (requireActivity() as MainActivity).hideBottomNavigation(false)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignInPhoneFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState) // livedata 사용할 때 객체 범위를 반드시 지정해줘야함!!
      (requireActivity() as MainActivity).hideBottomNavigation(true)

      binding.lifecycleOwner = this
      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.okButton.setOnClickListener { requestCheckPhoneNumber() }
      binding.phoneInput.addTextChangedListener(PhoneNumberFormattingTextWatcher("KR"))

      // 화면 들어오자마자 키보드 보이기
      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      inputMethodManager.showSoftInput(binding.phoneInput, 0)

      viewModel.showSnackBarEvent.observe(viewLifecycleOwner) {
         if (it) CustomSnackBar(binding.root, "일치하는 계정이 없어요!", binding.okButton).show()
      }
   }

   private fun requestCheckPhoneNumber() {
      viewModel.checkPhoneNumber(Phone(UserPreference.phoneNumber))
      viewModel.response.observe(viewLifecycleOwner, com.hey.heys.EventObserver { response ->
         val alert = AlertDialog.Builder(requireContext())

         when (response) {
            is NetworkResult.Success -> {
               if (response.data?.isUserExisted == true) {
                  goToPassword()
               } else {
                  viewModel.showSnackBar()
                  binding.okButton.isEnabled = false
               }
            }

            is NetworkResult.Error -> {
               alert.setTitle("전화번호 체크 실패").setMessage("전화번호 체크에 실패했습니다.").create().show()
               Log.w("checkMember error", response.message.toString())
            }

            is NetworkResult.Loading -> {
               alert.setTitle("로딩 중").setMessage("전화번호 체크가 지연되고 있습니다.").create().show()
            }
         }
      })
   }

   private fun goToPassword() {
      if (findNavController().currentDestination?.id == R.id.signInPhoneFragment) {
         findNavController().navigate(R.id.action_signInPhoneFragment_to_signInPasswordFragment)
      }
   }
}