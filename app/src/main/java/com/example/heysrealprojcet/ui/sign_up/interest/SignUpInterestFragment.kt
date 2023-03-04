package com.example.heysrealprojcet.ui.sign_up.interest

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.InterestViewModel
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignUpInterestFragmentBinding
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import org.mindrot.jbcrypt.BCrypt

@AndroidEntryPoint
class SignUpInterestFragment : Fragment() {
   private lateinit var binding: SignUpInterestFragmentBinding
   private val signUpInterestViewModel: SignUpInterestViewModel by viewModels()
   private val interestViewModel: InterestViewModel by viewModels()

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
      binding = SignUpInterestFragmentBinding.inflate(inflater, container, false)
      binding.vm = signUpInterestViewModel
      binding.vm2 = interestViewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.okButton.setOnClickListener { requestSignUp() }
   }

   private fun goToMain() {
      findNavController().navigate(R.id.main_navigation, bundleOf("isNewUser" to true))
   }

   private fun requestSignUp() {
      val user = User(
         phone = UserPreference.phoneNumber,
         name = UserPreference.name,
         password = UserPreference.password,
         age = UserPreference.age,
         gender = UserPreference.gender,
         interests = interestViewModel.interestList as ArrayList<String>)

      signUpInterestViewModel.signUp(user)
      signUpInterestViewModel.responseSignUp.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         // api 응답 별로 처리
         when (response) {
            is NetworkResult.Success -> {
               requestLogin(UserPreference.name, UserPreference.password)
               goToMain()
            }

            is NetworkResult.Error -> {
               alert.setTitle("회원가입 실패").setMessage("회원가입에 실패했습니다.").create().show()
            }

            is NetworkResult.Loading -> {
               alert.setTitle("로딩 중").setMessage("회원가입이 지연되고 있습니다.").create().show()
            }
         }
      }
   }

   private fun encryptPassword(password: String): String {
      val passwordHashed = BCrypt.hashpw(password, BCrypt.gensalt(10))
      Log.d("암호화 password: ", passwordHashed)

      val isValidPassword = BCrypt.checkpw(password, passwordHashed)
      Log.w("암호화 검증: ", isValidPassword.toString())
      return passwordHashed
   }

   private fun requestLogin(username: String, password: String) {
      signUpInterestViewModel.login(username, password)
      signUpInterestViewModel.responseLogin.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         when (response.isSuccessful) {
            true -> {
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
}
