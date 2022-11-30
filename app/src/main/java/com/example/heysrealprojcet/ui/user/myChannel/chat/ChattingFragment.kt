package com.example.heysrealprojcet.ui.user.myChannel.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.heysrealprojcet.databinding.ChattingFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage

class ChattingFragment : Fragment() {
   private lateinit var binding: ChattingFragmentBinding

   var serverIP = "tcp://192.168.0.254:1883"

   // 메세지 발행 토픽과 구독 토픽이 서로 달라도 ok
   var topic = "TopicName"

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)

      var mqttClient = MqttClient(serverIP, MqttClient.generateClientId(), null)
      mqttClient.connect()

      binding.sentBtn.setOnClickListener {
         // 서버로 메세지 전송
         Log.d("MQTTService", "Send")
         mqttClient.publish(topic, MqttMessage(binding.chatInput.text.toString().toByteArray()))
      }

      // 구독
      mqttClient.subscribe(topic)
      mqttClient.setCallback(object : MqttCallback {
         override fun connectionLost(cause: Throwable?) {
            Log.w("MQTTService", "connection Lost")
         }

         override fun messageArrived(topic: String?, message: MqttMessage?) {
            Log.d("MQTTService", "Message Arrived: " + message.toString())
         }

         override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Log.d("MQTTService", "Delivery Complete")
         }

      })
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChattingFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
   }
}