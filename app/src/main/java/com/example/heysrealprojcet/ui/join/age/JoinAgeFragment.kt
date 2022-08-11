package com.example.heysrealprojcet.ui.join.age

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
      binding.lifecycleOwner = this
      binding.okButton.setOnClickListener { goToInterest() }

      viewModel.isEnabled.observe(viewLifecycleOwner) {
         if (it) {
            binding.ageEditText.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edittext_focused)
         } else {
            binding.ageEditText.background = ContextCompat.getDrawable(requireContext(), R.drawable.bg_edittext_disabled)
         }
      }
   }

   private fun goToInterest() {
      //findNavController().navigate(R.id.action_joinAgeFragment_to_joinInterestFragment)
   }
}