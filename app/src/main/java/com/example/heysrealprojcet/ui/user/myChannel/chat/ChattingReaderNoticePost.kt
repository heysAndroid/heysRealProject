package com.example.heysrealprojcet.ui.user.myChannel.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.heysrealprojcet.databinding.ChattingReaderNoticePostBinding

class ChattingReaderNoticePost : Fragment() {
   private lateinit var binding: ChattingReaderNoticePostBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChattingReaderNoticePostBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)


   }
}