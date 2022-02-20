package com.example.heysrealprojcet.ui.join.age

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.JoinAgeFragmentBinding

class JoinAgeFragment : Fragment() {
   private lateinit var binding: JoinAgeFragmentBinding
   private val viewModel: JoinAgeViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = JoinAgeFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.okButton.setOnClickListener { findNavController().navigate(R.id.action_joinAgeFragment_to_joinInterestFragment) }
      viewModel.age.asLiveData().observe(viewLifecycleOwner, {
         binding.okButton.isEnabled = it.length == 2 || it.length == 3
      })
   }
}