package com.example.heysrealprojcet.ui.intro.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.PermissionFragmentBinding

class PermissionFragment : Fragment() {
   private lateinit var binding: PermissionFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = PermissionFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.okButton.setOnClickListener { goToIntro() }
   }

   private fun goToIntro() {
      findNavController().navigate(R.id.action_permissionFragment_to_introFragment)
   }
}