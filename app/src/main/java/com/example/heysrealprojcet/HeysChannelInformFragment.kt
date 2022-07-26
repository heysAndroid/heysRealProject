package com.example.heysrealprojcet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.heysrealprojcet.databinding.HeysChannelInformFragmentBinding

class HeysChannelInformFragment : Fragment() {
   private lateinit var binding: HeysChannelInformFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = HeysChannelInformFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnPurpose.setOnClickListener {
         val customDialog = ChannelPurposeDialogFragment()
         customDialog.show(childFragmentManager, "CustomDialog")

      }
   }
}