package com.hey.heys.ui.intro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.hey.heys.R
import com.hey.heys.databinding.LogoFragmentBinding
import com.hey.heys.ui.main.MainActivity
import com.hey.heys.util.UserPreference

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
      initDeepLink()
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

   private fun initDeepLink() {
      Log.i("initDeepLink ", "is called!")
      Firebase.dynamicLinks.getDynamicLink(requireActivity().intent)
         .addOnSuccessListener(requireActivity()) { pendingDynamicLinkData ->
            var deepLink: Uri? = null
            Log.w("Deeplink: ", deepLink.toString())
            if (pendingDynamicLinkData != null) {
               deepLink = pendingDynamicLinkData.link
            }

            if (deepLink != null && deepLink.getBooleanQueryParameter("id", false)) {
               val id = deepLink.getQueryParameter("id")

               if (id != null) {
                  // 로그인 안된 유저
                  if (UserPreference.accessToken.isNullOrBlank()) {
                     checkPermission()
                     Handler(Looper.getMainLooper()).postDelayed({ moveToNext() }, 3000)
                  } else {
                     // 로그인
                     Log.w("id::: ", deepLink.lastPathSegment.toString())
                     deepLink.lastPathSegment?.let { goToMain(it, id.toInt()) }
                  }
               }
            } else {
               checkPermission()
               Handler(Looper.getMainLooper()).postDelayed({ moveToNext() }, 3000)
            }
         }
         .addOnFailureListener(requireActivity()) {
            Log.e("deeplink: ", it.message.toString())
         }
   }

   private fun goToMain(path: String, id: Int) {
      val intent = Intent(requireContext(), MainActivity::class.java).apply {
         putExtra(Intent.EXTRA_TEXT, "$path$id")
         type = "text/plain"
      }
      startActivity(intent)
      requireActivity().finish()
   }

   private fun moveToNext() {
      // 자동 로그인
      if (UserPreference.isAutoLogin && permissionList.isEmpty()) {
         val intent = Intent(requireContext(), MainActivity::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, "mainHome")
            type = "text/plain"
         }
         startActivity(intent)
         requireActivity().finish()
      } else {
         lifecycleScope.launchWhenResumed {
            findNavController().navigate(R.id.action_logoFragment_to_permissionFragment)
         }
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