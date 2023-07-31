package com.example.heysrealprojcet.ui.user.myPage.edit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.InterestViewModel
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ProfileEditFragmentBinding
import com.example.heysrealprojcet.enums.ChannelInterest
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.ui.user.myPage.MbtiViewModel
import com.example.heysrealprojcet.util.UserPreference

class ProfileEditFragment : Fragment() {
   private lateinit var binding: ProfileEditFragmentBinding
   private lateinit var callback: OnBackPressedCallback
   private val viewModel: ProfileEditViewModel by viewModels()
   private val interestViewModel: InterestViewModel by viewModels()
   private var childNum = MutableLiveData(0)
   private val mbtiViewModel: MbtiViewModel by viewModels()

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
      binding = ProfileEditFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      binding.vmInterest = interestViewModel
      binding.vmMbti = mbtiViewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.okButton.setOnClickListener { gotoMyPage() }

      setMBTI()
      setInterest()

      interestViewModel.total.asLiveData().observe(viewLifecycleOwner) {
         binding.interestCount.text = "$it/3"
      }

      mbtiViewModel.mbti.asLiveData().observe(viewLifecycleOwner) {
         setAllMbtiUnselect()
         when (it) {
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
         // preference 에 저장
         UserPreference.mbti = it
      }

      binding.addLink.setOnClickListener {
         val childCount = binding.additionalLinkContainer.childCount
         if (childCount < 5) {
            childNum.observe(viewLifecycleOwner) {
               if (it < 4) {
                  binding.addButtonContainer.visibility = View.VISIBLE
               } else if (it == 4) {
                  binding.addButtonContainer.visibility = View.GONE
               }
            }
         }
      }


      binding.addLink.setOnClickListener {
         if (childNum.value!! < 4) {
            val hint = when (childNum.value) {
               0 -> "https://behance.net/gallery-c..."
               1 -> "https://m.instagram/my"
               2 -> "https://my.github.com"
               else -> " https://blog.naver.com/sfsdsf..."
            }
            binding.additionalLinkContainer.addView(makeAdditionalLinkView(hint, childNum.value!!))
            childNum.postValue(binding.additionalLinkContainer.childCount)
         }
      }
   }

   override fun onAttach(context: Context) {
      super.onAttach(context)

      // back button 이벤트 처리
      callback = object : OnBackPressedCallback(true) {
         override fun handleOnBackPressed() {
            val bottomSheet = ProfileEditCloseBottomSheet { goToBack() }
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
         }
      }
      requireActivity().onBackPressedDispatcher.addCallback(callback)
   }

   override fun onDetach() {
      super.onDetach()
      callback.remove()
   }

   private fun gotoMyPage() {
      findNavController().navigate(R.id.action_profileEditFragment_to_myPageFragment)
   }

   private fun goToBack() {
      findNavController().navigateUp()
   }

   private fun setMBTI() {
      viewModel.mbti.observe(viewLifecycleOwner) {
         when (it) {
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
            else -> {}
         }
         mbtiViewModel.setMbti(it)
      }
   }

   private fun setAllMbtiUnselect() {
      with(binding.mbtiView) {
         istj.isSelected = false
         isfj.isSelected = false
         infj.isSelected = false
         intj.isSelected = false

         istp.isSelected = false
         isfp.isSelected = false
         infp.isSelected = false
         intp.isSelected = false

         estp.isSelected = false
         esfp.isSelected = false
         enfp.isSelected = false
         entp.isSelected = false

         estj.isSelected = false
         esfj.isSelected = false
         enfj.isSelected = false
         entj.isSelected = false
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

   private fun makeAdditionalLinkView(hint: String, index: Int): AdditionalLinkView {
      return AdditionalLinkView(requireContext()).apply {
         setHint(hint)
         removeButtonClickListener {
            binding.additionalLinkContainer.removeViews(index, 1)
            childNum.postValue(binding.additionalLinkContainer.childCount)
         }
      }
   }
}