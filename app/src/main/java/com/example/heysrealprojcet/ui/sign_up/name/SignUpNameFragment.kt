package com.example.heysrealprojcet.ui.sign_up.name

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignUpNameFragmentBinding
import java.util.regex.Pattern

class SignUpNameFragment : Fragment() {
   private lateinit var binding: SignUpNameFragmentBinding
   private val viewModel: SignUpNameViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpNameFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.okButton.setOnClickListener { goToGender() }
      binding.name.filters = arrayOf(InputFilter { src, _, _, _, _, _ ->
         val ps = Pattern.compile("^[a-zA-Zㄱ-ㅣ가-힣]+$")

         if (!ps.matcher(src).matches()) {
            return@InputFilter ""
         } else {
            return@InputFilter null
         }
      })
   }

   private fun goToGender() {
      findNavController().navigate(R.id.action_signUpNameFragment_to_signUpGenderFragment)
   }
}