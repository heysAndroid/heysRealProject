package com.example.heysrealprojcet.ui.channel.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.HeysChannelInformFragmentBinding
import com.example.heysrealprojcet.ui.channel.dialog.ChannelCapacityDialog
import com.example.heysrealprojcet.ui.channel.dialog.ChannelFormDialog
import com.example.heysrealprojcet.ui.channel.dialog.ChannelPurposeDialog
import com.example.heysrealprojcet.ui.channel.dialog.ChannelRecruitmentMethodDialog
import com.example.heysrealprojcet.ui.channel.dialog.viewModel.ChannelFormDialogViewModel
import com.example.heysrealprojcet.ui.channel.dialog.viewModel.ChannelPurposeDialogViewModel
import com.example.heysrealprojcet.ui.channel.dialog.viewModel.ChannelRecruitmentMethodDialogViewModel
import com.example.heysrealprojcet.ui.channel.viewModel.HeysChannelInformViewModel
import com.example.heysrealprojcet.ui.main.MainActivity

class HeysChannelInformFragment : Fragment() {
   private lateinit var binding: HeysChannelInformFragmentBinding
   private val viewModel: HeysChannelInformViewModel by viewModels()

   private val purposeDialogViewModel: ChannelPurposeDialogViewModel by viewModels()
   private val formDialogViewModel: ChannelFormDialogViewModel by viewModels()
   private val recruitmentMethodDialogViewModel: ChannelRecruitmentMethodDialogViewModel by viewModels()

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
      binding = HeysChannelInformFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.purposeContainer.setOnClickListener {
         val purposeDialog = ChannelPurposeDialog(requireContext(), purposeDialogViewModel)
         purposeDialog.setOnOKClickListener { content ->
            binding.purpose.text = content
            binding.purpose.setTextColor(ContextCompat.getColor(context!!, R.color.color_53c740))
         }
         purposeDialog.show()
      }

      binding.formContainer.setOnClickListener {
         val formDialog = ChannelFormDialog(requireContext(), formDialogViewModel)
         formDialog.setOnOKClickListener { content ->
            binding.form.text = content
            binding.form.setTextColor(ContextCompat.getColor(context!!, R.color.color_53c740))
         }
         formDialog.show()
      }

      binding.capacityContainer.setOnClickListener {
         val capacityDialog = ChannelCapacityDialog(requireContext())
         capacityDialog.setOnOKClickListener { content ->
            binding.capacity.text = "최대 ${content}명"
            binding.capacity.setTextColor(ContextCompat.getColor(context!!, R.color.color_53c740))
         }
         capacityDialog.show()
      }

      binding.recruitmentMethodContainer.setOnClickListener {
         val recruitmentMethodDialog = ChannelRecruitmentMethodDialog(requireContext(), recruitmentMethodDialogViewModel)
         recruitmentMethodDialog.setOnOKClickListener { content ->
            binding.recruitmentMethod.text = content
            binding.recruitmentMethod.setTextColor(ContextCompat.getColor(context!!, R.color.color_53c740))
         }
         recruitmentMethodDialog.show()
      }

      binding.btnNext.setOnClickListener {
         goToName()
      }
   }

   private fun goToName() {
      findNavController().navigate(R.id.action_heysChannelInformFragment_to_heysChannelFreeFragment)
   }
}

