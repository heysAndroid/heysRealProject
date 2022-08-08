package com.example.heysrealprojcet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.heysrealprojcet.databinding.ChannelJoinFragmentDialogBinding

class ChannelJoinDialogFragment: DialogFragment() {
   private lateinit var binding : ChannelJoinFragmentDialogBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelJoinFragmentDialogBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnSave.setOnClickListener {
         dialog!!.dismiss()
      }
   }
}