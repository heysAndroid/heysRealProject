package com.example.heysrealprojcet.ui.sign_up.password

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
      binding.passwordToggle.setOnClickListener {
         viewModel.togglePasswordVisible()
         changeInputType()
      }
      binding.okButton.setOnClickListener { goToJoinName() }
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

   private fun goToJoinName() {
      findNavController().navigate(R.id.action_signUpPasswordFragment_to_signUpNameFragment)
   }
}