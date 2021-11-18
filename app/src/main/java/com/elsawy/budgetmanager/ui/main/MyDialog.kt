package com.elsawy.budgetmanager.ui.main

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.elsawy.budgetmanager.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.elsawy.budgetmanager.databinding.CustomDialogBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MyDialog : DialogFragment() {

   private val dialogViewModel: DialogViewModel by viewModels()

   private var _binding: CustomDialogBinding? = null
   private val binding get() = _binding!!

   override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
      _binding =
         DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_dialog, null, false)
      binding.lifecycleOwner = requireActivity()
      binding.dialogViewModel = dialogViewModel

      val customDialog = AlertDialog.Builder(requireContext(), 0).create()
      customDialog.apply {
         window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         setView(binding.root)
      }

      binding.dateTextview.setOnClickListener {
         selectDate()
      }

      binding.categoryTextview.setOnClickListener { v: View ->
         showMenu(v, R.menu.category_menu, binding.categoryTextview)
      }

      lifecycleScope.launch {
         repeatOnLifecycle(Lifecycle.State.STARTED) {

            launch {
               dialogViewModel.categoryState.collect {
                  if (it)
                     Toast.makeText(requireContext(), "Select Category", Toast.LENGTH_SHORT).show()
               }
            }

            launch {
               dialogViewModel.amountState.collect {
                  if (it)
                     Toast.makeText(requireContext(), "put the Amount", Toast.LENGTH_SHORT).show()
               }
            }

            launch {
               dialogViewModel.dialogState.collect {
                  it?.let {
                     if (it == DialogState.DISMISS) {
                        customDialog.dismiss()
                     } else if (it == DialogState.CANCEL) {
                        customDialog.cancel()
                     }
                  }
               }
            }

         }
      }

      return customDialog
   }

   private fun selectDate() {
      val calendar = Calendar.getInstance()
      val dateSetListener =
         DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            dialogViewModel.updateDate(calendar.time)
         }
      DatePickerDialog(requireContext(), dateSetListener,
         calendar.get(Calendar.YEAR),
         calendar.get(Calendar.MONTH),
         calendar.get(Calendar.DAY_OF_MONTH)).show()
   }

   private fun showMenu(v: View, @MenuRes menuRes: Int, categoryTextview: TextView) {
      val popup = PopupMenu(requireContext(), v)
      popup.menuInflater.inflate(menuRes, popup.menu)
      popup.setOnMenuItemClickListener { menuItem: MenuItem ->
         categoryTextview.text = menuItem.title
         true
      }
      popup.show()
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

}