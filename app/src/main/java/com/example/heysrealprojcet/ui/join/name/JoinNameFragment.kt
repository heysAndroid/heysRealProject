package com.example.heysrealprojcet.ui.join.name

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.JoinNameFragmentBinding

class JoinNameFragment : Fragment() {
   private lateinit var binding: JoinNameFragmentBinding
   private val viewModel: JoinNameViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = JoinNameFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.okButton.setOnClickListener { findNavController().navigate(R.id.action_joinNameFragment_to_joinGenderFragment) }
      viewModel.name.asLiveData().observe(viewLifecycleOwner, {
         binding.okButton.isEnabled = it.length == 3 || it.length == 4
      })
   }
}