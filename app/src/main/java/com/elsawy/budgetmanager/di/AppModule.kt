package com.elsawy.budgetmanager.di

import android.content.Context
import androidx.room.Room
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.Repositories.ActionRepositoryImpl
import com.elsawy.budgetmanager.data.local.ActionDao
import com.elsawy.budgetmanager.data.local.ActionDatabase
import com.elsawy.budgetmanager.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideActionDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ActionDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideActionDao(
        database: ActionDatabase
    ) = database.actionDao()

    @Provides
    @Singleton
    fun provideActionRepository(
        dao: ActionDao
    ) = ActionRepositoryImpl(dao) as ActionRepository

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher