package com.example.heysrealprojcet.ui.join.gender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
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
      binding.okButton.setOnClickListener {
         findNavController().navigate(R.id.action_joinGenderFragment_to_joinAgeFragment)
      }

      viewModel.isMale.observe(viewLifecycleOwner) { isMale ->
         binding.male.isSelected = isMale
         isMale?.let { binding.okButton.isEnabled = true }
      }

      viewModel.isFemale.observe(viewLifecycleOwner) { isFemale ->
         binding.female.isSelected = isFemale
      }

      viewModel.isNonBinary.observe(viewLifecycleOwner) { isNonBinary ->
         binding.nonBinary.isSelected = isNonBinary
      }
   }
}