package com.elsawy.budgetmanager.repository

import android.util.Log
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class FakeActionRepositoryTest : ActionRepository {

   val actions = mutableListOf<Action>()

   override val allActions: Flow<List<Action>>
      get() = flowOf(actions)

   override suspend fun insertActionItem(action: Action) {
      val balance = actions.maxByOrNull { it.date }?.balance ?: 0.0
      if (action.category == Category.INCOME)
         action.balance = balance + action.amount
      else
         action.balance = balance - action.amount

      actions.add(action)
   }

   override suspend fun getBalance(): Flow<Double> {
      val balance = actions.maxByOrNull { it.date }?.balance ?: 0.0
      return flowOf(balance)
   }

   override suspend fun getActionsByCategory(category: Category): Flow<List<Action>> {
      TODO("Not yet implemented")
   }

   override suspend fun getIncomeInTime(date: Date): Flow<Double> {
      TODO("Not yet implemented")
   }

   override suspend fun getPaidUpInTime(date: Date): Flow<Double> {
      TODO("Not yet implemented")
   }

   override suspend fun getSavedMoneyInTime(date: Date): Flow<Double> {
      TODO("Not yet implemented")
   }

   override suspend fun getPaidActionsInTime(date: Date): Flow<List<Action>> {
      TODO("Not yet implemented")
   }

   override suspend fun getAllActionsInTime(date: Date): Flow<List<Action>> {
      TODO("Not yet implemented")
   }

}