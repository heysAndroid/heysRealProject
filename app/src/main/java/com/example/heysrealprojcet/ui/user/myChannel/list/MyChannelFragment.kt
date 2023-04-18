package com.example.heysrealprojcet.ui.user.myChannel.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelFragmentBinding
import com.example.heysrealprojcet.enums.ChannelStatus
import com.example.heysrealprojcet.enums.ChannelType
import com.example.heysrealprojcet.model.Channel
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyChannelFragment : Fragment() {
   private lateinit var binding: MyChannelFragmentBinding
   private lateinit var myChannelItemRecyclerViewAdapter: MyChannelItemRecyclerViewAdapter
   private lateinit var myChannelList: MutableList<Channel>
   private val viewModel by viewModels<MyChannelViewModel>()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MyChannelFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      getMyChannelList()
      makeList()

      myChannelItemRecyclerViewAdapter = MyChannelItemRecyclerViewAdapter(type = myChannelList) { }
      binding.myChannelList.adapter = myChannelItemRecyclerViewAdapter
      binding.myChannelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun getMyChannelList() {
      viewModel.getMyChannelList("Bearer ${UserPreference.accessToken}").observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               Log.w("getmyChannel", "success")
            }

            is NetworkResult.Loading -> {
               Log.w("getmyChannel", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("getmyChannel", response.message.toString())
            }
         }
      }
   }

   private fun makeList() {
      myChannelList = mutableListOf(
         Channel(R.drawable.bg_channel_activity_card, "강남/서초 대학생, 사회초년생 시각 디자인 대외활동 같이해요!", 7, ChannelStatus.New, 100, 3, ChannelType.Activity),
         Channel(R.drawable.bg_channel_contest_card, "강남/서초 에서시각 디자인 공모전 같이해요!", 7, ChannelStatus.Normal, 30, 500, ChannelType.Contest),
         Channel(R.drawable.bg_channel_study_card, "대학생 인공지능 스터디 모임 같이해요!", 250, ChannelStatus.Closed, 50, 1250, ChannelType.Study),
      )
   }
}