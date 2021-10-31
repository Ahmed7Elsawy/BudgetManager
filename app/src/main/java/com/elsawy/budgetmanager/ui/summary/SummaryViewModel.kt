package com.elsawy.budgetmanager.ui.summary

import android.util.Log
import androidx.lifecycle.*
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val repository: ActionRepository,
) : ViewModel() {

    fun getIncome(date: Date): LiveData<Double> = liveData {
        val data = repository.getIncomeInTime(date)
        data.collect {
            emit(it)
        }
    }

    fun getPaidUp(date: Date): LiveData<Double> = liveData {
        val data = repository.getPaidUpInTime(date)
        data.collect {
            emit(it)
        }
    }



    var _allActions = MutableLiveData<List<Action>>()
    var allActions: LiveData<List<Action>> = _allActions

    fun getActionsByCategory(category: Category) {
        viewModelScope.launch {

            repository.getActionsByCategory(category).collect {
                _allActions.postValue(it)
                it.forEach { action ->
                    Log.d("SummaryVM", action.toString())
                }
            }
        }
    }


}