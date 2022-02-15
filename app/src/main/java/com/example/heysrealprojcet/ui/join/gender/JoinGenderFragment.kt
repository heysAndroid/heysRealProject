package com.example.heysrealprojcet.ui.join.gender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.heysrealprojcet.databinding.JoinGenderFragmentBinding

class JoinGenderFragment : Fragment() {
   private lateinit var binding: JoinGenderFragmentBinding
   private val viewModel: JoinGenderViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = JoinGenderFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.okButton.setOnClickListener { }

      viewModel.isMale.observe(viewLifecycleOwner, { isMale ->
         if (isMale) {
            binding.female.isSelected = false
            binding.male.isSelected = true
         } else {
            binding.female.isSelected = true
            binding.male.isSelected = false
         }
      })
   }
}