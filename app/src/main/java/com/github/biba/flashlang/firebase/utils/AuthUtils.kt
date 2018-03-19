package com.github.biba.flashlang.firebase.utils

import com.github.biba.flashlang.firebase.auth.response.AuthResponse
import com.github.biba.flashlang.firebase.auth.response.IAuthResponse
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class AuthUtils {
    companion object {
        fun retrieveResponse(pTask: Task<AuthResult>): IAuthResponse {
            val response = AuthResponse()
            if (pTask.isSuccessful) {
                val user = pTask.result.user!!
                response.user = user
            } else {
                response.error = pTask.exception!!
            }
            return response
        }
    }
}
