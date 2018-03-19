package com.github.biba.flashlang.firebase.auth.request

data class EmailAuthInfo(val email: String, val password: String) : AuthInfo()
