package com.example.heysrealprojcet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.HeysChannelFreeFragmentBinding

class HeysChannelFreeFragment: Fragment() {
   private lateinit var binding: HeysChannelFreeFragmentBinding
   private val viewModel: HeysChannelFreeViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = HeysChannelFreeFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.isEnabled.observe(viewLifecycleOwner) {
         if (it) {
            var maxText = binding.edtIntroduce.text
            binding.letterCount.text = "${binding.edtIntroduce.length()}"
            binding.edtIntroduce.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_channel_edittext_focused)
            if(binding.edtIntroduce.length() > 40) {
               binding.edtIntroduce.text = maxText
            }
         } else {
            binding.letterCount.text = "0"
            binding.edtIntroduce.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_channel_edittext_normal)
         }
      }
   }

   private fun goToInform() {
      //findNavController().navigate()
   }
}