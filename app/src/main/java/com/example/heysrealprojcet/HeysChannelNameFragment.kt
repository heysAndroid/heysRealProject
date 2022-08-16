package com.example.heysrealprojcet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.databinding.HeysChannelNameFragmentBinding

class HeysChannelNameFragment : Fragment() {
   private lateinit var binding : HeysChannelNameFragmentBinding
   private val viewModel: HeysChannelNameViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = HeysChannelNameFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
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