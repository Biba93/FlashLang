package com.github.biba.flashlang.firebase.auth

import com.github.biba.flashlang.domain.models.user.User
import com.google.firebase.auth.FirebaseUser

class UserFactory {

    fun createUser(pUser: FirebaseUser): User {
        return User(pUser)
    }
}
