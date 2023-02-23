package com.example.data.repository.firebase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    FirebaseRepository {

    override fun getUserUid() = firebaseAuth.currentUser?.uid
    override fun signOut() = firebaseAuth.signOut()

    override suspend fun signUpUser(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    override suspend fun signInUser(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    override suspend fun resetUser(mail: String) = firebaseAuth.sendPasswordResetEmail(mail)

}