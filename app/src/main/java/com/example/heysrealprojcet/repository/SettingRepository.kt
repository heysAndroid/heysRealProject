package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.SettingApi
import javax.inject.Inject

class SettingRepository @Inject constructor(
    private val settingApi: SettingApi) {
    suspend fun getCategory() = settingApi.getCategory()
}