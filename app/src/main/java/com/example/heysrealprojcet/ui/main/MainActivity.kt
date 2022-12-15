package com.example.heysrealprojcet.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   private lateinit var binding: MainActivityBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = MainActivityBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val intentText = intent.getStringExtra(Intent.EXTRA_TEXT)
      Log.w("intentText, ", intentText.toString())
      intentText?.let { setNavigationGraph(it) }
   }

   fun hideBottomNavigation(state: Boolean) {
      if (state) binding.bottomNavigation.visibility = View.GONE
      else binding.bottomNavigation.visibility = View.VISIBLE
   }

   private fun setNavigationGraph(intent: String) {
      val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
      val navController = navHostFragment.navController
      val navGraph = navController.navInflater.inflate(R.navigation.sign_up_navigation)

      if (intent == "login") {
         navGraph.startDestination = R.id.signInPhoneFragment
      } else {
         navGraph.startDestination = R.id.signUpPhoneFragment
      }
      navHostFragment.navController.graph = navGraph
      initNavigation()
   }

   private fun initNavigation() {
      NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.navHostMain))
      // 아이콘이 태마색으로 변경되는 것 막음
      binding.bottomNavigation.itemIconTintList = null
   }
}