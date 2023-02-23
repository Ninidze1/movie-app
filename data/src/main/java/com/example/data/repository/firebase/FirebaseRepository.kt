package com.example.data.repository.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface FirebaseRepository {

    fun getUserUid(): String?
    fun signOut()
    suspend fun signUpUser(email: String, password: String): Task<AuthResult>
    suspend fun signInUser(email: String, password: String): Task<AuthResult>
    suspend fun resetUser(mail: String): Task<Void>

}