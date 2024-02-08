package com.hey.heys.ui.login.sign_up

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import com.hey.heys.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.sign_up_activity)

      // 상태바 및 아이콘 색상 변경
      window.apply {
         //상태바
         statusBarColor = Color.WHITE
         //상태바 아이콘(true: 검정 / false: 흰색)
         WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true
      }
   }
}