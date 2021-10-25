package com.elsawy.budgetmanager.ui.summary

import android.util.Log
import androidx.lifecycle.*
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SummaryViewModel @ViewModelScoped constructor(
    private val repository: ActionRepository,
) : ViewModel() {

    private var _allActions = MutableLiveData<List<Action>>()
    var allActions: LiveData<List<Action>> = _allActions

     fun getActionsByCategory(category: Category)
     {
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