package com.example.heysrealprojcet.ui.channel.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelDetailFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class ChannelDetailFragment : Fragment() {
   private lateinit var binding: ChannelDetailFragmentBinding

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
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.heysAll.setOnClickListener { goToMemberList() }

      binding.detail.setOnClickListener {
         if (binding.layoutExpand.visibility == View.VISIBLE) {
            binding.layoutExpand.visibility = View.GONE
            binding.layoutExpand.animate().setDuration(200).rotation(0f)
         } else {
            binding.layoutExpand.visibility = View.VISIBLE
            binding.layoutExpand.animate().setDuration(200).rotation(0f)
         }
      }

      binding.waitingHeysList.setOnClickListener { goToWaitingHeysList() }
   }

   private fun goToMemberList() {
      findNavController().navigate(R.id.action_channelDetailFragment_to_memberHeysListFragment)
   }

   private fun goToWaitingHeysList() {
      findNavController().navigate(R.id.action_channelDetailFragment_to_waitingHeyListFragment)
   }
}