package com.example.heys.ui.login.sign_up.name

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heys.R
import com.example.heys.databinding.SignUpNameFragmentBinding
import com.example.heys.ui.main.MainActivity
import java.util.regex.Pattern

class SignUpNameFragment : Fragment() {
   private lateinit var binding: SignUpNameFragmentBinding
   private val viewModel: SignUpNameViewModel by viewModels()

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
      binding = SignUpNameFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.okButton.setOnClickListener { goToGender() }
      binding.name.filters = arrayOf(InputFilter { src, _, _, _, _, _ ->
         val ps = Pattern.compile("^[a-zA-Zㄱ-ㅣ가-힣]+$")

         if (!ps.matcher(src).matches()) {
            return@InputFilter ""
         } else {
            return@InputFilter null
         }
      })

      // 화면 들어오자마자 키보드 보이기
      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      binding.name.requestFocus()
      inputMethodManager.showSoftInput(binding.name, 0)
   }

   private fun goToGender() {
      findNavController().navigate(R.id.action_signUpNameFragment_to_signUpGenderFragment)
   }
}