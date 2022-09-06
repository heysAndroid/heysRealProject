package com.example.heysrealprojcet.ui.channel.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.heysrealprojcet.databinding.HeysChannelChatFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class HeysChannelChatFragment : Fragment() {
   private lateinit var binding: HeysChannelChatFragmentBinding

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
      binding = HeysChannelChatFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }
}