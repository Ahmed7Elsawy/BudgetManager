package com.elsawy.budgetmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [Action::class], version = 1)
@TypeConverters(Converters::class)
abstract class ActionDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao
}