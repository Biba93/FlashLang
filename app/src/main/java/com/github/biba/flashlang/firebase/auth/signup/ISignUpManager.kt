package com.github.biba.flashlang.firebase.auth.signup

import com.github.biba.flashlang.firebase.auth.IFirebaseUserManager
import com.github.biba.flashlang.firebase.auth.request.IAuthRequest

interface ISignUpManager {

    fun signUp(pRequest: IAuthRequest, pCallback: IFirebaseUserManager.IAuthCallback)
}
