package com.example.heys.ui.main.content.contestExtracurricular.contest.detail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.heys.CustomSnackBar
import com.example.heys.R
import com.example.heys.databinding.ContestExtracurricularDetailFragmentBinding
import com.example.heys.model.network.NetworkResult
import com.example.heys.ui.main.MainActivity
import com.example.heys.util.UserPreference
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLink
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContestExtracurricularDetailFragment : Fragment() {
   private lateinit var binding: ContestExtracurricularDetailFragmentBinding
   private val viewModel: ContestExtracurricularDetailViewModel by viewModels()
   val args: ContestExtracurricularDetailFragmentArgs by navArgs()
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

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ContestExtracurricularDetailFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      contentViewCountUp(args.contentId)
      getContentDetail(args.contentId)

      with(binding) {
         btnBack.setOnClickListener { findNavController().navigateUp() }
         btnChannel.setOnClickListener { goToChannelList(args.contentId) }
         btnShare.setOnClickListener { kakaoShare(args.contentId) }
         btnBookmark.setOnClickListener {
            it.isSelected = it.isSelected != true
            if (it.isSelected) {
               contentAddBookmark(args.contentId)
            } else {
               contentRemoveBookmark(args.contentId)
            }
         }
         btnZoom.setOnClickListener { viewModel.thumbnailUri.value?.let { goToZoom(it) } }
         imgUrl.setOnClickListener { viewModel.linkUrl.value?.let { goToWebView(it) } }
      }
   }

   private fun goToZoom(imageUri: String) {
      findNavController().navigate(
         R.id.action_contestExtracurricularDetailFragment_to_contestDetailLookFragment,
         bundleOf("imageUri" to imageUri))
   }

   private fun goToChannelList(contentId: Int) {
      findNavController().navigate(
         R.id.action_contestExtracurricularDetailFragment_to_channelListFragment, bundleOf(
            "channelType" to args.channelType,
            "contentId" to contentId
         )
      )
   }

   private fun goToWebView(url: String) {
      findNavController().navigate(
         R.id.action_contestExtracurricularDetailFragment_to_webViewFragment,
         bundleOf("url" to url))
   }

   private fun getContentDetail(id: Int) {
      viewModel.getContentDetail("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.receiveContentDetail(response.data?.contentDetail)
               Glide.with(requireContext()).load(viewModel.thumbnailUri.value).error(R.drawable.bg_thumbnail_default).into(binding.imgThumbnail)
               viewModel.dday.value?.let { setDday(it) }
               setBookmark()
            }

            is NetworkResult.Error -> {
               Log.w("getContentDetail: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("getContentDetail: ", "loading")
            }
         }
      }
   }

   private fun contentViewCountUp(id: Int) {
      viewModel.contentViewCountUp("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("contentViewCountUp: ", response.message.toString())
            }

            is NetworkResult.Error -> {
               Log.w("contentViewCountUp: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("contentViewCountUp: ", "loading")
            }
         }
      }
   }

   private fun contentAddBookmark(id: Int) {
      viewModel.contentAddBookmark("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("contentAddBookmark: ", response.data?.message.toString())
               CustomSnackBar(binding.root, "내 관심에 추가했어요!", null, true, subMessage = "보러가기").show()
            }

            is NetworkResult.Error -> {
               Log.w("contentAddBookmark: ", "error ${response.message}")
               CustomSnackBar(binding.root, "내 관심에 추가하기를 실패했어요.", null, true).show()
            }

            is NetworkResult.Loading -> {
               Log.i("contentAddBookmark: ", "loading")
               CustomSnackBar(binding.root, "내 관심에 추가하기가 지연되고 있어요.", null, true).show()
            }
         }
      }
   }

   private fun contentRemoveBookmark(id: Int) {
      viewModel.contentRemoveBookmark("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("contentRemoveBookmark: ", response.data?.message.toString())
               CustomSnackBar(binding.root, "내 관심에서 제거했어요!", null, true).show()
            }

            is NetworkResult.Error -> {
               Log.w("contentRemoveBookmark: ", "error ${response.message}")
               CustomSnackBar(binding.root, "내 관심에서 제거하기를 실패했어요.", null, true).show()
            }

            is NetworkResult.Loading -> {
               Log.i("contentRemoveBookmark: ", "loading")
               CustomSnackBar(binding.root, "내 관심에서 제거하기가 지연되고 있어요.", null, true).show()
            }
         }
      }
   }

   private fun setDday(dday: Int) {
      when {
         dday < 0 -> {
            binding.dday.text = "마감"
            binding.dday.setBackgroundResource(R.drawable.bg_e1e1e1_radius_4)
         }

         dday in 1..6 -> {
            binding.dday.text = "D-${dday}"
            binding.dday.setBackgroundResource(R.drawable.bg_fd494a_radius_4)
         }

         else -> {
            binding.dday.text = "D-${dday}"
            binding.dday.setBackgroundResource(R.drawable.bg_34d676_radius_4)
         }
      }
   }

   private fun setBookmark() {
      viewModel.isBookMarked.observe(viewLifecycleOwner) {
         binding.btnBookmark.isSelected = it
      }
   }

   private fun kakaoShare(id: Int) {
      val feed = createFeed(id)

      if (ShareClient.instance.isKakaoTalkSharingAvailable(requireContext())) {
         ShareClient.instance.shareDefault(requireContext(), feed) { sharingResult, error ->
            if (error != null) {
               Log.e("kakaoShare", "카카오톡 공유 실패", error)
            } else if (sharingResult != null) {
               Log.d("kakaoShare", "카카오톡 공유 성공 ${sharingResult.intent}")
               startActivity(sharingResult.intent)

               Log.w("kakaoShare", "Warning Msg: ${sharingResult.warningMsg}")
               Log.w("kakaoShare", "Argument Msg: ${sharingResult.argumentMsg}")
            }
         }
      } else {
         val sharerUrl = WebSharerClient.instance.makeDefaultUrl(feed)
         try {
            KakaoCustomTabsClient.openWithDefault(requireContext(), sharerUrl)
         } catch (e: UnsupportedOperationException) {
            Log.e("sharerUrl", "카카오톡 공유 실패", e.cause)
         }
      }
   }

   private fun createFeed(id: Int): FeedTemplate {
      val dynamicLink = Firebase.dynamicLinks.dynamicLink {
         link = Uri.parse("https://heys.page.link/content?id=${id}")
         domainUriPrefix = "https://heys.page.link"
         androidParameters {
            DynamicLink.AndroidParameters.Builder()
               .build()
         }
      }

      return FeedTemplate(
         content = Content(
            title = "함께 성장하는 청춘을 만들어가요!",
            description = "모바일 앱에서 확인해보세요.",
            imageUrl = "https://i.ibb.co/HxMF3Dx/img-heys-logo.png",
            link = Link(
               mobileWebUrl = dynamicLink.uri.toString())),

         buttons = listOf(
            Button(
               "앱으로 이동",
               Link(
                  mobileWebUrl = dynamicLink.uri.toString())
            )))
   }
}