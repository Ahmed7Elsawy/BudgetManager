package com.elsawy.budgetmanager.ui.home

import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.utils.convertTimeStampToDate


// return index list of actions list
fun actionsToRowHeaders(actions: List<Action>): List<RowHeader> {
   return actions.mapIndexed { index, _ ->
      RowHeader(index + 1)
   }.toList()
}

fun getColumnHeaders(): List<ColumnHeader> {
   return listOf(
      ColumnHeader("Date"),
      ColumnHeader("Category"),
      ColumnHeader("Amount"),
      ColumnHeader("Balance"),
      ColumnHeader("Description")
   )
}

fun actionsToCells(actions: List<Action>): List<List<Cell>> {
   return actions.map {  action ->
      listOf(
         Cell(convertTimeStampToDate(action.date)),
         Cell(action.category.value),
         Cell(action.amount),
         Cell(action.balance),
         Cell(action.description),
         )
   }.toList()
}
