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
   var isExpanded = false

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
      binding.btnDetail.setOnClickListener {
         if (isExpanded) {
            binding.channelDescription.maxLines = 5
            binding.btnDetail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0)
            isExpanded = false
         } else {
            // TODO
            // close 리소스 수정
            binding.channelDescription.maxLines = Int.MAX_VALUE
            binding.btnDetail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown_close, 0)
            isExpanded = true
         }
      }
      binding.waitingHeysList.setOnClickListener { goToWaitingHeysList() }
      binding.btnJoin.setOnClickListener { goToMyChannel() }
   }

   private fun goToMemberList() {
      findNavController().navigate(R.id.action_channelDetailFragment_to_memberHeysListFragment)
   }

   private fun goToWaitingHeysList() {
      findNavController().navigate(R.id.action_channelDetailFragment_to_waitingHeyListFragment)
   }

   private fun goToMyChannel() {
      findNavController().navigate(R.id.action_channelDetailFragment_to_myChannelFragment)
   }
}