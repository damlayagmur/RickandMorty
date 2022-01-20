package com.damlayagmur.rickandmorty.util

import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.layoutInflaterFactory(): LayoutInflater = LayoutInflater.from(context)