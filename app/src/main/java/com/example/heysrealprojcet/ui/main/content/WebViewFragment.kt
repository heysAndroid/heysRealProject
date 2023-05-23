package com.example.heysrealprojcet.ui.main.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.heysrealprojcet.databinding.WebviewFragmentBinding

class WebViewFragment : Fragment() {
   private lateinit var binding: WebviewFragmentBinding
   private val args: WebViewFragmentArgs by navArgs()

   private val callback = object : OnBackPressedCallback(true) {
      override fun handleOnBackPressed() {
         if (binding.webView.canGoBack()) {
            binding.webView.goBack()
         } else {
            findNavController().navigateUp()
         }
      }
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = WebviewFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      binding.webView.apply {
         webViewClient = WebViewClient()
         settings.javaScriptEnabled = true
         settings.loadWithOverviewMode = true
         settings.useWideViewPort = true
         settings.setSupportZoom(true)
         settings.builtInZoomControls = true
         loadUrl(args.url)
      }

      // 뒤로가기 처리
      requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
   }

}