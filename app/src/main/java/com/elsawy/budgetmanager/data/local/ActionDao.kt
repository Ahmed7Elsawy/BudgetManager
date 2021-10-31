package com.elsawy.budgetmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ActionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAction(action: Action)

    @Query("SELECT * FROM actions_table")
    fun getAllActions(): Flow<List<Action>>

    @Query("SELECT * FROM actions_table where date >= :end")
    fun getActionsInTime(end:Long): LiveData<List<Action>>

    @Query("SELECT * FROM actions_table where category = :category")
    fun getActionsByCategory(category: Category): Flow<List<Action>>

    @Query("SELECT sum(amount) FROM actions_table where category = :category and date >= :date ")
    fun getIncomeInTime(date: Date,category: Category = Category.INCOME): Flow<Double>




}