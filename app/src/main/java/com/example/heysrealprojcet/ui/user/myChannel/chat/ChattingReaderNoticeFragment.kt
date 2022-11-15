package com.example.heysrealprojcet.ui.user.myChannel.chat

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChattingReaderNoticeBinding

class ChattingReaderNoticeFragment : Fragment() {
   private lateinit var binding: ChattingReaderNoticeBinding
   private val viewModel: ChattingReaderNoticeViewModel by viewModels()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChattingReaderNoticeBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.edtTitle.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(32))
      binding.edtContent.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(1500))

      binding.btnSave.setOnClickListener { gotoNoticePost() }
   }

   private fun gotoNoticePost() {
      findNavController().navigate(R.id.action_chattingReaderNotice_to_chattingReaderNoticePost)
   }
}