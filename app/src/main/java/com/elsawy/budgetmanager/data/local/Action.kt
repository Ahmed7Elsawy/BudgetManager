package com.elsawy.budgetmanager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "actions_table")
data class Action(
    val date: Long,
    val category: Category,
    val description: String,
    val amount: Double,
    var balance: Double = 0.0,
    @PrimaryKey(autoGenerate = true)
    val aid: Int = 0,
)

