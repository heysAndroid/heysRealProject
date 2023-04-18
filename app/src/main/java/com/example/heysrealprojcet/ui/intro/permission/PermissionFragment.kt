package com.example.heysrealprojcet.ui.intro.permission

import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.PermissionFragmentBinding
import com.example.heysrealprojcet.util.UserPreference
import kotlin.system.exitProcess

class PermissionFragment : Fragment() {
   private lateinit var binding: PermissionFragmentBinding

   private lateinit var permissions: Array<String>
   private lateinit var permissionList: ArrayList<String>

   private val requestPermissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
         val deniedList: List<String> = result.filter {
            !it.value
         }.map {
            it.key
         }

         when {
            deniedList.isNotEmpty() -> {
               checkRequestPermission()
               binding.okButton.isEnabled = false
            }
            else -> {
               binding.okButton.isEnabled = true
            }
         }
      }

   override fun onResume() {
      super.onResume()
      checkPermission()

      binding.okButton.isEnabled = permissionList.isEmpty()
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = PermissionFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         binding.layoutNotification.isVisible = true
         permissions = arrayOf(POST_NOTIFICATIONS, CALL_PHONE)
      } else {
         binding.layoutNotification.isVisible = false
         permissions = arrayOf(CALL_PHONE)
      }

      permissionCheck()

      binding.okButton.setOnClickListener { goToIntro() }
   }

   private fun permissionCheck() {
      if (!checkPermission()) {
         requestPermissionLauncher.launch(permissionList.toTypedArray())
         binding.okButton.isEnabled = false
      } else {
         binding.okButton.isEnabled = true
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

   private fun checkRequestPermission() {
      if (!checkPermission()) {
         permissionDialog()
      }
   }

   private fun permissionDialog() {
      AlertDialog.Builder(requireContext()).apply {
         setMessage("권한을 모두 허용해야만 서비스를 이용할 수 있어요.")
         setNegativeButton("닫기") { _, _ ->
            exitProcess(0)
         }
         setPositiveButton("확인") { _, _ ->
            startActivity(
               Intent(
                  Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                  Uri.fromParts("package", requireActivity().packageName, null)
               )
            )
         }
      }.show()
   }

   private fun goToIntro() {
//      if (UserPreference.isAutoLogin) {
//         val intent = Intent(requireContext(), MainActivity::class.java).apply {
//            putExtra(Intent.EXTRA_TEXT, "mainHome")
//            type = "text/plain"
//         }
//         startActivity(intent)
//         requireActivity().finish()
//      } else {
      UserPreference.init()
      findNavController().navigate(R.id.action_permissionFragment_to_introFragment)
//      }
   }
}