package com.example.heysrealprojcet.ui.join

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.heysrealprojcet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.join_activity)
   }
}