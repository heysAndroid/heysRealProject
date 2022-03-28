package com.example.heysrealprojcet.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestDetailFragmentBinding

class ContestDetailFragment : Fragment() {
   private lateinit var binding: ContestDetailFragmentBinding

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ContestDetailFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.goToHeys.setOnClickListener { goToHeys() }
   }

   private fun goToHeys() {
      findNavController().navigate(R.id.action_contestDetailFragment_to_heysListFragment)
   }
}