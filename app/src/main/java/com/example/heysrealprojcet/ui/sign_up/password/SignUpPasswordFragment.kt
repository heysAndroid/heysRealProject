package com.example.heysrealprojcet.ui.sign_up.password

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignUpPasswordFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference

class SignUpPasswordFragment : Fragment() {
   private lateinit var binding: SignUpPasswordFragmentBinding
   private val viewModel: SignUpPasswordViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpPasswordFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      if(UserPreference.isExistingUser) {
         binding.okButton.text = "로그인 하기"
         binding.description3.visibility = View.GONE
      }
      else {
         binding.okButton.text = "3초만에 헤이즈 가입하기"
         binding.btnForget.visibility = View.GONE
      }

      binding.passwordToggle.setOnClickListener {
         viewModel.togglePasswordVisible()
         changeInputType()
      }

      binding.okButton.setOnClickListener {
         if(UserPreference.isExistingUser) {
            // TODO 비밀번호 검사
            goToMain()
         }
         else {
            goToJoinName()
         }
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

   private fun goToMain() {
      val intent = Intent(requireContext(), MainActivity::class.java)
      startActivity(intent)
      requireActivity().finish()
   }

   private fun goToJoinName() {
      findNavController().navigate(R.id.action_signUpPasswordFragment_to_signUpNameFragment)
   }
}