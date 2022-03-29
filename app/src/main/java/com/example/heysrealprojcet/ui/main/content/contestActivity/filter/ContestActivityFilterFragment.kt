package com.example.heysrealprojcet.ui.main.content.contestActivity.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.databinding.ContestActivityFilterFragmentBinding

class ContestActivityFilterFragment : Fragment() {
   private lateinit var binding: ContestActivityFilterFragmentBinding
   private val viewModelActivity: ContestActivityFilterViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ContestActivityFilterFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModelActivity
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.btnApply.setOnClickListener { findNavController().navigateUp() }
   }
}