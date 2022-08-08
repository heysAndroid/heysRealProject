package com.example.heysrealprojcet.ui.main.content.contestActivity.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestActivityDetailFragmentBinding

class ContestActivityDetailFragment : Fragment() {
   private lateinit var binding: ContestActivityDetailFragmentBinding

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ContestActivityDetailFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.goToHeys.setOnClickListener { goToHeys() }

//      if(binding.introduce.lineCount > 5) {
//         binding.layoutExpand.visibility = View.VISIBLE
//         binding.detail.visibility = View.GONE
//      }
//
//      binding.detail.setOnClickListener {
//         if (binding.layoutExpand.visibility == View.VISIBLE) {
//            binding.layoutExpand.visibility = View.GONE
//         } else {
//            binding.layoutExpand.visibility = View.VISIBLE
//         }
//      }
   }

   private fun goToHeys() {
      findNavController().navigate(R.id.action_contestActivityDetailFragment_to_heysChannelListFragment)
   }
}