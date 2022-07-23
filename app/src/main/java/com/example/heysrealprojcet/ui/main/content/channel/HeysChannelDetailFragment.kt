package com.example.heysrealprojcet.ui.main.content.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.HeysChannelDetailFragmentBinding

class HeysChannelDetailFragment : Fragment() {
   private lateinit var binding: HeysChannelDetailFragmentBinding

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