package com.elsawy.budgetmanager.Repositories

import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ActionRepository {

    val allActions: Flow<List<Action>>

    suspend fun insertActionItem(action: Action)

    suspend fun getActionsByCategory(category: Category): Flow<List<Action>>

    suspend fun getIncomeInTime(date: Date): Flow<Double>

    suspend fun getPaidUpInTime(date: Date): Flow<Double>

    suspend fun getSavedMoneyInTime(date: Date): Flow<Double>

}