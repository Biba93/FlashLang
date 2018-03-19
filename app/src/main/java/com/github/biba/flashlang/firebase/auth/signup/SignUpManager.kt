package com.github.biba.flashlang.firebase.auth.signup

import com.github.biba.flashlang.firebase.auth.IFirebaseUserManager
import com.github.biba.flashlang.firebase.auth.request.EmailAuthInfo
import com.github.biba.flashlang.firebase.auth.request.IAuthRequest
import com.github.biba.flashlang.firebase.auth.response.IAuthResponse
import com.github.biba.flashlang.firebase.utils.AuthUtils
import com.google.firebase.auth.FirebaseAuth

class SignUpManager : ISignUpManager {

    private val mAuth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    override fun signUp(pRequest: IAuthRequest, pCallback: IFirebaseUserManager.IAuthCallback) {
        val authInfo = pRequest.authInfo
        return when (authInfo) {
            is EmailAuthInfo -> signUpWithEmail(authInfo, pCallback)
            else -> {
                pCallback.onError(IllegalStateException("No such auth type"))
            }
        }
    }

    private fun signUpWithEmail(pRequestInfo: EmailAuthInfo, pCallback: IFirebaseUserManager.IAuthCallback) {
        val email = pRequestInfo.email
        val password = pRequestInfo.password
        var response: IAuthResponse
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener({ task ->
                    response = AuthUtils.retrieveResponse(task)
                    if (response.error != null) {
                        pCallback.onError(response.error)
                    } else {
                        pCallback.onSuccess(response.user)
                    }
                })
    }


}
