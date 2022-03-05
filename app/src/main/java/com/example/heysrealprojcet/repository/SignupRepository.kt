package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.SignUpApi
import com.example.heysrealprojcet.model.User
import javax.inject.Inject

class SignupRepository @Inject constructor(
    private val signUpApi: SignUpApi) {
    suspend fun signup(user: User) = signUpApi.signup(user)
}