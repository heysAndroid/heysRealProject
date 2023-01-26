package com.example.heysrealprojcet.ui.main.content.contestActivity.contest.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestDetailLookFragmentBinding

class ContestDetailLookFragment : Fragment() {
   private lateinit var binding: ContestDetailLookFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ContestDetailLookFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.closeButton.setOnClickListener { goToDetail() }
   }

   private fun goToDetail() {
      findNavController().navigate(R.id.action_contestDetailLookFragment_to_contestActivityDetailFragment)
   }
}