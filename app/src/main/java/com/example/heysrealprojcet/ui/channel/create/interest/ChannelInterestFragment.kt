package com.example.heysrealprojcet.ui.channel.create.interest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelInterestFragmentBinding

class ChannelInterestFragment : Fragment() {
   private lateinit var binding : ChannelInterestFragmentBinding
   private val viewModel: ChannelInterestViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelInterestFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnPreview.setOnClickListener { goToChannelPreview() }
   }

   private fun goToChannelPreview() {
      findNavController().navigate(R.id.action_channelInterestFragment_to_channelPreviewFragment)
   }
}