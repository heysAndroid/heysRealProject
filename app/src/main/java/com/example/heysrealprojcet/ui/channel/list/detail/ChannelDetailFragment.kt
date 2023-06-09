package com.example.heysrealprojcet.ui.channel.list.detail

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
import com.example.heysrealprojcet.CustomSnackBar
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelDetailFragmentBinding
import com.example.heysrealprojcet.enums.ChannelForm
import com.example.heysrealprojcet.enums.ChannelRecruitmentMethod
import com.example.heysrealprojcet.enums.Gender
import com.example.heysrealprojcet.enums.RelationshipWithMe
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.channel.list.detail.approvedUser.ApprovedUserImageListRecyclerViewAdapter
import com.example.heysrealprojcet.ui.channel.list.detail.waitingUser.WaitingUserImageListRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
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

      with(binding) {
         btnBookmark.setOnClickListener {
            it.isSelected = it.isSelected != true
            if (it.isSelected) {
               channelAddBookmark(args.channelId)
            } else {
               channelRemoveBookmark(args.channelId)
            }
         }
         allApprovedUser.setOnClickListener { goToApprovedUserList() }
         allWaitingUser.setOnClickListener { goToWaitingUserList() }
         btnJoin.setOnClickListener { }
         tvEdit.setOnClickListener { goToChannelEdit() }
      }
   }

   private fun goToApprovedUserList() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         it.approvedUserList?.let { approvedUserList ->
            findNavController().navigate(
               R.id.action_channelDetailFragment_to_approvedUserListFragment,
               bundleOf("approvedUser" to approvedUserList.toTypedArray()))
         }
      }
   }

   private fun goToWaitingUserList() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         it.waitingUserList?.let { waitingUserList ->
            findNavController().navigate(
               R.id.action_channelDetailFragment_to_waitingUserListFragment,
               bundleOf("waitingUser" to waitingUserList.toTypedArray()))
         }
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
               setChannelRegion()
               setChannelRecruitmentMethod()
               viewModel.channelDetail.value?.online?.let { setChannelForm(it) }
               setLeaderImage()
               setApprovedUserList()
               initBookmark()
               setLinks()

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
         if (viewModel.channelDetail.value?.recruitMethod == ChannelRecruitmentMethod.Immediately.method) {
            "승인없이 바로 참여가능해요."
         } else {
            "승인이 필요해요."
         }
   }

   private fun setChannelForm(form: String) {
      when (form) {
         ChannelForm.Both.form -> {
            binding.channelForm.text = ChannelForm.Both.form + "으로"
         }

         ChannelForm.Offline.form -> {
            binding.channelForm.text = ChannelForm.Offline.form + "으로"
         }

         else -> {
            binding.channelForm.text = ChannelForm.Online.form + "으로"
         }
      }
   }

   // 프로필 퍼센트에 따른 이미지 설정
   private fun setLeaderImage() {
      viewModel.channelDetail.observe(viewLifecycleOwner) {
         when (it.leader.percentage) {
            in 0..49 -> {
               when (UserPreference.gender) {
                  Gender.Male.gender -> binding.leaderImage.setImageResource(R.drawable.ic_male_0)
                  Gender.Female.gender -> binding.leaderImage.setImageResource(R.drawable.ic_female_0)
                  else -> binding.leaderImage.setImageResource(R.drawable.ic_none_0)
               }
            }

            in 50..99 -> {
               when (UserPreference.gender) {
                  Gender.Male.gender -> binding.leaderImage.setImageResource(R.drawable.ic_male_50)
                  Gender.Female.gender -> binding.leaderImage.setImageResource(R.drawable.ic_female_50)
                  else -> binding.leaderImage.setImageResource(R.drawable.ic_none_50)
               }
            }

            100 -> {
               when (UserPreference.gender) {
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
               binding.btnJoin.apply {
                  text = "이미 참여중이에요"
                  isEnabled = false
               }
               binding.tvEdit.visibility = View.VISIBLE
            }

            // 방문자
            RelationshipWithMe.Visiter.relationship -> {
               binding.btnJoin.apply {
                  text = "채널 참여하기"
                  isEnabled = true
               }
               binding.tvEdit.visibility = View.GONE
            }

            RelationshipWithMe.Member.relationship -> {
               binding.btnJoin.apply {
                  text = "이미 참여중이에요"
                  isEnabled = false
               }
               binding.tvEdit.visibility = View.GONE
            }


            else -> {
               binding.btnJoin.apply {
                  text = "승인 대기중이에요"
                  isEnabled = false
               }
               binding.tvEdit.visibility = View.GONE
            }
         }
      }
   }
}