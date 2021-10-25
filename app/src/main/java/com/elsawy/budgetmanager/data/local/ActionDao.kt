package com.elsawy.budgetmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface ActionDao {

    @Insert
    fun insertAction(action: Action)

    @Query("SELECT balance FROM actions ORDER BY aid DESC LIMIT 1")
    fun getBalance(): LiveData<Double>


    @Query("SELECT * FROM actions")
    fun getAllActions(): LiveData<List<Action>>

    @Query("SELECT * FROM actions where date >= :end")
    fun getActionsInTime(end:Long): LiveData<List<Action>>

    @Query("SELECT * FROM actions where date >= :category")
    fun getActionsByCategory(category: Category): LiveData<List<Action>>




}