package com.tbcacademy.shwop.base

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.data.UserPreference
import com.tbcacademy.shwop.utils.DataStore
import com.tbcacademy.shwop.utils.updateLocale


abstract class LanguageActivity : AppCompatActivity() {

    private lateinit var loadingView: View

    private lateinit var contentView: ContentFrameLayout

    private var loading = false

   // private lateinit var preferences: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setTheme(R.style.Theme_PokemonCards)
      //  preferences = UserPreference(this)
        contentView = findViewById<ContentFrameLayout>(android.R.id.content)
        //loadingView = layoutInflater.inflate(R.layout.dialog_loading, contentView, false)
    }

    override fun attachBaseContext(newBase: Context?) {
      //  preferences = UserPreference(this)
        val newLangContext = newBase?.let { updateLocale(it, DataStore.language) }
        super.attachBaseContext(newLangContext)
    }

  /*  protected fun showDialog(@StringRes title: Int, @StringRes message: Int) {
        showDialog(title, getString(message))
    }*/

 /*   protected fun showDialog(@StringRes title: Int, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(
              //  R.string.common_Ok
            ) { dialog, _ -> dialog.dismiss() }
            .setCancelable(true)
            .show()
    }*/

    fun showLoading() {
        if (loading) return
        contentView.addView(loadingView)
        loading = true
    }

    fun hideLoading() {
        if (!loading) return
        contentView.removeView(loadingView)
        loading = false
    }


}