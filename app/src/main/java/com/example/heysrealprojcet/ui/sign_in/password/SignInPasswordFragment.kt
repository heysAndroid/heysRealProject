package com.example.heysrealprojcet.ui.sign_in.password

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignInPasswordFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInPasswordFragment : Fragment() {
   private lateinit var binding: SignInPasswordFragmentBinding
   private val viewModel: SignInPasswordViewModel by viewModels()

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
      binding = SignInPasswordFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.passwordToggle.setOnClickListener {
         viewModel.togglePasswordVisible()
         changeInputType()
      }

      binding.okButton.setOnClickListener {
         requestLogin(UserPreference.phoneNumber, UserPreference.password)
      }

      // 화면 들어오자마자 키보드 보이기
      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      binding.password.requestFocus()
      inputMethodManager.showSoftInput(binding.password, 0)
   }

   private fun changeInputType() {
      if (viewModel.isPasswordVisible.value == true) {
         binding.password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
         binding.password.setSelection(binding.password.length())
      } else {
         binding.password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
         binding.password.setSelection(binding.password.length())
      }
   }

   private fun requestLogin(username: String, password: String) {
      viewModel.login(username, password)
      viewModel.response.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         when (response.isSuccessful) {
            true -> {
               alert.setTitle("로그인 성공")
                  .setPositiveButton("확인") { _, _ -> goToMain() }.create().show()
               val token = response.headers()["Authorization"]?.split(" ")?.last()
               token.let { UserPreference.accessToken = it.toString() }
            }

            false -> {
               alert.setTitle("로그인 실패").setMessage("로그인에 실패했습니다.").create().show()
               Log.w("error: ", response.errorBody().toString())
            }
         }
      }
   }

   private fun goToMain() {
      findNavController().navigate(R.id.action_signInPasswordFragment_to_mainNavigation)
   }
}