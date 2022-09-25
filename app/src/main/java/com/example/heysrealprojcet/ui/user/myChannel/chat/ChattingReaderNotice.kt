package com.example.heysrealprojcet.ui.user.myChannel.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChattingReaderNoticeBinding

class ChattingReaderNotice : Fragment() {
   private lateinit var binding: ChattingReaderNoticeBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChattingReaderNoticeBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.btnSave.setOnClickListener { gotoNoticePost() }
   }

   private fun gotoNoticePost() {
      findNavController().navigate(R.id.action_chattingReaderNotice_to_chattingReaderNoticePost)
   }
}