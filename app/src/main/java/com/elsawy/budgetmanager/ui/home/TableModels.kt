package com.elsawy.budgetmanager.ui.home

import androidx.annotation.Nullable

open class Cell(
   @field:Nullable @get:Nullable
   @param:Nullable val data: Any,
)

class ColumnHeader(data: Any?) : Cell(data!!)

class RowHeader(data: Any?) : Cell(data!!)