package com.elsawy.budgetmanager.di

import android.content.Context
import androidx.room.Room
import com.elsawy.budgetmanager.data.local.ActionDatabase
import com.elsawy.budgetmanager.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideActionDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ActionDatabase::class.java, DATABASE_NAME).build()


    @Singleton
    @Provides
    fun provideActionDao(
        database: ActionDatabase
    ) = database.actionDao()
}