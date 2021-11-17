package com.elsawy.budgetmanager.ui.main

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DialogViewModel @Inject constructor(
   private val repository: ActionRepository,
) : ViewModel() {

   private val _date = MutableStateFlow(Date(System.currentTimeMillis()))
   var date = _date

   private val _amount = MutableStateFlow(.0)
   var amount = _amount

   private val _description = MutableStateFlow("")
   var description = _description

   private val _category = MutableStateFlow<Category?>(null)
   var category = _category

   private val _categoryState = MutableLiveData<Boolean>()
   val categoryState = _categoryState

   private val _amountState = MutableLiveData<Boolean>()
   val amountState = _amountState

   private val _dialogState = MutableLiveData<DialogState>()
   val dialogState = _dialogState

   fun updateDate(date: Date) {
      _date.value = date
   }

   fun updateAmount(editable: Editable) {
      if (editable.isNotEmpty())
         _amount.value = editable.toString().toDouble()
      else
         _amount.value = .0
   }

   fun updateDescription(editable: Editable) {
      _description.value = editable.toString()
   }

   fun updateCategory(editable: Editable) {
      _category.value = Category.valueOf(editable.toString().uppercase())
   }

   private fun dismissDialog() {
      _dialogState.value = DialogState.DISMISS
   }

   fun cancelDialog() {
      DialogState.CANCEL
   }

   fun onSaveClicked() {
      if (isCategoryValid() && isAmountValid()) {
         val action = Action(date.value.time, category.value!!, description.value, amount.value)
         insertAction(action)
         dismissDialog()
      } else {
         if (!isCategoryValid()) {
            categoryState.value = true
         }
         if (!isAmountValid()) {
            amountState.value = true
         }
      }
   }

   private fun insertAction(action: Action) {

      viewModelScope.launch(Dispatchers.IO) {
         var balance = 0.0
         repository.getBalance().take(1).collect {
            balance = it
         }

         if (action.category == Category.INCOME)
            balance += action.amount
         else
            balance -= action.amount
         action.balance = balance
         repository.insertActionItem(action)
      }
   }

   fun convertTimeStampToDate(date: Date): String {
      val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.US)
      return sdf.format(date)
   }

   private fun isCategoryValid() = category.value != null

   private fun isAmountValid() = amount.value != .0

}

enum class DialogState {
   DISMISS,
   CANCEL
}