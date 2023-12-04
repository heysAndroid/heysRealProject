package com.example.heys.ui.channel.list.detail

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.heys.CustomSnackBar
import com.example.heys.R
import com.example.heys.databinding.ChannelDetailFragmentBinding
import com.example.heys.enums.ChannelForm
import com.example.heys.enums.ChannelRecruitmentMethod
import com.example.heys.enums.Gender
import com.example.heys.enums.RelationshipWithMe
import com.example.heys.model.network.NetworkResult
import com.example.heys.ui.channel.list.detail.approvedUser.ApprovedUserImageListRecyclerViewAdapter
import com.example.heys.ui.channel.list.detail.waitingUser.WaitingUserImageListRecyclerViewAdapter
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
class ChannelDetailFragment : Fragment() {
   private lateinit var binding: ChannelDetailFragmentBinding
   private val viewModel by viewModels<ChannelDetailViewModel>()
   val args: ChannelDetailFragmentArgs by navArgs()

   private lateinit var approvedUserAdapter: ApprovedUserImageListRecyclerViewAdapter
   private lateinit var waitingUserAdapter: WaitingUserImageListRecyclerViewAdapter

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
      binding = ChannelDetailFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      getChannelDetail(args.channelId)
      channelViewCountUp(args.channelId)
      setRelationshipWithMe()

      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.btnBookmark.setOnClickListener {
         it.isSelected = it.isSelected != true
         if (it.isSelected) {
            channelAddBookmark(args.channelId)
         } else {
            channelRemoveBookmark(args.channelId)
         }
      }
      binding.allApprovedUser.setOnClickListener { goToApprovedUserList() }
      binding.allWaitingUser.setOnClickListener { goToWaitingUserList() }
      binding.btnJoinVisitor.setOnClickListener { joinChannel(args.channelId) }
      binding.llContent.setOnClickListener { goToContentDetail() }
      binding.btnShare.setOnClickListener { kakaoShare() }
      // 채널 수정 우선 주석 처리
//      binding.tvEdit.setOnClickListener { goToChannelEdit() }
   }


   private fun goToApprovedUserList() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         findNavController().navigate(
            R.id.action_channelDetailFragment_to_approvedUserListFragment,
            bundleOf("channelId" to it.id))
      }
   }

   private fun goToWaitingUserList() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         val isLeader = it.relationshipWithMe == RelationshipWithMe.Leader.relationship
         findNavController().navigate(
            R.id.action_channelDetailFragment_to_waitingUserListFragment,
            bundleOf("channelId" to it.id, "isLeader" to isLeader))
      }
   }

   private fun goToChannelEdit() {
      findNavController().navigate(
         R.id.action_channelDetailFragment_to_channelEditFragment,
         bundleOf(
            "channelDetail" to viewModel.channelDetail.value,
            "interestString" to viewModel.interestString.value,
            "purposeString" to viewModel.purposeString.value,
            "recruitEndDate" to viewModel.recruitEndDate.value,
            "recruitEndTime" to viewModel.recruitEndTime.value)
      )
   }

   private fun getChannelDetail(id: Int) {
      viewModel.getChannelDetail("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.receiveChannelDetail(response.data?.channelDetail)
               setThumbnailImage()
               setChannelRegion()
               setChannelRecruitmentMethod()
               viewModel.channelDetail.value?.online?.let { setChannelForm(it) }
               setLeaderImage()
               setApprovedUserList()
               initBookmark()
               setLinks()
               setContent()

               // 승인 결정
               if (viewModel.channelDetail.value?.recruitMethod == ChannelRecruitmentMethod.Approval.methodEng) {
                  setWaitingUserList()
               } else {
                  binding.waitingUserCount.visibility = View.GONE
                  binding.waitingUserList.visibility = View.GONE
               }
            }

            is NetworkResult.Error -> {
               Log.w("getDetail: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("getDetail: ", "loading")
            }
         }
      }
   }

   private fun channelViewCountUp(id: Int) {
      viewModel.channelViewCountUp("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("channelViewCountUp: ", response.data?.message!!)
            }

            is NetworkResult.Error -> {
               Log.w("channelViewCountUp: ", "error ${response.message}")
            }

            is NetworkResult.Loading -> {
               Log.i("channelViewCountUp: ", "loading")
            }
         }
      }
   }

   private fun channelAddBookmark(id: Int) {
      viewModel.channelAddBookmark("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("channelAddBookmark: ", response.data?.message.toString())
               CustomSnackBar(binding.root, "내 관심에 추가했어요!", null, true, subMessage = "보러가기").show()
            }

            is NetworkResult.Error -> {
               Log.w("channelAddBookmark: ", "error ${response.message}")
               CustomSnackBar(binding.root, "내 관심에 추가하기를 실패했어요.", null, true).show()
            }

            is NetworkResult.Loading -> {
               Log.i("channelAddBookmark: ", "loading")
               CustomSnackBar(binding.root, "내 관심에 추가하기가 지연되고 있어요.", null, true).show()
            }
         }
      }
   }

   private fun channelRemoveBookmark(id: Int) {
      viewModel.channelRemoveBookmark("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("channelRemoveBookmark: ", response.data?.message.toString())
               CustomSnackBar(binding.root, "내 관심에서 제거했어요!", null, true).show()
            }

            is NetworkResult.Error -> {
               Log.w("channelRemoveBookmark: ", "error ${response.message}")
               CustomSnackBar(binding.root, "내 관심에서 제거하기를 실패했어요.", null, true).show()
            }

            is NetworkResult.Loading -> {
               Log.i("channelRemoveBookmark: ", "loading")
               CustomSnackBar(binding.root, "내 관심에서 제거하기가 지연되고 있어요.", null, true).show()
            }
         }
      }
   }

   private fun joinChannel(id: Int) {
      viewModel.joinChannel("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.d("joinChannel: ", response.data?.message.toString())
               CustomSnackBar(binding.root, "채널 신청이 완료되었어요!", null, true).show()

               viewModel.channelDetail.observe(viewLifecycleOwner) {
                  // 바로 승인
                  if (it.recruitMethod == ChannelRecruitmentMethod.Immediately.methodEng) {
                     binding.btnJoinVisitor.visibility = View.GONE
                     binding.btnJoinMember.visibility = View.VISIBLE
                  } else {
                     // 승인 결정
                     binding.btnJoinVisitor.visibility = View.GONE
                     binding.btnJoinApplicant.visibility = View.VISIBLE
                  }
               }
            }

            is NetworkResult.Error -> {
               Log.w("joinChannel: ", "error ${response.message}")
               CustomSnackBar(binding.root, "채널 신청을 실패했어요.", null, true).show()
            }

            is NetworkResult.Loading -> {
               Log.i("joinChannel: ", "loading")
               CustomSnackBar(binding.root, "채널 신청이 지연되고 있어요.", null, true).show()
            }
         }
      }
   }

   private fun setChannelRegion() {
      //온라인이면 지역 텍스트 숨기기
      if (viewModel.channelDetail.value?.online == ChannelForm.Online.engForm) {
         binding.channelRegion.visibility = View.GONE
      } else {
         binding.channelRegion.visibility = View.VISIBLE
      }
   }

   private fun setChannelRecruitmentMethod() {
      binding.channelRecruitmentMethod.text =
         if (viewModel.channelDetail.value?.recruitMethod == ChannelRecruitmentMethod.Immediately.methodEng) {
            "승인없이 바로 참여가능해요."
         } else {
            "승인이 필요해요."
         }
   }

   private fun setChannelForm(form: String) {
      when (form) {
         ChannelForm.Offline.engForm -> {
            binding.channelForm.text = ChannelForm.Offline.form + "으로 "
         }

         ChannelForm.Online.engForm -> {
            binding.channelForm.text = ChannelForm.Online.form + "으로 "
         }

         ChannelForm.Both.engForm -> {
            binding.channelForm.text = ChannelForm.Both.form + "으로 "
         }
      }
   }

   // 프로필 퍼센트에 따른 이미지 설정
   private fun setLeaderImage() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         when (it.leader.percentage) {
            in 0..49 -> {
               when (it.leader.gender) {
                  Gender.Male.gender -> binding.leaderImage.setImageResource(R.drawable.ic_male_0)
                  Gender.Female.gender -> binding.leaderImage.setImageResource(R.drawable.ic_female_0)
                  else -> binding.leaderImage.setImageResource(R.drawable.ic_none_0)
               }
            }

            in 50..99 -> {
               when (it.leader.gender) {
                  Gender.Male.gender -> binding.leaderImage.setImageResource(R.drawable.ic_male_50)
                  Gender.Female.gender -> binding.leaderImage.setImageResource(R.drawable.ic_female_50)
                  else -> binding.leaderImage.setImageResource(R.drawable.ic_none_50)
               }
            }

            100 -> {
               when (it.leader.gender) {
                  Gender.Male.gender -> binding.leaderImage.setImageResource(R.drawable.ic_male_100)
                  Gender.Female.gender -> binding.leaderImage.setImageResource(R.drawable.ic_female_100)
                  else -> binding.leaderImage.setImageResource(R.drawable.ic_none_100)
               }
            }
         }
      }
   }

   private fun setApprovedUserList() {
      viewModel.channelDetail.value?.approvedUserList?.let {
         if (it.size == 0) {
            setApprovedUserListInvisible()
         } else {
            setApprovedUserListVisible()
            approvedUserAdapter = ApprovedUserImageListRecyclerViewAdapter(it)
            binding.approvedUserList.adapter = approvedUserAdapter
            binding.approvedUserList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
         }
      }
   }

   private fun setApprovedUserListVisible() {
      binding.approvedUserListEmpty.visibility = View.GONE
      binding.approvedUserList.visibility = View.VISIBLE
   }

   private fun setApprovedUserListInvisible() {
      binding.approvedUserListEmpty.visibility = View.VISIBLE
      binding.approvedUserList.visibility = View.GONE
   }

   private fun setWaitingUserList() {
      viewModel.channelDetail.value?.waitingUserList?.let {
         if (it.size == 0) {
            setWaitingUserListInvisible()
         } else {
            setWaitingUserListVisible()
            waitingUserAdapter = WaitingUserImageListRecyclerViewAdapter(it)
            binding.waitingUserList.adapter = waitingUserAdapter
            binding.waitingUserList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
         }
      }
   }

   private fun setWaitingUserListVisible() {
      binding.waitingUserListEmpty.visibility = View.GONE
      binding.waitingUserList.visibility = View.VISIBLE
   }

   private fun setWaitingUserListInvisible() {
      binding.waitingUserListEmpty.visibility = View.VISIBLE
      binding.waitingUserList.visibility = View.GONE
   }

   private fun initBookmark() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         binding.btnBookmark.isSelected = it.isBookMarked
      }
   }

   private fun goToWebView(url: String) {
      findNavController().navigate(
         R.id.action_channelDetailFragment_to_webViewFragment,
         bundleOf("url" to url))
   }

   private fun setLinks() {
      viewModel.channelDetail.observe(viewLifecycleOwner) { channelDetail ->
         if (channelDetail.links[0].link.isNullOrEmpty() or channelDetail.links[1].link.isNullOrEmpty()) {
            binding.link2.visibility = View.GONE

            if (channelDetail.links[0].link.isNullOrEmpty()) {
               binding.link1.setOnClickListener { goToWebView(channelDetail.links[1].link) }
            }
            if (channelDetail.links[1].link.isNullOrEmpty()) {
               binding.link1.setOnClickListener { goToWebView(channelDetail.links[0].link) }
            }
         } else {
            binding.link2.visibility = View.VISIBLE
            binding.link1.setOnClickListener { goToWebView(channelDetail.links[0].link) }
            binding.link2.setOnClickListener { goToWebView(channelDetail.links[1].link) }
         }
      }
   }

   private fun setRelationshipWithMe() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         when (it.relationshipWithMe) {
            // 리더
            RelationshipWithMe.Leader.relationship -> {
               binding.btnJoinMember.visibility = View.VISIBLE
               binding.btnJoinVisitor.visibility = View.GONE
               binding.btnJoinApplicant.visibility = View.GONE
            }

            RelationshipWithMe.Member.relationship -> {
               binding.btnJoinMember.visibility = View.VISIBLE
               binding.btnJoinVisitor.visibility = View.GONE
               binding.btnJoinApplicant.visibility = View.GONE
            }

            // 방문자
            RelationshipWithMe.Visiter.relationship -> {
               binding.btnJoinMember.visibility = View.GONE
               binding.btnJoinVisitor.visibility = View.VISIBLE
               binding.btnJoinApplicant.visibility = View.GONE
            }

            // 승인 대기자
            else -> {
               binding.btnJoinMember.visibility = View.GONE
               binding.btnJoinVisitor.visibility = View.GONE
               binding.btnJoinApplicant.visibility = View.VISIBLE
            }
         }
      }
   }

   private fun setThumbnailImage() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         Glide.with(requireContext()).load(it.thumbnailUri).error(R.drawable.bg_thumbnail_default)
            .into(binding.imgThumbnail)
      }
   }

   private fun setContent() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         if (it.contentData != null) {
            binding.llContent.visibility = View.VISIBLE
            it.contentData.run {
               Glide.with(requireContext()).load(this.previewImgUrl).error(R.drawable.bg_thumbnail_default).into(binding.imgContentThumbnail)
               binding.tvContentTitle.text = this.title
               binding.tvContentCompany.text = this.company
            }
         } else {
            binding.llContent.visibility = View.GONE
         }
      }
   }

   private fun goToContentDetail() {
      val contentData = viewModel.channelDetail.value?.contentData!!
      findNavController().navigate(
         R.id.action_channelDetailFragment_to_contestExtracurricularDetailFragment, bundleOf(
            "channelType" to contentData.type, "contentId" to contentData.id
         ))
   }

   private fun kakaoShare() {
      val feed = createFeed()

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

   private fun createFeed(): FeedTemplate {
      val dynamicLink = Firebase.dynamicLinks.dynamicLink {
         link = Uri.parse("https://heys.page.link/channel?id=${viewModel.channelDetail.value?.id}")
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