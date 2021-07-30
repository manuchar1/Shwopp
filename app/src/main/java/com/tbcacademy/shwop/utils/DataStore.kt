package com.tbcacademy.shwop.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import java.lang.RuntimeException
import java.util.*

object DataStore {

    private const val KEY_LANGUAGE = "key_language"
    private const val KEY_TOKEN = "key_token"
    private const val KEY_LAST_TIME_SAVED_CARDS_FETCHED = "key_last_time_saved_cards_fetched"
    private var sharedPreferences: SharedPreferences? = null



    fun initialize(context: Context, sharedPreferences: SharedPreferences) {
        DataStore.sharedPreferences = sharedPreferences
    }

    var language: String
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(KEY_LANGUAGE, value)?.commit()
        }
        get() {
            return sharedPreferences?.getString(KEY_LANGUAGE, Locale.getDefault().language)
                ?: throw RuntimeException("not initialized!!")
        }


    var authToken: String?
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(KEY_TOKEN, value)?.commit()
        }
        get() {
            return (sharedPreferences ?: throw RuntimeException("not initialized!!"))
                .getString(KEY_TOKEN, null)
        }

    var lastTimeSavedCardsFetched: Long
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putLong(KEY_LAST_TIME_SAVED_CARDS_FETCHED, value)?.commit()
        }
        get() {
            return (sharedPreferences ?: throw RuntimeException("not initialized!!"))
                .getLong(KEY_LAST_TIME_SAVED_CARDS_FETCHED, 0)
        }


}