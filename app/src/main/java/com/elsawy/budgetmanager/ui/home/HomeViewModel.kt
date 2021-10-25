package com.elsawy.budgetmanager.ui.home

import androidx.lifecycle.*
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelScoped constructor(
    private val repository: ActionRepository,
) : ViewModel() {

    val allActions: LiveData<List<Action>> = repository.allActions.asLiveData()

}