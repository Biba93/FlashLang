package com.github.biba.flashlang.firebase.auth.response

import com.google.firebase.auth.FirebaseUser

class AuthResponse : IAuthResponse {

    override var user: FirebaseUser? = null

    override var error: Throwable? = null
}
