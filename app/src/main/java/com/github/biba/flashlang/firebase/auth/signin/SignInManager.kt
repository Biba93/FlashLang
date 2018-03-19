package com.github.biba.flashlang.firebase.auth.signin

import com.github.biba.flashlang.firebase.auth.IFirebaseUserManager
import com.github.biba.flashlang.firebase.auth.request.EmailAuthInfo
import com.github.biba.flashlang.firebase.auth.request.IAuthRequest
import com.github.biba.flashlang.firebase.auth.response.IAuthResponse
import com.github.biba.flashlang.firebase.utils.AuthUtils
import com.google.firebase.auth.FirebaseAuth

class SignInManager : ISignInManager {

    private val mAuth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    override fun signIn(pAuthRequest: IAuthRequest, pCallback: IFirebaseUserManager.IAuthCallback) {
        val authInfo = pAuthRequest.authInfo
        return when (authInfo) {
            is EmailAuthInfo -> authWithEmail(authInfo, pCallback)
            else -> {
                pCallback.onError(IllegalStateException("No such auth type"))
            }
        }
    }

    private fun authWithEmail(pAuthInfo: EmailAuthInfo, pCallback: IFirebaseUserManager.IAuthCallback) {
        val email = pAuthInfo.email
        val password = pAuthInfo.password
        var response: IAuthResponse

        mAuth.signInWithEmailAndPassword(email, password)
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
