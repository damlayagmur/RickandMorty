package com.damlayagmur.rickandmorty.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast

fun ViewGroup.layoutInflaterFactory(): LayoutInflater = LayoutInflater.from(context)

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun showLoading(progressBar: ProgressBar, isVisible:Boolean){
    if(isVisible){
        progressBar.visibility = View.VISIBLE
    }else{
        progressBar.visibility = View.GONE
    }
}