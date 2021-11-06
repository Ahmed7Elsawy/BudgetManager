package com.elsawy.budgetmanager.ui.summary

import androidx.lifecycle.*
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.elsawy.budgetmanager.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
   private val repository: ActionRepository,
) : ViewModel() {

   fun getIncome(date: Date) = liveData {
      val data = repository.getIncomeInTime(date)
      data.collect {
         emit(it)
      }
   }

   fun getPaidUp(date: Date) = liveData {
      val data = repository.getPaidUpInTime(date)
      data.collect {
         emit(it)
      }
   }

   fun getSavedMoney(date: Date) = liveData {
      val data = repository.getSavedMoneyInTime(date)
      data.collect {
         emit(it)
      }
   }

   fun getPaidActionsInTime(date: Date) = liveData {
      repository.getPaidActionsInTime(date).collect {
         emit(it)
      }
   }

   private val _dateFilterFlow = MutableStateFlow<DateFilter>(WeekFilter())
   var dateFilterFlow: StateFlow<DateFilter> = _dateFilterFlow

   init {
      getAllActionsInTime(dateFilterFlow.value.getDate())
   }

   private var _incomeSum = MutableStateFlow(.0)
   var incomeSum: StateFlow<Double> = _incomeSum

   private var _paidSum = MutableStateFlow(.0)
   var paidSum: StateFlow<Double> = _paidSum

   private var _savedMoney = MutableStateFlow(.0)
   var savedMoney: StateFlow<Double> = _savedMoney

   private var _incomeActions = MutableStateFlow<List<Action>>(emptyList())
   var incomeActions: StateFlow<List<Action>> = _incomeActions

   private var _paidActions = MutableStateFlow<List<Action>>(emptyList())
   var paidActions: StateFlow<List<Action>> = _paidActions

   fun getAllActionsInTime(date: Date) {

      viewModelScope.launch {
         repository.getAllActionsInTime(date)
            .collect { actions ->
               _incomeActions.value = actions.filter { it.category == Category.INCOME }
               _incomeSum.value = _incomeActions.value.map { it.amount }.sum()
//                  actions.filter { it.category == Category.INCOME }.map { it.amount }.sum()
               _paidActions.value = actions.filter { it.category != Category.INCOME }
               _paidSum.value = _paidActions.value.map { it.amount }.sum()
               _savedMoney.value = _incomeSum.value - _paidSum.value
            }
      }
   }

   fun setDateFilter(dateFilter: DateFilter) {
      _dateFilterFlow.value = dateFilter
   }

}