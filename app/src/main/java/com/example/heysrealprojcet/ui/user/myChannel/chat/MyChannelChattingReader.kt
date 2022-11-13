package com.example.heysrealprojcet.ui.user.myChannel.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelChattingReaderBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class MyChannelChattingReader : Fragment() {
   private lateinit var binding: MyChannelChattingReaderBinding

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
      binding = MyChannelChattingReaderBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.notice.setOnClickListener { gotoNotice() }

      binding.btnManage.setOnClickListener { gotoManage() }
   }

   private fun gotoManage() {
      findNavController().navigate(R.id.action_myChannelCattingReader_to_myChannelManageFragment)
   }

   private fun gotoNotice() {
      findNavController().navigate(R.id.action_myChannelCattingReader_to_chattingReaderNotice)
   }
}