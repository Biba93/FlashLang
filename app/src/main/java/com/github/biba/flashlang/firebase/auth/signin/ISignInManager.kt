package com.github.biba.flashlang.firebase.auth.signin

import com.github.biba.flashlang.firebase.auth.IFirebaseUserManager
import com.github.biba.flashlang.firebase.auth.request.IAuthRequest

interface ISignInManager {

    fun signIn(pAuthRequest: IAuthRequest, pCallback: IFirebaseUserManager.IAuthCallback)

}
