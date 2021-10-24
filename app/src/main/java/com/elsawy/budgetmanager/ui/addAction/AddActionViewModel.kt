package com.elsawy.budgetmanager.ui.addAction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elsawy.ahmed.news.data.provider.PreferencesHelper
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddActionViewModel @ViewModelScoped constructor(
    private val repository: ActionRepository,
    private val sharedPreferences: PreferencesHelper,
) : ViewModel() {

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
    }


}