package com.example.heysrealprojcet.ui.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.LogoFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference

class LogoFragment : Fragment() {
   private lateinit var binding: LogoFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      val mWindow = requireActivity().window
      mWindow.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
      binding = LogoFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      Handler(Looper.getMainLooper()).postDelayed({ moveToNext() }, 3000)
   }

   private fun moveToNext() {
      if (UserPreference.isAutoLogin) {
         val intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, "mainHome")
            type = "text/plain"
         }
         startActivity(intent)
         requireActivity().finish()
      } else {
         findNavController().navigate(R.id.action_logoFragment_to_introFragment)
      }
   }
}