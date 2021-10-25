package com.elsawy.budgetmanager.Repositories

import androidx.lifecycle.LiveData
import com.elsawy.budgetmanager.data.local.Action
import kotlinx.coroutines.flow.Flow

interface ActionRepository {

    val allActions: Flow<List<Action>>

    suspend fun insertActionItem(action: Action)

}