package com.tbcacademy.shwop.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.tbcacademy.shwop.utils.Constants.HAS_SESSION
import com.tbcacademy.shwop.utils.Constants.LANGUAGE
import com.tbcacademy.shwop.utils.Constants.TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreference @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("user", Context.MODE_PRIVATE)
    }

    fun saveUserSession(session: Boolean) {
        sharedPreferences.edit().putBoolean(HAS_SESSION, session).apply()
    }

    fun hasSession() = sharedPreferences.getBoolean(HAS_SESSION, false)

    fun saveToken(token:String) {
        sharedPreferences.edit().putString(TOKEN,token).apply()
    }

    fun token() = sharedPreferences.getString(TOKEN,"")

   /* var language: String
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(LANGUAGE, value)?.commit()
        }
        get() {
            return sharedPreferences?.getString(LANGUAGE, Locale.getDefault().language)
                ?: throw RuntimeException("not initialized!!")
        }*/

}