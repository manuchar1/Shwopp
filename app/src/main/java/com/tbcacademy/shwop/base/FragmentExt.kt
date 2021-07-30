package com.tbcacademy.shwop.base

import android.graphics.Color
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showProgress
import com.google.android.material.snackbar.Snackbar
import com.tbcacademy.shwop.R

fun Fragment.snackbar(text: String) {
    Snackbar.make(
        requireView(),
        text,
        Snackbar.LENGTH_LONG
    ).show()

}

fun Fragment.showProgressButton(textView: TextView) {

    bindProgressButton(textView)
    textView.attachTextChangeAnimator()

    textView.showProgress {
        buttonTextRes = R.string.loading
        progressColor = Color.WHITE

    }
}