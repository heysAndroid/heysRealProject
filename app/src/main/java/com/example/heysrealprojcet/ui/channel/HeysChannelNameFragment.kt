package com.example.heysrealprojcet.ui.channel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.HeysChannelNameFragmentBinding

class HeysChannelNameFragment : Fragment() {
   private lateinit var binding: HeysChannelNameFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = HeysChannelNameFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnNext.setOnClickListener {
         goToInform()
      }
   }

   private fun goToInform() {
      findNavController().navigate(R.id.action_heysChannelNameFragment_to_heysChannelInformFragment)
   }
}