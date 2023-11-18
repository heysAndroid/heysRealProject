package com.example.heys.ui.user.myPage.edit

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.heys.InterestViewModel
import com.example.heys.databinding.ProfileEditFragmentBinding
import com.example.heys.enums.ChannelInterest
import com.example.heys.model.network.MyPage
import com.example.heys.model.network.MyPageEdit
import com.example.heys.model.network.NetworkResult
import com.example.heys.ui.main.MainActivity
import com.example.heys.ui.user.myPage.MbtiViewModel
import com.example.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
      binding.okButton.setOnClickListener { editMyPage() }

      getMyPage()

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
      }

      binding.addLink.setOnClickListener {
         if (childNum.value!! < 4) {
            when (childNum.value) {
               0 -> binding.llBehance.visibility = View.VISIBLE
               1 -> binding.llInstagram.visibility = View.VISIBLE
               2 -> binding.llGithub.visibility = View.VISIBLE
               else -> {
                  binding.llNaver.visibility = View.VISIBLE
                  binding.addButtonContainer.visibility = View.GONE
               }
            }
            childNum.value = childNum.value!! + 1
         }
      }

      binding.imgLink2.setOnClickListener {
         if (childNum.value == 1) {
            binding.llBehance.visibility = View.GONE
            childNum.value = childNum.value!! - 1
            viewModel.link2.value = ""
         }
      }

      binding.imgLink3.setOnClickListener {
         if (childNum.value == 2) {
            binding.llInstagram.visibility = View.GONE
            childNum.value = childNum.value!! - 1
            viewModel.link3.value = ""
         }
      }

      binding.imgLink4.setOnClickListener {
         if (childNum.value == 3) {
            binding.llGithub.visibility = View.GONE
            childNum.value = childNum.value!! - 1
            viewModel.link4.value = ""
         }
      }

      binding.imgLink5.setOnClickListener {
         if (childNum.value == 4) {
            binding.llNaver.visibility = View.GONE
            binding.addButtonContainer.visibility = View.VISIBLE
            childNum.value = childNum.value!! - 1
            viewModel.link5.value = ""
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

      interestViewModel.interestList.forEach {
         Log.d("interest:", it)
      }
   }

   private fun getMyPage() {
      val token = UserPreference.accessToken
      viewModel.getMyInfo("Bearer $token")
      viewModel.response.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         when (response) {
            is NetworkResult.Success -> {
               response.data?.user?.let { setMyPageInfo(it) }
            }

            is NetworkResult.Error -> {
               alert.setTitle("마이페이지 로딩 실패").setMessage("마이페이지 로딩에 실패했습니다.").create().show()
            }

            is NetworkResult.Loading -> {
               alert.setTitle("마이페이지 로딩 중").setMessage("마이페이지 로딩이 지연되고 있습니다.").create().show()
            }
         }
      }
   }

   private fun setMyPageInfo(myPage: MyPage) {
      viewModel.introduce.value = myPage.introduce
      viewModel.name.value = myPage.name
      viewModel.mbti.value = myPage.userPersonality
      viewModel.job.value = myPage.job
      viewModel.capability.value = myPage.capability
      viewModel.interestArray = myPage.interests.toMutableList()
      myPage.profileLinks.forEachIndexed { index, s ->
         when (index) {
            0 -> if (!s.isNullOrBlank()) viewModel.link1.value = s
            1 -> if (!s.isNullOrBlank()) viewModel.link2.value = s
            2 -> if (!s.isNullOrBlank()) viewModel.link3.value = s
            3 -> if (!s.isNullOrBlank()) viewModel.link4.value = s
            4 -> if (!s.isNullOrBlank()) viewModel.link5.value = s
         }
      }
      setMBTI()
      setInterest()
   }

   private fun editMyPage() {
      val token = UserPreference.accessToken
      val myPageEdit = MyPageEdit(
         phone = UserPreference.phoneNumber,
         gender = UserPreference.gender,
         name = viewModel.name.value,
         job = viewModel.job.value,
         capability = viewModel.capability.value,
         introduce = viewModel.introduce.value,
         userPersonality = mbtiViewModel.mbti.value,
         interests = interestViewModel.interestList.toTypedArray(),
         profileLinks = arrayOf(viewModel.link1.value!!, viewModel.link2.value!!, viewModel.link3.value!!, viewModel.link4.value!!, viewModel.link5.value!!)
      )

      viewModel.editMyInfo("Bearer $token", myPageEdit).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.i("editMyInfo: ", "success")
               findNavController().navigateUp()
            }

            is NetworkResult.Error -> {
               Log.w("editMyInfo: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("editMyInfo: ", "loading")
            }
         }
      }
   }
}