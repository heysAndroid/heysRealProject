package com.hey.heys.ui.user.myChannel.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hey.heys.R
import com.hey.heys.databinding.NotificationFragmentBinding
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.Notification
import com.hey.heys.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
   private lateinit var binding: NotificationFragmentBinding
   private lateinit var adapter: NotificationViewAdapter
   private val viewModel: NotificationViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = NotificationFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      adapter = context?.let {
         NotificationViewAdapter(it, object : NotificationViewAdapter.onClickListener {
            override fun onClick(noti: Notification) {
               val notificationDialog = NotificationDialog(requireContext(), noti)
               notificationDialog?.onClickListener { channelId ->
                  findNavController().navigate(
                     R.id.action_notificationFragment_to_channelDetailFragment, bundleOf("channelId" to channelId))
               }
               notificationDialog?.show(childFragmentManager, null)
            }

         })
      }!!
      binding.notificationList.adapter = adapter
      binding.notificationList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      getNotificationList()
   }

   private fun getNotificationList() {
      viewModel.getNotification("Bearer ${UserPreference.accessToken}").observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               response.data?.data?.let {
                  adapter.submitList(it.reversed())
               }
            }

            is NetworkResult.Loading -> {
               Log.w("getNoti", "loading")
            }

            is NetworkResult.Error -> {
               Log.w("getNoti", response.message.toString())
            }
         }
      }
   }
}