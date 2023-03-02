package com.example.heysrealprojcet.ui.user.myPage.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.InterestViewModel
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ProfileEditFragmentBinding
import com.example.heysrealprojcet.enums.ChannelInterest

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
      setInterest()

      interestViewModel.total.asLiveData().observe(viewLifecycleOwner) {
         binding.interestCount.text = "$it/3"
      }
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

   private fun setInterest() {
      // select 선택
      viewModel.interestArray.forEach {
         when (it) {
            ChannelInterest.Planning.interest -> binding.interestView.planning.isSelected = true
            ChannelInterest.Design.interest -> binding.interestView.design.isSelected = true
            ChannelInterest.Programming.interest -> binding.interestView.programming.isSelected = true
            ChannelInterest.IT.interest -> binding.interestView.it.isSelected = true
            ChannelInterest.Data.interest -> binding.interestView.data.isSelected = true
            ChannelInterest.Game.interest -> binding.interestView.game.isSelected = true
            ChannelInterest.Marketing.interest -> binding.interestView.marketing.isSelected = true
            ChannelInterest.Business.interest -> binding.interestView.business.isSelected = true
            ChannelInterest.Economics.interest -> binding.interestView.economics.isSelected = true
            ChannelInterest.Engineering.interest -> binding.interestView.engineering.isSelected = true
            ChannelInterest.Art.interest -> binding.interestView.art.isSelected = true
            ChannelInterest.Novel.interest -> binding.interestView.novel.isSelected = true
            ChannelInterest.Lifestyle.interest -> binding.interestView.lifestyle.isSelected = true
            ChannelInterest.Picture.interest -> binding.interestView.picture.isSelected = true
            ChannelInterest.Culture.interest -> binding.interestView.culture.isSelected = true
            ChannelInterest.Travel.interest -> binding.interestView.travel.isSelected = true
            ChannelInterest.Environment.interest -> binding.interestView.environment.isSelected = true
            ChannelInterest.Language.interest -> binding.interestView.language.isSelected = true
            ChannelInterest.MediaContents.interest -> binding.interestView.mediaContents.isSelected = true
            ChannelInterest.Paper.interest -> binding.interestView.paper.isSelected = true
            ChannelInterest.Sports.interest -> binding.interestView.sports.isSelected = true
            ChannelInterest.Dance.interest -> binding.interestView.dance.isSelected = true
            ChannelInterest.Service.interest -> binding.interestView.service.isSelected = true
         }
      }
      interestViewModel.setInterest(viewModel.interestArray)
   }
}