package com.example.heysrealprojcet.ui.user.myChannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MyChannelFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class MyChannelFragment : Fragment() {
   private lateinit var binding: MyChannelFragmentBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = MyChannelFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.bell.setOnClickListener { gotoNotification() }
      binding.mail.setOnClickListener { gotoPrivateNotification() }
      binding.btnReader.setOnClickListener { gotoChattingReader() }
   }

   private fun gotoChattingReader() {
      findNavController().navigate(R.id.action_myChannelFragment_to_myChannelCattingReader)
   }

   private fun gotoNotification() {
      findNavController().navigate(R.id.action_myChannelFragment_to_myChannelNotificationFragment)
   }

   private fun gotoPrivateNotification() {
      findNavController().navigate(R.id.action_myChannelFragment_to_myChannelPrivateNotificationFragment)
   }
}