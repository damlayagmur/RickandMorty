package com.damlayagmur.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.damlayagmur.rickandmorty.R
import com.damlayagmur.rickandmorty.databinding.ActivityHomeBinding
import com.damlayagmur.rickandmorty.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.scopes.ActivityScoped


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityHomeBinding::inflate)
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment?
        val navController: NavController = navHostFragment!!.getNavController()
        setupActionBarWithNavController(navController!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp() || super.onSupportNavigateUp()
    }
}