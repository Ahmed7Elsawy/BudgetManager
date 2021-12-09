package com.elsawy.budgetmanager.ui.summary

import android.util.Log
import androidx.lifecycle.*
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.elsawy.budgetmanager.di.IoDispatcher
import com.elsawy.budgetmanager.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
   private val repository: ActionRepository,
   @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

   private val _dateFilterFlow = MutableStateFlow<DateFilter>(MonthFilter())
   var dateFilterFlow: StateFlow<DateFilter> = _dateFilterFlow

   init {
      getAllActionsInTime(dateFilterFlow.value.getDate())
   }

   private var _incomeSum = MutableStateFlow(0)
   var incomeSum: StateFlow<Int> = _incomeSum

   private var _paidSum = MutableStateFlow(0)
   var paidSum: StateFlow<Int> = _paidSum

   private var _savedMoney = MutableStateFlow(0)
   var savedMoney: StateFlow<Int> = _savedMoney

   private var _incomeActions = MutableStateFlow<List<Action>>(emptyList())
   var incomeActions: StateFlow<List<Action>> = _incomeActions

   private var _paidActions = MutableStateFlow<List<Action>>(emptyList())
   var paidActions: StateFlow<List<Action>> = _paidActions

   private fun getAllActionsInTime(date: Date) {
      viewModelScope.launch(dispatcher) {
         repository.getAllActionsInTime(date)
            .collect { actions ->
               _incomeActions.value = actions.filter { it.category == Category.INCOME }
               _incomeSum.value = _incomeActions.value.map { it.amount }.sum().toInt()
               _paidActions.value = actions.filter { it.category != Category.INCOME }
               _paidSum.value = _paidActions.value.map { it.amount }.sum().toInt()
               _savedMoney.value = _incomeSum.value - _paidSum.value
            }
      }
   }

   fun setDateFilter(dateFilter: Int) {
      when (dateFilter) {
         1 -> _dateFilterFlow.value = WeekFilter()
         2 -> _dateFilterFlow.value = MonthFilter()
         3 -> _dateFilterFlow.value = YearFilter()
      }
      getAllActionsInTime(dateFilterFlow.value.getDate())
   }


}