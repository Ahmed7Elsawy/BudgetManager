package com.elsawy.budgetmanager.ui.addAction

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MenuRes
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddActionActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var dateTextView: TextView
    private lateinit var categoryTextview: TextView
    @Inject lateinit var addActionViewModel: AddActionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_action)

        dateTextView = findViewById(R.id.date_textview)
        dateTextView.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

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
            DatePickerDialog(this@AddActionActivity, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        categoryTextview = findViewById(R.id.category_textview)
        categoryTextview.setOnClickListener { v: View ->
            showMenu(v, R.menu.category_menu)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_action) {
            Toast.makeText(applicationContext, "saved", Toast.LENGTH_LONG).show()
            addActionViewModel.insertAction(Action(11L, Category.INCOME, "desc", 21.5))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View?) {
        TODO("Not yet implemented")
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            categoryTextview.text = menuItem.title
            true
        }
        popup.show()
    }

}