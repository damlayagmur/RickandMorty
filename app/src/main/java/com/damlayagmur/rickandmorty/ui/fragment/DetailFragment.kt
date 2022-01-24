package com.damlayagmur.rickandmorty.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.util.showToast

class DetailFragment: Fragment(R.layout.fragment_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showToast(requireContext(),"message")
    }
}