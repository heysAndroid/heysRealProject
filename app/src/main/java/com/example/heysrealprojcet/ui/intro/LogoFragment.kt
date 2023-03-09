package com.example.heysrealprojcet.ui.intro

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.LogoFragmentBinding

class LogoFragment : Fragment() {
   private lateinit var binding: LogoFragmentBinding

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = LogoFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         activity?.window?.insetsController?.hide(WindowInsets.Type.statusBars())
      } else {
         activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
      }

      Handler(Looper.getMainLooper()).postDelayed({
                                                     findNavController().navigate(R.id.action_logoFragment_to_introFragment)
                                                  }, 3000)
   }

   override fun onDestroyView() {
      super.onDestroyView()

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         activity?.window?.insetsController?.show(WindowInsets.Type.statusBars())
      } else {
         activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      }
   }
}