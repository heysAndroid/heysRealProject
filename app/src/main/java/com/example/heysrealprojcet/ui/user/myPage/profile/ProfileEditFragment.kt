package com.example.heysrealprojcet.ui.user.myPage.profile

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ProfileEditFragmentBinding

class ProfileEditFragment : Fragment() {
   private lateinit var binding: ProfileEditFragmentBinding
   private val viewModel: ProfileEditViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ProfileEditFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.introduceEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(35))
      binding.nameEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
      binding.jobEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))
      binding.abilityCount.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(10))

      binding.okButton.setOnClickListener { gotoMyPage() }
   }

   private fun gotoMyPage() {
      findNavController().navigate(R.id.action_profileEditFragment_to_myPageFragment)
   }
}