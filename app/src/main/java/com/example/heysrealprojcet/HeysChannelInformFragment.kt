package com.example.heysrealprojcet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.HeysChannelInformFragmentBinding

class HeysChannelInformFragment : Fragment() {
   private lateinit var binding: HeysChannelInformFragmentBinding
   private val viewModel : HeysChannelInformViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = HeysChannelInformFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.isPurpose.observe(viewLifecycleOwner) { purpose ->
         Log.e("태그2", purpose)
         binding.purpose.text = purpose
      }

      binding.btnPurpose.setOnClickListener {
         val customDialog = ChannelPurposeDialogFragment()
         customDialog.show(childFragmentManager, "CustomDialog")
      }

      binding.btnForm.setOnClickListener {
         val customDialog = ChannelFormDialogFragment()
         customDialog.show(childFragmentManager, "CustomDialog")
      }

      binding.btnJoin.setOnClickListener {
         val customDialog = ChannelJoinDialogFragment()
         customDialog.show(childFragmentManager, "CustomDialog")
      }

      binding.btnWay.setOnClickListener {
         val customDialog = ChannelWayDialogFragment()
         customDialog.show(childFragmentManager, "CustomDialog")
      }
   }
}