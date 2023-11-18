package com.example.heys.ui.user.myChannel.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heys.databinding.NotificationFragmentBinding
import com.example.heys.model.network.NetworkResult
import com.example.heys.util.UserPreference
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

      adapter = context?.let { NotificationViewAdapter(it) }!!
      binding.notificationList.adapter = adapter
      binding.notificationList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
      getNotificationList()
   }

   private fun getNotificationList() {
      viewModel.getNotification("Bearer ${UserPreference.accessToken}").observe(viewLifecycleOwner) { response ->
         when (response) {
            is NetworkResult.Success -> {
               response.data?.data?.let {
                  adapter.submitList(it)
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