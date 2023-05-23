package com.example.heysrealprojcet.ui.user.myChannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heysrealprojcet.databinding.ChannelFragmentBinding
import com.example.heysrealprojcet.ui.channel.list.ChannelItemRecyclerViewAdapter

class ChannelFragment : Fragment() {
   private lateinit var binding: ChannelFragmentBinding
   private lateinit var channelItemRecyclerViewAdapter: ChannelItemRecyclerViewAdapter

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      channelItemRecyclerViewAdapter = ChannelItemRecyclerViewAdapter(mutableListOf()) { }
      binding.channelList.adapter = channelItemRecyclerViewAdapter
      binding.channelList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
   }
}