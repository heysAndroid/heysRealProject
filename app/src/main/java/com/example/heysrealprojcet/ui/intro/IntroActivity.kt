package com.example.heysrealprojcet.ui.intro

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.example.heysrealprojcet.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.intro_activity)

      // 상태바 및 아이콘 색상 변경
      window.apply {
         //상태바
         statusBarColor = Color.WHITE
         //상태바 아이콘(true: 검정 / false: 흰색)
         WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true
      }
   }
}