package com.example.heysrealprojcet.ui.main.detail.heys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
   }
}