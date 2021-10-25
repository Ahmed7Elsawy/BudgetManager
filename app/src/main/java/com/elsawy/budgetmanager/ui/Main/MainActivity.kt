package com.elsawy.budgetmanager.ui.Main

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            launchCustomDialog()
        }

        setupBottomNavigation()

    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.get(1).isEnabled = false
        val navController = findNavController(R.id.nav_fragment)
        bottomNavigationView.setupWithNavController(navController)
    }


    private fun launchCustomDialog() {
        val customLayout =
            LayoutInflater.from(this@MainActivity).inflate(R.layout.custom_dialog, null)

        val dateTextView = customLayout.findViewById<TextView>(R.id.date_textview)
        dateTextView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
        val categoryTextview = customLayout.findViewById<TextView>(R.id.category_textview)
        val descriptionEditText = customLayout.findViewById<TextView>(R.id.description_edittext)
        val amountEdittext = customLayout.findViewById<TextView>(R.id.amount_edittext)

        val calendar = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd.MM.yyyy" // mention the format you need
                val simpleDateFormat = SimpleDateFormat(myFormat, Locale.US)
                dateTextView.text = simpleDateFormat.format(calendar.time)
            }

        dateTextView.setOnClickListener {
            DatePickerDialog(this@MainActivity, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        categoryTextview.setOnClickListener { v: View ->
            showMenu(v, R.menu.category_menu,categoryTextview)
        }

        val builder = AlertDialog.Builder(this@MainActivity)
            .setView(customLayout)
            .setPositiveButton("Save") { dialogInterface, _ ->

                val action = Action(calendar.timeInMillis,
                    Category.valueOf(categoryTextview.text.toString().toUpperCase()),
                    descriptionEditText.text.toString(),
                    amountEdittext.text.toString().toDouble()
                )

                Log.d("Action",action.toString())
                mainActivityViewModel.insertAction(action)
                // Dismiss Dialog
                dialogInterface.dismiss()

                Toast.makeText(this@MainActivity, "message", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ -> dialogInterface.cancel() }
        builder.show()
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int, categoryTextview: TextView) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            categoryTextview.text = menuItem.title
            true
        }
        popup.show()
    }

}