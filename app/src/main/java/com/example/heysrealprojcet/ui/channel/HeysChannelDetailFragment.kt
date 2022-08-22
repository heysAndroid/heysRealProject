package com.example.heysrealprojcet.ui.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.HeysChannelDetailFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class HeysChannelDetailFragment : Fragment() {
   private lateinit var binding: HeysChannelDetailFragmentBinding

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
      binding = HeysChannelDetailFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      // binding.heysAll.setOnClickListener { goToMemberList() }

      binding.detail.setOnClickListener {
         if (binding.layoutExpand.visibility == View.VISIBLE) {
            binding.layoutExpand.visibility = View.GONE
            binding.layoutExpand.animate().setDuration(200).rotation(0f)
         } else {
            binding.layoutExpand.visibility = View.VISIBLE
            binding.layoutExpand.animate().setDuration(200).rotation(0f)
         }
      }
   }

   private fun goToMemberList() {
      findNavController().navigate(R.id.action_heysChannelDetailFragment_to_memberHeysListFragment)
   }
}