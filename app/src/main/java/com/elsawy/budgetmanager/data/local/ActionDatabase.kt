package com.elsawy.budgetmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Action::class], version = 1)
abstract class ActionDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao
}