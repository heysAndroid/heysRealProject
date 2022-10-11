package com.example.heysrealprojcet.ui.user.channel.waitingChannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.WaitingChannelListFragmentBinding
import com.example.heysrealprojcet.enums.ChannelStatus
import com.example.heysrealprojcet.model.Channel

class WaitingChannelListFragment : Fragment() {
   private lateinit var binding: WaitingChannelListFragmentBinding
   private lateinit var engagedChannelItemRecyclerViewAdapter: WaitingChannelItemRecyclerViewAdapter
   private lateinit var channelList: MutableList<Channel>


   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = WaitingChannelListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      makeList()
      engagedChannelItemRecyclerViewAdapter = WaitingChannelItemRecyclerViewAdapter(channel = channelList) {
         val bottomSheet = ChannelCancelBottomSheet(requireContext())
         bottomSheet.show(childFragmentManager, bottomSheet.tag)
      }
      binding.channelList.adapter = engagedChannelItemRecyclerViewAdapter
      binding.channelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }

   private fun makeList() {
      channelList = mutableListOf(
         Channel(R.drawable.bg_sample_image_crop, "수도권 팀원 \n모집해요!", 7, ChannelStatus.New, 100, 3),
         Channel(R.drawable.bg_sample_image_crop, "같이 도전 하실분 구합니다.", 7, ChannelStatus.Normal, 30, 500),
         Channel(R.drawable.bg_sample_image_crop, "천안 팀원 구해요", 250, ChannelStatus.Closed, 50, 1250),
      )
   }
}