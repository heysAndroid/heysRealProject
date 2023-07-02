package com.example.heysrealprojcet.ui.login.sign_in.password

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
import com.example.heysrealprojcet.CustomSnackBar
import com.example.heysrealprojcet.EventObserver
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignInPasswordFragmentBinding
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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
      setDeviceToken()

      // 화면 들어오자마자 키보드 보이기
      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      binding.password.requestFocus()
      inputMethodManager.showSoftInput(binding.password, 0)

      binding.passwordToggle.setOnClickListener {
         viewModel.togglePasswordVisible()
         changeInputType()
      }
      binding.btnForget.setOnClickListener { goToPasswordChange() }
      binding.okButton.setOnClickListener {
         requestLogin(UserPreference.phoneNumber, UserPreference.password)
      }

      viewModel.showSnackBarEvent.observe(viewLifecycleOwner) {
         if (it) CustomSnackBar(binding.root, "비밀번호가 일치하지 않아요!", binding.okButton).show()
      }
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
      viewModel.response.observe(viewLifecycleOwner, EventObserver { response ->
         when (response.isSuccessful) {
            true -> {
               val token = response.headers()["Authorization"]?.split(" ")?.last()
               token.let { UserPreference.accessToken = it.toString() }
               UserPreference.isAutoLogin = true
               postDeviceToken()
               goToMain()
            }

            false -> {
               viewModel.showSnackBar()
            }
         }
      })
   }

   private fun setDeviceToken() {
      FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
         if (!task.isSuccessful) {
            Log.w("FCM registration failed", task.exception)
            return@OnCompleteListener
         }

         UserPreference.deviceToken = task.result
         Log.d("FCM test", UserPreference.deviceToken)
      })
   }

   private fun postDeviceToken() {
      viewModel.postDeviceToken("Bearer ${UserPreference.accessToken}", UserPreference.deviceToken).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("postDeviceToken: ", response.data?.message.toString())
            }

            is NetworkResult.Error -> {
               Log.w("postDeviceToken: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("postDeviceToken: ", "loading")
            }
         }
      }
   }

   private fun goToPasswordChange() {
      findNavController().navigate(R.id.action_signInPasswordFragment_to_settingPasswordForgetFragment2)
   }

   private fun goToMain() {
      findNavController().navigate(R.id.action_signInPasswordFragment_to_mainNavigation)
   }
}

