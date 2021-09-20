package com.example.moviesapplication.user_state

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.moviesapplication.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedSharedPref @Inject constructor(@ApplicationContext private val context: Context) {

    private val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    private val specificFileToStore = "passcode_state"

    private val sharedPref: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(context, specificFileToStore, masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }

    fun writePasscode(passcode: MutableList<Int>) {
        val passcodeToStr = passcode.joinToString(",")
        val editable = sharedPref.edit()
        editable.putString(Constants.PASS_KEY, passcodeToStr)
        editable.apply()
    }

    fun readPasscode(): String {
        return sharedPref.getString(Constants.PASS_KEY, Constants.UNAUTHORISED).toString()
    }

    fun deletePasscode() {
        val editable = sharedPref.edit()
        editable.clear()
        editable.apply()
    }

}