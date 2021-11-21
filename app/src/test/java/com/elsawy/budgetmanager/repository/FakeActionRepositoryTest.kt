package com.elsawy.budgetmanager.repository

import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.elsawy.budgetmanager.utils.getCalculatedDate
import kotlinx.coroutines.flow.*
import java.util.*

class FakeActionRepositoryTest : ActionRepository {

   val actions = mutableListOf<Action>(
      // last Year Actions
      Action(getCalculatedDate(-300).time, Category.INCOME, "description", 1000.0),
      Action(getCalculatedDate(-250).time, Category.GROCERIES, "description", 200.0),
      Action(getCalculatedDate(-240).time, Category.ENTERTAINMENT, "description", 160.0),
      Action(getCalculatedDate(-200).time, Category.INCOME, "description", 800.0),
      Action(getCalculatedDate(-150).time, Category.ENTERTAINMENT, "description", 400.0),
      Action(getCalculatedDate(-100).time, Category.MORTGAGE, "description", 100.0),
      Action(getCalculatedDate(-50).time, Category.UTILITIES, "description", 200.0),

      // last Month Actions
      Action(getCalculatedDate(-25).time, Category.INCOME, "description", 1000.0),
      Action(getCalculatedDate(-23).time, Category.GROCERIES, "description", 200.0),
      Action(getCalculatedDate(-21).time, Category.ENTERTAINMENT, "description", 30.0),
      Action(getCalculatedDate(-15).time, Category.INCOME, "description", 500.0),
      Action(getCalculatedDate(-12).time, Category.ENTERTAINMENT, "description", 200.0),
      Action(getCalculatedDate(-10).time, Category.MORTGAGE, "description", 300.0),
      Action(getCalculatedDate(-9).time, Category.UTILITIES, "description", 100.0),

      // last weak Actions
      Action(getCalculatedDate(-5).time, Category.INCOME, "description", 1000.0),
      Action(getCalculatedDate(-4).time, Category.GROCERIES, "description", 100.0),
      Action(getCalculatedDate(-4).time, Category.ENTERTAINMENT, "description", 60.0),
      Action(getCalculatedDate(-3).time, Category.INCOME, "description", 500.0),
      Action(getCalculatedDate(-3).time, Category.ENTERTAINMENT, "description", 200.0),
      Action(getCalculatedDate(-2).time, Category.MORTGAGE, "description", 300.0),
      Action(getCalculatedDate(-1).time, Category.UTILITIES, "description", 100.0)
   )

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

   override suspend fun getAllActionsInTime(date: Date): Flow<List<Action>> = flow {
      if (actions.isNotEmpty()) {
         val filteredActions = actions.filter { it.date >= date.time }.toList()
         emit(filteredActions)
      }else {
         emit(emptyList())
      }
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

}