package com.example.heys.ui.channel.create.preview

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.heys.App
import com.example.heys.R
import com.example.heys.databinding.ChannelPreviewFragmentBinding
import com.example.heys.enums.ChannelForm
import com.example.heys.enums.ChannelRecruitmentMethod
import com.example.heys.enums.Gender
import com.example.heys.model.network.MyPage
import com.example.heys.model.network.NetworkResult
import com.example.heys.model.network.Study
import com.example.heys.ui.main.MainActivity
import com.example.heys.util.ChannelPreference
import com.example.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelPreviewFragment : Fragment() {
   private lateinit var binding: ChannelPreviewFragmentBinding
   private val viewModel by viewModels<ChannelPreviewViewModel>()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPreviewFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      setThumbnailImage(ChannelPreference.channelThumbnailUrl)
      setChannelRegion()
      setChannelRecruitmentMethod()
      getMyInfo()

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.btnNext.setOnClickListener {
         if (ChannelPreference.contentId < 0) {
            requestCreateStudy()
         } else {
            requestCreateChannel()
         }
      }
      binding.btnBack.setOnClickListener { findNavController().navigateUp() }

      viewModel.link1.observe(viewLifecycleOwner) {
         if (it.contains("kakao")) {
            binding.link1.setImageResource(R.drawable.ic_link_kakao)
         } else {
            binding.link1.setImageResource(R.drawable.ic_link_clip)
         }
      }

      viewModel.link2.observe(viewLifecycleOwner) {
         if (!it.isNullOrBlank()) {
            binding.link2.visibility = View.VISIBLE
            if (it.contains("kakao")) {
               binding.link2.setImageResource(R.drawable.ic_link_kakao)
            } else {
               binding.link2.setImageResource(R.drawable.ic_link_clip)
            }
         }
      }
   }

   private fun goToComplete(channelId: Int) {
      findNavController().navigate(
         R.id.action_channelPreviewFragment_to_channelCreateCompleteFragment,
         bundleOf("channelId" to channelId))
   }

   private fun setChannelRegion() {
      //온라인이면 지역 텍스트 숨기기
      if (viewModel.channelForm.value == ChannelForm.Online.form) {
         binding.channelRegion.visibility = View.GONE
      } else {
         binding.channelRegion.visibility = View.VISIBLE
      }
   }

   private fun setChannelRecruitmentMethod() {
      binding.channelRecruitmentMethod.text =
         if (viewModel.channelRecruitmentMethod.value == ChannelRecruitmentMethod.Immediately.method) {
            "승인없이 바로 참여가능해요."
         } else {
            "승인이 필요해요."
         }
   }

   private fun requestCreateStudy() {
      viewModel.channelInterest
      val study = Study(
         name = ChannelPreference.channelName,
         purposes = ChannelPreference.channelPurposeArray,
         online = ChannelPreference.channelFormEng,
         location = ChannelPreference.channelRegion,
         limit = ChannelPreference.channelCapacity,
         recruitEndDate = ChannelPreference.channelRecruitEndDateTime,
         recruitMethod = ChannelPreference.channelRecruitmentMethodEng,
         contentText = ChannelPreference.channelActivity,
         recruitText = ChannelPreference.channelMember,
         thumbnailUri = "",
         linkUri = arrayListOf(viewModel.link1.value!!, viewModel.link2.value!!),
         interests = ChannelPreference.channelInterestArray
      )

      val token = UserPreference.accessToken
      viewModel.createStudy("Bearer $token", study)
      viewModel.responseCreateStudy.observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.i("createStudy: ", "success")
               response.data?.id?.let { goToComplete(it) }
            }

            is NetworkResult.Error -> {
               Log.w("createStudy: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("createStudy: ", "loading")
            }
         }
      }
   }

   private fun requestCreateChannel() {
      viewModel.channelInterest
      val channel = Study(
         name = ChannelPreference.channelName,
         purposes = ChannelPreference.channelPurposeArray,
         online = ChannelPreference.channelFormEng,
         location = ChannelPreference.channelRegion,
         limit = ChannelPreference.channelCapacity,
         recruitEndDate = ChannelPreference.channelRecruitEndDateTime,
         recruitMethod = ChannelPreference.channelRecruitmentMethodEng,
         contentText = ChannelPreference.channelActivity,
         recruitText = ChannelPreference.channelMember,
         thumbnailUri = "",
         linkUri = arrayListOf(viewModel.link1.value!!, viewModel.link2.value!!),
         interests = ChannelPreference.channelInterestArray
      )

      val token = UserPreference.accessToken
      viewModel.createContentChannel("Bearer $token", ChannelPreference.contentId, channel).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.i("createChannel: ", "success")
               response.data?.id?.let { goToComplete(it) }
            }

            is NetworkResult.Error -> {
               Log.w("createChannel: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("createChannel: ", "loading")
            }
         }
      }
   }

   private fun getMyInfo() {
      viewModel.getMyInfo("Bearer ${UserPreference.accessToken}")
      viewModel.responseMyPage.observe(viewLifecycleOwner) { response ->
         val alert = AlertDialog.Builder(requireContext())

         when (response) {
            is NetworkResult.Success -> {
               response.data?.user?.let { setLeaderProfile(it) }
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

   private fun setLeaderProfile(user: MyPage) {
      binding.leaderName.text = user.name
      user.introduce?.let { binding.introText.text = it }

      // 퍼센트에 따른 프로필 이미지 설정
      when (user.percentage) {
         in 0..49 -> {
            when (user.gender) {
               Gender.Male.genderEnglish -> binding.leaderImage.setImageResource(R.drawable.ic_male_0)
               Gender.Female.genderEnglish -> binding.leaderImage.setImageResource(R.drawable.ic_female_0)
               else -> binding.leaderImage.setImageResource(R.drawable.ic_none_0)
            }
         }

         in 50..99 -> {
            when (user.gender) {
               Gender.Male.genderEnglish -> binding.leaderImage.setImageResource(R.drawable.ic_male_50)
               Gender.Female.genderEnglish -> binding.leaderImage.setImageResource(R.drawable.ic_female_50)
               else -> binding.leaderImage.setImageResource(R.drawable.ic_none_50)
            }
         }

         100 -> {
            when (user.gender) {
               Gender.Male.genderEnglish -> binding.leaderImage.setImageResource(R.drawable.ic_male_100)
               Gender.Female.genderEnglish -> binding.leaderImage.setImageResource(R.drawable.ic_female_100)
               else -> binding.leaderImage.setImageResource(R.drawable.ic_none_100)
            }
         }
      }
   }

   private fun setThumbnailImage(thumbnailUrl: String) {
      Glide.with(App.getInstance().applicationContext)
         .load(thumbnailUrl)
         .error(R.drawable.bg_thumbnail_default).into(binding.imgThumbnail)
   }
}