package com.example.heysrealprojcet.ui.user.myChannel.privateNotification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelPrivateNotificationFragmentBinding
import com.example.heysrealprojcet.model.NotificationList

class MyChannelPrivateNotificationFragment : Fragment() {
   private lateinit var binding : MyChannelPrivateNotificationFragmentBinding
   private lateinit var myChannelPrivateNotificationViewAdapter: MyChannelPrivateNotificationViewAdapter
   private lateinit var notificationList: MutableList<NotificationList>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MyChannelPrivateNotificationFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      makeList()
      myChannelPrivateNotificationViewAdapter = MyChannelPrivateNotificationViewAdapter(notificationList)
      binding.notificationList.adapter = myChannelPrivateNotificationViewAdapter
      binding.notificationList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      notificationList = mutableListOf(
         NotificationList(R.drawable.bg_circle_e5e5e5, "남주혁", "남주혁님이 인사했어요.", "방금"),
         NotificationList(R.drawable.bg_circle_e5e5e5, "김태리", "김태리님이 프로필을 보냈어요.", "방금"),
         NotificationList(R.drawable.bg_circle_e5e5e5, "백이진", "IT 관련 신규 공고가 올라왔어요. 어떤 공고가 올라왔는지 확인해볼까요?", "12시간 전"),
         NotificationList(R.drawable.bg_circle_e5e5e5, "나희도", "IT 관련 신규 공고가 올라왔어요. 어떤 공고가 올라왔는지 확인해볼까요?", "1일 전")
      )
   }
}