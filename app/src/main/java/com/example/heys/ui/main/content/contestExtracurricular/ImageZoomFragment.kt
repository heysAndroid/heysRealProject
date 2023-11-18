package com.example.heys.ui.main.content.contestExtracurricular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.heys.databinding.ImageZoomFragmentBinding

class ImageZoomFragment : Fragment() {
   private lateinit var binding: ImageZoomFragmentBinding
   val args: ImageZoomFragmentArgs by navArgs()

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ImageZoomFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      Glide.with(requireContext()).load(args.imageUri).into(binding.image)
      binding.closeButton.setOnClickListener { findNavController().navigateUp() }
   }
}