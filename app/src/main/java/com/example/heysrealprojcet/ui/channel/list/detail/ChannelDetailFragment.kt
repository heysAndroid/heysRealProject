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
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelDetailFragmentBinding
import com.example.heysrealprojcet.enums.ChannelForm
import com.example.heysrealprojcet.enums.ChannelRecruitmentMethod
import com.example.heysrealprojcet.enums.Gender
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.ui.channel.list.detail.approvedUser.ApprovedUserImageListRecyclerViewAdapter
import com.example.heysrealprojcet.ui.channel.list.detail.waitingUser.WaitingUserImageListRecyclerViewAdapter
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChannelDetailFragment : Fragment() {
   private lateinit var mainActivity: MainActivity
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
      binding.allApprovedUser.setOnClickListener { goToApprovedUserList() }
      binding.allWaitingUser.setOnClickListener { goToWaitingUserList() }

      binding.btnJoin.setOnClickListener { }
   }

   private fun goToApprovedUserList() {
      viewModel.approvedUserList.observe(viewLifecycleOwner) {
         it?.let {
            findNavController().navigate(R.id.action_channelDetailFragment_to_approvedUserListFragment, bundleOf("approvedUser" to it.toTypedArray()))
         }
      }
   }

   private fun goToWaitingUserList() {
      viewModel.waitingUserList.observe(viewLifecycleOwner) {
         it?.let {
            findNavController().navigate(R.id.action_channelDetailFragment_to_waitingUserListFragment, bundleOf("waitingUser" to it.toTypedArray()))
         }
      }
   }

   private fun getChannelDetail(id: Int) {
      viewModel.getChannelDetail("Bearer ${UserPreference.accessToken}", id).observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               viewModel.receiveChannelDetail(response.data?.channelDetail)
               setChannelRegion()
               setChannelRecruitmentMethod()
               viewModel.channelForm.value?.let { setChannelForm(it) }
               setLeaderImage()
               setApprovedUserList()

               // 승인 결정
               if (viewModel.recruitMethod.value == ChannelRecruitmentMethod.Decide.methodEng) {
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

   private fun setChannelRegion() {
      //온라인이면 지역 텍스트 숨기기
      if (viewModel.channelForm.value == ChannelForm.Online.engForm) {
         binding.channelRegion.visibility = View.GONE
      } else {
         binding.channelRegion.visibility = View.VISIBLE
      }
   }

   private fun setChannelRecruitmentMethod() {
      binding.channelRecruitmentMethod.text = if (viewModel.recruitMethod.value == ChannelRecruitmentMethod.Approval.method) {
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
      viewModel.leader.observe(viewLifecycleOwner) { leader ->
         when (leader.percentage) {
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
      viewModel.approvedUserList.value?.let {
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
      viewModel.waitingUserList.value?.let {
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
}