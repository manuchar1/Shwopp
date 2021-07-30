package com.tbcacademy.shwop.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tbcacademy.shwop.R
import com.tbcacademy.shwop.base.LanguageActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : LanguageActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}