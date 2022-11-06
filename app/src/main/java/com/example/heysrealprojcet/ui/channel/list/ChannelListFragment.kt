package com.example.heysrealprojcet.ui.channel.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelListFragmentBinding
import com.example.heysrealprojcet.enums.ChannelStatus
import com.example.heysrealprojcet.model.Channel
import com.example.heysrealprojcet.ui.main.MainActivity

class ChannelListFragment : Fragment() {
   private lateinit var binding: ChannelListFragmentBinding
   private lateinit var channelItemRecyclerViewAdapter: ChannelItemRecyclerViewAdapter
   private lateinit var channelList: MutableList<Channel>

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

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ChannelListFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      makeList()
      channelItemRecyclerViewAdapter = ChannelItemRecyclerViewAdapter(type = channelList) { goToChannelDetail() }
      binding.heysList.adapter = channelItemRecyclerViewAdapter
      binding.heysList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

      binding.makeChannel.setOnClickListener {
         goToChannelList()
      }
   }

   private fun makeList() {
      channelList = mutableListOf(
         Channel(R.drawable.bg_sample_image_crop, "수도권 팀원 \n모집해요!", 7, ChannelStatus.New, 100, 3),
         Channel(R.drawable.bg_sample_image_crop, "같이 도전 하실분 구합니다.", 7, ChannelStatus.Normal, 30, 500),
         Channel(R.drawable.bg_sample_image_crop, "천안 팀원 구해요", 250, ChannelStatus.Closed, 50, 1250),
      )
   }

   private fun goToChannelList() {
      findNavController().navigate(R.id.action_channelListFragment_to_channelNameFragment)
   }

   private fun goToChannelDetail() {
      findNavController().navigate(R.id.action_channelListFragment_to_channelDetailFragment)
   }
}