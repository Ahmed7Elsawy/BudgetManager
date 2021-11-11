package com.elsawy.budgetmanager.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   private lateinit var mainBinding: ActivityMainBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

      mainBinding.fab.setOnClickListener {
         MyDialog().show(supportFragmentManager, "AddActionDialog")
      }
      setupBottomNavigation()
   }

   private fun setupBottomNavigation() {
      mainBinding.bottomNavigationView.background = null
      mainBinding.bottomNavigationView.menu[1].isEnabled = false
      val navController = findNavController(R.id.nav_fragment)
      mainBinding.bottomNavigationView.setupWithNavController(navController)
   }

}