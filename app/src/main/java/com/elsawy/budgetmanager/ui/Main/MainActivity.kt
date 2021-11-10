package com.elsawy.budgetmanager.ui.Main

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.MenuRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   private val mainActivityViewModel: MainActivityViewModel by viewModels()

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
      val customLayout: View =
         LayoutInflater.from(this@MainActivity).inflate(R.layout.custom_dialog, null, false)
      val customDialog = AlertDialog.Builder(this, 0).create()
      customDialog.apply {
         window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         setView(customLayout)
      }.show()


      val dateTextView = customLayout.findViewById<TextView>(R.id.date_textview)
      val categoryTextview = customLayout.findViewById<TextView>(R.id.category_textview)
      val descriptionEditText = customLayout.findViewById<TextView>(R.id.description_edittext)
      val amountEdittext = customLayout.findViewById<TextView>(R.id.amount_edittext)
      val saveButton = customLayout.findViewById<MaterialButton>(R.id.save_dialog_btn)
      val cancelButton = customLayout.findViewById<MaterialButton>(R.id.cancel_dialog_btn)

      var selectedDate: Date = Date()
      mainActivityViewModel.dateLiveData.observe(this) { date ->
         val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
         dateTextView.text = simpleDateFormat.format(date)
         selectedDate = date
      }

      dateTextView.setOnClickListener {
         selectDate()
      }
      categoryTextview.setOnClickListener { v: View ->
         showMenu(v, R.menu.category_menu, categoryTextview)
      }

      saveButton.setOnClickListener {
         val description = descriptionEditText.text.toString()
         val amount = amountEdittext.text.toString()
         val category = categoryTextview.text.toString()

         if (isCategoryValid(category) && isAmountValid(amount)) {
            val action = Action(selectedDate.time,
               Category.valueOf(category.uppercase()),
               description,
               amount.toDouble())

            Log.d("Action", action.toString())
            mainActivityViewModel.insertAction(action)
            // Dismiss Dialog
            customDialog.dismiss()
         } else {
            if (!isAmountValid(amount))
               Toast.makeText(this@MainActivity, "put the Amount", Toast.LENGTH_SHORT).show()
            if (!isCategoryValid(category))
               Toast.makeText(this@MainActivity, "Select Category", Toast.LENGTH_SHORT).show()
         }
      }

      cancelButton.setOnClickListener {
         customDialog.cancel()
      }

   }

   private fun selectDate() {
      val calendar = Calendar.getInstance()
      val dateSetListener =
         DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            mainActivityViewModel.updateDate(calendar.time)
         }
      DatePickerDialog(this@MainActivity, dateSetListener,
         calendar.get(Calendar.YEAR),
         calendar.get(Calendar.MONTH),
         calendar.get(Calendar.DAY_OF_MONTH)).show()
   }

   private fun isCategoryValid(category: String) = category != getString(R.string.category)

   private fun isAmountValid(amount: String) = amount.isNotEmpty()

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