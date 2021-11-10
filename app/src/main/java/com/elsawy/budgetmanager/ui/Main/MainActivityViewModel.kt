package com.elsawy.budgetmanager.ui.Main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsawy.ahmed.news.data.provider.PreferencesHelper
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
   private val repository: ActionRepository,
   private val sharedPreferences: PreferencesHelper,
) : ViewModel() {

   private val _dateLiveData = MutableLiveData<Date>()
   var dateLiveData = _dateLiveData

   init {
      _dateLiveData.value = Date(System.currentTimeMillis())
   }

   fun updateDate(date: Date) {
      _dateLiveData.value = date
   }

   fun insertAction(action: Action) {
      var balance = sharedPreferences.getBalance()
      if (action.category == Category.INCOME)
         balance += action.amount
      else
         balance -= action.amount
      action.balance = balance
      sharedPreferences.setBalance(balance)
      viewModelScope.launch(Dispatchers.IO) {
         repository.insertActionItem(action)
      }
      Log.d("ActionVM", action.toString())
   }


}