package com.example.heysrealprojcet.ui.join.interest

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.heysrealprojcet.model.User
import com.example.heysrealprojcet.model.UserCategory
import com.example.heysrealprojcet.repository.SignupRepository
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinInterestViewModel @Inject constructor(
  private val signupRepository: SignupRepository) : ViewModel() {
  private val totalMax = 3
  private var total = MutableStateFlow(0)
  val totalString = MutableStateFlow("${total.value}/3")
  private val interestList = mutableListOf<String>()

  fun onClickInterest(v: View) {
    val item = v.tag.toString()

    if (total.value < totalMax) {
      if (v.isSelected) {
        v.isSelected = false
        total.value -= 1
        interestList.remove(item)
      } else {
        v.isSelected = true
        total.value += 1
        interestList.add(item)
      }
    } else {
      if (v.isSelected) {
        v.isSelected = false
        total.value -= 1
        interestList.remove(item)
      }
    }
    totalString.value = "${total.value}/$totalMax"
  }

  fun signup() {
    val user = User(
      name = UserPreference.name,
      phone = UserPreference.phoneNumber,
      age = UserPreference.age,
      gender = UserPreference.gender,
      password = UserPreference.password,
      userCategories = listOf(UserCategory(preference = 1, categoryId = 2)))

    CoroutineScope(Dispatchers.IO).launch {
      signupRepository.signup(user)?.let { response ->
        if (response.isSuccessful) {
          response.body()?.let {
            Log.w("====== sign up ======", it.toString())
          }
        } else {
          Log.e("======== ERROR ========", response.message().toString())
        }
      }
    }
  }
}