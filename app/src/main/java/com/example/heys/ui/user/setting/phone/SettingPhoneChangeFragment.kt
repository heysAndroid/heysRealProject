package com.example.heys.ui.user.setting.phone

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heys.CustomSnackBar
import com.example.heys.R
import com.example.heys.databinding.SettingPhoneChangeFragmentBinding
import com.example.heys.model.network.NetworkResult
import com.example.heys.model.network.Phone
import com.example.heys.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingPhoneChangeFragment : Fragment() {
   private lateinit var binding: SettingPhoneChangeFragmentBinding
   private val viewModel: SettingPhoneChangeViewModel by viewModels()

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
      binding = SettingPhoneChangeFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnNext.setOnClickListener { requestCheckPhoneNumber(viewModel.phoneNumber.value.toString()) }

      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      inputMethodManager.showSoftInput(binding.phoneInput, 0)
   }

   private fun goToPhoneVerification() {
      findNavController().navigate(
         R.id.action_settingPhoneChangeFragment_to_settingPhoneVerificationFragment,
         bundleOf("phoneNumber" to viewModel.phoneNumber.value))
   }

   private fun requestCheckPhoneNumber(phone: String) {
      viewModel.checkPhoneNumber(Phone(phone)).observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())
         when (response) {
            is NetworkResult.Success -> {
               if (response.data?.isUserExisted == true) {
                  CustomSnackBar(binding.root, "이미 존재하는 전화번호입니다.", binding.btnNext).show()
               } else {
                  postPhoneVerification(phone)
               }
            }

            is NetworkResult.Error -> {
               alert.setTitle("전화번호 체크 실패").setMessage("전화번호 체크에 실패했습니다.").create().show()
            }

            is NetworkResult.Loading -> {
               alert.setTitle("로딩 중").setMessage("전화번호 체크가 지연되고 있습니다.").create().show()
            }
         }
      }
   }

   private fun postPhoneVerification(phoneNumber: String) {
      viewModel.postPhoneVerification(Phone(phone = phoneNumber.replace("-", ""))).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("postPhoneVerification: ", response.data?.message.toString())
               goToPhoneVerification()
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
}