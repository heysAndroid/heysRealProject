package com.example.heys.ui.user.myChannel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heys.R
import com.example.heys.databinding.HeysChannelFragmentBinding
import com.example.heys.ui.main.MainActivity
import com.example.heys.ui.user.myChannel.allChannel.AllChannelFragment
import com.example.heys.ui.user.myChannel.list.MyChannelFragment

class HeysChannelFragment : Fragment() {
   private lateinit var binding: HeysChannelFragmentBinding
   private val viewModel: HeysChannelViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = HeysChannelFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.isSelected.observe(viewLifecycleOwner) {
         if (it) {
            binding.myChannel.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_262626))
            binding.tvChannel.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_e1e1e1))
            getMyChannel()
         } else {
            binding.myChannel.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_e1e1e1))
            binding.tvChannel.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_262626))
            getChannel()
         }
      }

      binding.notification.setOnClickListener { gotoNotification() }
   }

   private fun gotoNotification() {
      findNavController().navigate(R.id.action_myChannelFragment_to_notificationFragment)
   }

   private fun getMyChannel() {
      requireActivity().supportFragmentManager.beginTransaction()
         .replace(R.id.heys_channel_frame_layout, MyChannelFragment())
         .commitAllowingStateLoss()
   }

   private fun getChannel() {
      requireActivity().supportFragmentManager.beginTransaction()
         .replace(R.id.heys_channel_frame_layout, AllChannelFragment())
         .commitAllowingStateLoss()
   }
}