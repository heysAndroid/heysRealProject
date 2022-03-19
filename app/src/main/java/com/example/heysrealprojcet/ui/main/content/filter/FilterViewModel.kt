package com.example.heysrealprojcet.ui.main.content.filter

import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class FilterViewModel: ViewModel() {
    private val interestMax = 2
    private val activityMax = 1
    private val timeMax = 1

    private var interestTotal = MutableStateFlow(0)
    private var activityTotal = MutableStateFlow(0)
    private var timeTotal = MutableStateFlow(0)


    val interestTotalString = MutableStateFlow("${interestTotal.value}/3")

    private val interestArray = mutableListOf<String>()
    private val activityArray = mutableListOf<String>()
    private val timeArray = mutableListOf<String>()


    fun onClickInterest(v: View) {
        val item = v.tag.toString()

        if (interestTotal.value < interestMax) {
            if (v.isSelected) {
                v.isSelected = false
                interestTotal.value -= 1
                interestArray.remove(item)
            } else {
                v.isSelected = true
                interestTotal.value += 1
                interestArray.add(item)
            }
        } else {
            if (v.isSelected) {
                v.isSelected = false
                interestTotal.value -= 1
                interestArray.remove(item)
            }
        }
        interestTotalString.value = "${interestTotal.value}/$interestMax"
    }

    fun onClickActivity(v: View) {
        val item = v.tag.toString()

        if(activityTotal.value < activityMax) {
            if (v.isSelected) {
                v.isSelected = false
                activityTotal.value -= 1
                activityArray.remove(item)
            } else {
                v.isSelected = true
                activityTotal.value += 1
                activityArray.add(item)
            }
        } else {
            if (v.isSelected) {
                v.isSelected = false
                activityTotal.value -= 1
                activityArray.remove(item)
            }
        }
    }

    fun onClickTime(v: View) {

    }
}