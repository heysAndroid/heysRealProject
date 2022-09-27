package com.example.heysrealprojcet.ui.user.myChannel.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelNotificationFragmentBinding
import com.example.heysrealprojcet.model.NotificationList

class MyChannelNotificationFragment : Fragment() {
   private lateinit var binding: MyChannelNotificationFragmentBinding
   private lateinit var myChannelNotificationViewAdapter: MyChannelNotificationViewAdapter
   private lateinit var notificationList: MutableList<NotificationList>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MyChannelNotificationFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()
      myChannelNotificationViewAdapter = MyChannelNotificationViewAdapter(notificationList)
      binding.notificationList.adapter = myChannelNotificationViewAdapter
      binding.notificationList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      notificationList = mutableListOf(
         NotificationList(R.drawable.bg_circle_e5e5e5, "추천 알림", "IT 관련 신규 공고가 올라왔어요. 어떤 공고가 올라왔는지 확인해볼까요?", "방금"),
         NotificationList(R.drawable.bg_circle_e5e5e5, "추천 알림", "데이터/인공지능 관련 공고에서 당신을 기다리는 헤이즈가 있어요! 성장하는 청춘을 함께해볼까요?", "방금"),
         NotificationList(R.drawable.bg_circle_e5e5e5, "헤이즈 활동 알림", "IT 관련 신규 공고가 올라왔어요. 어떤 공고가 올라왔는지 확인해볼까요?", "12시간 전"),
         NotificationList(R.drawable.bg_circle_e5e5e5, "추천 알림", "IT 관련 신규 공고가 올라왔어요. 어떤 공고가 올라왔는지 확인해볼까요?", "1일 전"),
         NotificationList(R.drawable.bg_circle_e5e5e5, "헤이즈 활동 알림", "IT 관련 신규 공고가 올라왔어요. 어떤 공고가 올라왔는지 확인해볼까요?", "7일전")
      )
   }
}