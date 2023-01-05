package com.example.heysrealprojcet.ui.sign_up.age

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignUpAgeFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class SignUpAgeFragment : Fragment() {
   private lateinit var binding: SignUpAgeFragmentBinding
   private val viewModel: SignUpAgeViewModel by viewModels()
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpAgeFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

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

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.okButton.setOnClickListener { goToInterest() }

//      viewModel.isEnabled.observe(viewLifecycleOwner) {
//         if (it) {
//            binding.ageEditText.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edittext_focused)
//         } else {
//            binding.ageEditText.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edittext_disabled)
//         }
//      }
   }

   private fun goToInterest() {
      findNavController().navigate(R.id.action_signUpAgeFragment_to_signUpInterestFragment)
   }
}