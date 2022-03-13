package com.example.heysrealprojcet.ui.intro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.heysrealprojcet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.intro_activity)
   }
}