package com.example.heysrealprojcet.ui.user.myPage.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.InterestViewModel
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ProfileEditFragmentBinding

class ProfileEditFragment : Fragment() {
   private lateinit var binding: ProfileEditFragmentBinding
   private val viewModel: ProfileEditViewModel by viewModels()
   private val interestViewModel: InterestViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ProfileEditFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      binding.vmInterest = interestViewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.okButton.setOnClickListener { gotoMyPage() }
      setMbti()
   }

   private fun gotoMyPage() {
      findNavController().navigate(R.id.action_profileEditFragment_to_myPageFragment)
   }

   private fun setMbti() {
      viewModel.radioChecked.observe(viewLifecycleOwner) {
         when (it) {
            R.id.radioGroup1 -> {
               // radioGroup 2,3,4는 unselect 처리
               binding.mbtiView.radioGroup2.clearCheck()
               binding.mbtiView.radioGroup3.clearCheck()
               binding.mbtiView.radioGroup4.clearCheck()
            }

            R.id.radioGroup2 -> {
               binding.mbtiView.radioGroup1.clearCheck()
               binding.mbtiView.radioGroup3.clearCheck()
               binding.mbtiView.radioGroup4.clearCheck()
            }

            R.id.radioGroup3 -> {
               binding.mbtiView.radioGroup1.clearCheck()
               binding.mbtiView.radioGroup2.clearCheck()
               binding.mbtiView.radioGroup4.clearCheck()
            }

            R.id.radioGroup4 -> {
               binding.mbtiView.radioGroup1.clearCheck()
               binding.mbtiView.radioGroup2.clearCheck()
               binding.mbtiView.radioGroup3.clearCheck()
            }
         }
      }
   }
}