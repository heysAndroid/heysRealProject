package com.example.heysrealprojcet.ui.channel.create.preview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelPreviewFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class ChannelPreviewFragment : Fragment() {
   private lateinit var binding: ChannelPreviewFragmentBinding

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

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPreviewFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.btnMake.setOnClickListener { goToDetail() }
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_channelPreviewFragment_to_channelDetailFragment)
   }
}