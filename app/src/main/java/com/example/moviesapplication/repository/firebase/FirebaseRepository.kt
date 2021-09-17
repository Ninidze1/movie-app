package com.example.moviesapplication.repository.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    fun getUser(): FirebaseUser?
    fun signOut()
    suspend fun signUpUser(email: String, password: String): Task<AuthResult>
    suspend fun signInUser(email: String, password: String): Task<AuthResult>
    suspend fun resetUser(mail: String): Task<Void>

}