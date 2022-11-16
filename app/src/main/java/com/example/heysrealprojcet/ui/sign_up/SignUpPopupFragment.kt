package com.example.heysrealprojcet.ui.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.heysrealprojcet.databinding.SignUpPopupFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class SignUpPopupFragment : Fragment() {
   private lateinit var binding: SignUpPopupFragmentBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      binding = SignUpPopupFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.nextButton.setOnClickListener {
         val intent = Intent(requireContext(), SignUpActivity::class.java)
         startActivity(intent)
         requireActivity().finish()
      }
   }
}