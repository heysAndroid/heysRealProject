package com.example.heysrealprojcet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.heysrealprojcet.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {

   // FCM 서버에 메세지 전송하면 자동으로 호출되며, 메소드 내의 메세지 처리 가능!
   override fun onMessageReceived(message: RemoteMessage) {
      super.onMessageReceived(message)
      if (message.notification != null) {
         Log.i("onMessageReceived : ", message.notification?.body.toString())
         showFCM(message.notification?.title, message.notification!!.body!!)
      }
   }

   // FCM 서버에 등록되었을 때 호출됨
   // 알림 on, 로그인
   override fun onNewToken(token: String) {
      super.onNewToken(token)
      Log.i("onNewToken : ", token)
   }

   private fun showFCM(title: String?, body: String) {
      // 어떤 화면으로 이동할 건지 정의
      val intent = Intent(this, MainActivity::class.java).apply {
         flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
      }

      val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

      val channelId = "noti_channel"
      val notificationBuilder = NotificationCompat.Builder(this, channelId)
         .setContentTitle(title)
         .setSmallIcon(R.drawable.logo)
         .setContentText(body)
         .setAutoCancel(true)
         .setContentIntent(pendingIntent)

      val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

      // 앱의 알림 채널을 시스템에 등록
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         // id, 이름, 중요도
         val channel = NotificationChannel(channelId, "noti_channel", NotificationManager.IMPORTANCE_DEFAULT)
         notiManager.createNotificationChannel(channel)
      }
      notiManager.notify(0, notificationBuilder.build())
   }
}