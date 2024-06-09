package com.hey.heys.ui.user.myChannel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hey.heys.R
import com.hey.heys.databinding.HeysChannelFragmentBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.ui.user.myChannel.allChannel.AllChannelFragment
import com.hey.heys.ui.user.myChannel.list.MyChannelFragment
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
      getNotificationsExist()

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

      binding.imgNotification.setOnClickListener { gotoNotification() }
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

   private fun getNotificationsExist() {
      viewModel.getNotificationExist("Bearer ${UserPreference.accessToken}").observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               val isNewExist = response.data?.data as Boolean
               if (isNewExist) {
                  binding.imgNotification.setImageDrawable(requireContext().getDrawable(R.drawable.ic_noti_with_dot))
               } else {
                  binding.imgNotification.setImageDrawable(requireContext().getDrawable(R.drawable.ic_noti_without_dot))
               }
            }

            is NetworkResult.Loading -> {
               Log.w("notiExist", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("notiExist", response.message.toString())
            }
         }
      }
   }
}