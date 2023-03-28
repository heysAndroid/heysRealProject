package com.example.heysrealprojcet.ui.intro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.LogoFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference

class LogoFragment : Fragment() {
   private lateinit var binding: LogoFragmentBinding
   private lateinit var permissions: Array<String>
   private lateinit var permissionList: ArrayList<String>

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = LogoFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      // fragment statusBar 숨기기
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
      } else {
         requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
         )
      }

      permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.CALL_PHONE)
      } else {
         arrayOf(Manifest.permission.CALL_PHONE)
      }

      checkPermission()

      Handler(Looper.getMainLooper()).postDelayed({ moveToNext() }, 3000)
   }

   override fun onDestroyView() {
      super.onDestroyView()

      // fragment statusBar 보이기
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
         requireActivity().window.insetsController?.show(WindowInsets.Type.statusBars())
      } else {
         requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
      }
   }

   private fun moveToNext() {
      // 자동 로그인 기능
      if (UserPreference.isAutoLogin && permissionList.isEmpty()) {
         val intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, "mainHome")
            type = "text/plain"
         }
         startActivity(intent)
         requireActivity().finish()
      } else {
         findNavController().navigate(R.id.action_logoFragment_to_permissionFragment)
      }
   }

   private fun checkPermission(): Boolean {
      permissionList = arrayListOf()
      for (pm in permissions) {
         if (ContextCompat.checkSelfPermission(requireContext(), pm) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(pm)
         }
      }
      return permissionList.isEmpty()
   }
}