package com.example.heysrealprojcet.ui.user.myChannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelFragmentBinding
import com.example.heysrealprojcet.enums.ChannelStatus
import com.example.heysrealprojcet.enums.ChannelType
import com.example.heysrealprojcet.model.Channel
import com.example.heysrealprojcet.ui.main.MainActivity

class MyChannelFragment : Fragment() {
   private lateinit var binding: MyChannelFragmentBinding
   private lateinit var myChannelItemRecyclerViewAdapter: MyChannelItemRecyclerViewAdapter
   private lateinit var myChannelList: MutableList<Channel>

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MyChannelFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      // recyclerview 불러오기
      makeList()
      myChannelItemRecyclerViewAdapter = MyChannelItemRecyclerViewAdapter(type = myChannelList) { goToChat() }
      binding.myChannelList.adapter = myChannelItemRecyclerViewAdapter
      binding.myChannelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.notification.setOnClickListener { gotoNotification() }
      binding.mail.setOnClickListener { goToMailbox() }
   }

   private fun goToChat() {
      findNavController().navigate(R.id.action_myChannelFragment_to_myChannelCattingReader)
   }

   private fun gotoNotification() {
      findNavController().navigate(R.id.action_myChannelFragment_to_myChannelNotificationFragment)
   }

   private fun goToMailbox() {
      findNavController().navigate(R.id.action_myChannelFragment_to_myChannelPrivateNotificationFragment)
   }

   private fun makeList() {
      myChannelList = mutableListOf(
         Channel(R.drawable.bg_channel_contest_card, "제3회 연구개발특구 AI SPARK 챌린지 팀원 모집해요!", 7, ChannelStatus.New, 100, 3, ChannelType.Contest),
         Channel(R.drawable.bg_channel_activity_card, "마켓팅 대외활동 팀원을 찾습니다!", 7, ChannelStatus.Normal, 30, 500, ChannelType.Activity),
         Channel(R.drawable.bg_channel_study_card, "금융/IT 취업 준비 같이해요!", 250, ChannelStatus.Closed, 50, 1250, ChannelType.Study),
      )
   }
}