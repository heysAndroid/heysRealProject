package com.example.heysrealprojcet.ui.sign_up.interest

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.SignUpInterestFragmentBinding
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint
import org.mindrot.jbcrypt.BCrypt

@AndroidEntryPoint
class SignUpInterestFragment : Fragment() {
   private lateinit var binding: SignUpInterestFragmentBinding
   private val viewModel: SignUpInterestViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpInterestFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.okButton.setOnClickListener { requestSignUp() }
   }

   private fun goToMain() {
      // findNavController().navigate(R.id.action_joinInterestFragment_to_mainFragment)
   }

   private fun requestSignUp() {
      val user = User(
         phone = UserPreference.phoneNumber,
         username = UserPreference.name,
         password = UserPreference.password,
         age = UserPreference.age,
         gender = UserPreference.gender,
         interests = viewModel.interestList as ArrayList<String>)

      viewModel.signUp(user)
      viewModel.response.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         // api 응답 별로 처리
         when (response) {
            is NetworkResult.Success -> {
               UserPreference.accessToken = response.data?.token ?: ""
               alert.setTitle("${user.username}님 회원가입 성공")
                  .setMessage("accessToken: ${UserPreference.accessToken}")
                  .setPositiveButton("확인") { _, _ -> goToMain() }.create().show()
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
}
