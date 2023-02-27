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
import com.example.heysrealprojcet.util.UserPreference

class ProfileEditFragment : Fragment() {
   private lateinit var binding: ProfileEditFragmentBinding
   private val viewModel: ProfileEditViewModel by viewModels()
   private val mbtiViewModel: MbtiViewModel by viewModels()
   private val interestViewModel: InterestViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ProfileEditFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      binding.vmMbti = mbtiViewModel
      binding.vmInterest = interestViewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      setMbti()
      binding.okButton.setOnClickListener { gotoMyPage() }
   }

   private fun gotoMyPage() {
      findNavController().navigate(R.id.action_profileEditFragment_to_myPageFragment)
   }

   private fun setMbti() {
      when (UserPreference.mbti) {
         "ISTJ" -> binding.mbtiView.istj.isSelected = true
         "ISFJ" -> binding.mbtiView.isfj.isSelected = true
         "INFJ" -> binding.mbtiView.infj.isSelected = true
         "INTJ" -> binding.mbtiView.intj.isSelected = true
         "ISTP" -> binding.mbtiView.istp.isSelected = true
         "ISFP" -> binding.mbtiView.isfp.isSelected = true
         "INFP" -> binding.mbtiView.infp.isSelected = true
         "INTP" -> binding.mbtiView.intp.isSelected = true
         "ESTP" -> binding.mbtiView.estp.isSelected = true
         "ESFP" -> binding.mbtiView.esfp.isSelected = true
         "ENFP" -> binding.mbtiView.enfp.isSelected = true
         "ENTP" -> binding.mbtiView.entp.isSelected = true
         "ESTJ" -> binding.mbtiView.estj.isSelected = true
         "ESFJ" -> binding.mbtiView.esfj.isSelected = true
         "ENFJ" -> binding.mbtiView.enfj.isSelected = true
         "ENTJ" -> binding.mbtiView.entj.isSelected = true
      }
   }
}