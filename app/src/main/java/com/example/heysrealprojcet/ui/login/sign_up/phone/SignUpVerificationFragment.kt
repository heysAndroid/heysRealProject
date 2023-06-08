package com.example.heysrealprojcet.ui.login.sign_up.phone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignUpPhoneVerificationFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity

class SignUpVerificationFragment : Fragment() {
   private lateinit var binding: SignUpPhoneVerificationFragmentBinding
   private val viewModel: SignUpVerificationViewModel by viewModels()

   //   private lateinit var firebaseAuth: FirebaseAuth
   private var storedVerificationId = ""
//   private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

//   private val callbacks by lazy {
//      object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//         override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            Toast.makeText(requireContext(), "인증코드가 전송되었습니다. 90초 이내에 입력해주세요", Toast.LENGTH_LONG).show()
//            verifyPhoneNumberWithCode(credential)
//         }
//
//         @SuppressLint("LongLogTag")
//         override fun onVerificationFailed(e: FirebaseException) {
//            Log.w("=== onVerificationFailed ===", e)
//            Toast.makeText(requireContext(), "휴대폰 인증에 실패했습니다.", Toast.LENGTH_LONG).show()
//         }
//
//         override fun onCodeSent(verficationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//            Log.d("=== onCodeSent ===", verficationId)
//            storedVerificationId = verficationId
//            resendToken = token
//         }
//      }
//   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpPhoneVerificationFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
//      firebaseAuth = Firebase.auth
      binding.okButton.isEnabled = true
      //initViewModelCallback()
      return binding.root
   }

//   @SuppressLint("LongLogTag")
//   private fun initViewModelCallback() {
//      with(viewModel) {
//         requestPhoneAuth.observe(viewLifecycleOwner) {
//            if (it) {
//               viewModel.phoneAuthNumber = ""
//               val phoneNumber = "+82" + UserPreference.phoneNumber.substring(1)
//               startPhoneNumberVerification(phoneNumber)
//               Log.w("=== phoneNum ===", phoneNumber)
//            } else {
//               Toast.makeText(requireContext(), "전화번호를 입력해주세요", Toast.LENGTH_LONG).show()
//            }
//         }
//
//         requestResendPhoneAuth.observe(viewLifecycleOwner) {
//            if (it) {
//               viewModel.phoneAuthNumber = ""
//               val phoneNumber = "+82" + UserPreference.phoneNumber.substring(1)
//               resendVerificationCode(phoneNumber, resendToken)
//            }
//         }
//
//         is6Digit.observe(viewLifecycleOwner) {
//            if (it) {
//               Log.w("=== StoredVerification ID ===", storedVerificationId)
//               Log.w("=== Verification Number ===", viewModel.verificationNumber.value)
//               val phoneCredential = PhoneAuthProvider.getCredential(storedVerificationId, viewModel.verificationNumber.value)
//               verifyPhoneNumberWithCode(phoneCredential)
//            } else {
//               binding.okButton.isEnabled = false
//            }
//         }
//      }
//   }

//   private fun startPhoneNumberVerification(phoneNumber: String) {
//      val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//         .setPhoneNumber(phoneNumber)
//         .setTimeout(120L, TimeUnit.SECONDS)
//         .setActivity(requireActivity())
//         .setCallbacks(callbacks)
//         .build()
//      PhoneAuthProvider.verifyPhoneNumber(options)
//   }

//   private fun resendVerificationCode(phoneNumber: String, token: PhoneAuthProvider.ForceResendingToken?) {
//      val optionsBuilder = PhoneAuthOptions.newBuilder(firebaseAuth)
//         .setPhoneNumber(phoneNumber)
//         .setTimeout(120L, TimeUnit.SECONDS)
//         .setActivity(requireActivity())
//         .setCallbacks(callbacks)
//
//      if (token != null) {
//         optionsBuilder.setForceResendingToken(token)
//      }
//      PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
//   }

//   @SuppressLint("LongLogTag")
//   private fun verifyPhoneNumberWithCode(phoneAuthCredential: PhoneAuthCredential) {
//      Firebase.auth.signInWithCredential(phoneAuthCredential)
//         .addOnCompleteListener(requireActivity()) { task ->
//            if (task.isSuccessful) {
//               Log.d("=== signInWithCredential ===", "success")
//               binding.okButton.isEnabled = true
//            } else {
//               Log.w("=== verify Error ===", task.exception.toString())
//               Toast.makeText(requireContext(), "인증 번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
//            }
//         }
//   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      viewModel.phoneNumber.value = arguments?.getString("phoneNumber")
      if (viewModel.phoneNumber.value!!.isNotEmpty()) {
         viewModel.timerStart()
      }
      binding.okButton.setOnClickListener { goToJoinPassword() }

      // 화면 들어오자마자 키보드 보이기
      val inputMethodManager =
         requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      binding.phoneInput.requestFocus()
      inputMethodManager.showSoftInput(binding.phoneInput, 0)
   }

   private fun goToJoinPassword() {
      findNavController().navigate(R.id.action_signUpVerificationFragment_to_signUpPasswordFragment)
   }
}