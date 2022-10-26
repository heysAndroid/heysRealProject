package com.example.heysrealprojcet.ui.channel.create.inform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelInformFragmentBinding
import com.example.heysrealprojcet.ui.channel.dialog.capacity.ChannelCapacityDialog
import com.example.heysrealprojcet.ui.channel.dialog.capacity.ChannelCapacityDialogViewModel
import com.example.heysrealprojcet.ui.channel.dialog.form.ChannelFormDialog
import com.example.heysrealprojcet.ui.channel.dialog.purpose.ChannelPurposeDialog
import com.example.heysrealprojcet.ui.channel.dialog.recruitmentMethod.ChannelRecruitmentMethodDialog
import com.example.heysrealprojcet.ui.main.MainActivity

class ChannelInformFragment : Fragment() {
   private lateinit var binding: ChannelInformFragmentBinding
   private val viewModel: ChannelInformViewModel by viewModels()
   private val capacityDialogViewModel: ChannelCapacityDialogViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelInformFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.purposeContainer.setOnClickListener {
         val purposeDialog = ChannelPurposeDialog()
         purposeDialog.setOnOKClickListener { content ->
            binding.purpose.text = content
            binding.purpose.setTextColor(ContextCompat.getColor(context!!, R.color.color_53c740))
         }
         purposeDialog.show(childFragmentManager, null)
      }

      binding.formContainer.setOnClickListener {
         val formDialog = ChannelFormDialog()
         formDialog.setOnOKClickListener { content ->
            binding.form.text = content
            binding.form.setTextColor(ContextCompat.getColor(context!!, R.color.color_53c740))
         }
         formDialog.show(childFragmentManager, null)
      }

      binding.capacityContainer.setOnClickListener {
         val capacityDialog = ChannelCapacityDialog(requireContext(), capacityDialogViewModel)
         capacityDialog.setOnOKClickListener { content ->
            binding.capacity.text = "최대 ${content}명"
            binding.capacity.setTextColor(ContextCompat.getColor(context!!, R.color.color_53c740))
         }
         capacityDialog.show()
      }

      binding.recruitmentMethodContainer.setOnClickListener {
         val recruitmentMethodDialog = ChannelRecruitmentMethodDialog()
         recruitmentMethodDialog.setOnOKClickListener { content ->
            binding.recruitmentMethod.text = content
            binding.recruitmentMethod.setTextColor(ContextCompat.getColor(context!!, R.color.color_53c740))
         }
         recruitmentMethodDialog.show(childFragmentManager, null)
      }

      binding.btnNext.setOnClickListener { goToName() }
   }

   private fun goToName() {
      findNavController().navigate(R.id.action_heysChannelInformFragment_to_heysChannelFreeFragment)
   }
}

