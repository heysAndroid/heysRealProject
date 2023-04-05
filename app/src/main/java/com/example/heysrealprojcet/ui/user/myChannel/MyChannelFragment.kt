package com.example.heysrealprojcet.ui.user.myChannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelFragmentBinding
import com.example.heysrealprojcet.enums.ChannelStatus
import com.example.heysrealprojcet.enums.ChannelType
import com.example.heysrealprojcet.model.Channel

class MyChannelFragment : Fragment() {
   private lateinit var binding: MyChannelFragmentBinding
   private lateinit var myChannelItemRecyclerViewAdapter: MyChannelItemRecyclerViewAdapter
   private lateinit var myChannelList: MutableList<Channel>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MyChannelFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()

      myChannelItemRecyclerViewAdapter = MyChannelItemRecyclerViewAdapter(type = myChannelList) { }
      binding.myChannelList.adapter = myChannelItemRecyclerViewAdapter
      binding.myChannelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      myChannelList = mutableListOf(
         Channel(R.drawable.bg_channel_activity_card, "강남/서초 대학생, 사회초년생 시각 디자인 대외활동 같이해요!", 7, ChannelStatus.New, 100, 3, ChannelType.Activity),
         Channel(R.drawable.bg_channel_contest_card, "강남/서초 에서시각 디자인 공모전 같이해요!", 7, ChannelStatus.Normal, 30, 500, ChannelType.Contest),
         Channel(R.drawable.bg_channel_study_card, "대학생 인공지능 스터디 모임 같이해요!", 250, ChannelStatus.Closed, 50, 1250, ChannelType.Study),
      )
   }
}