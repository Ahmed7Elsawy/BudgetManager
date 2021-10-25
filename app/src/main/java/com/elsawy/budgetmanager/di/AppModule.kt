package com.elsawy.budgetmanager.di

import android.content.Context
import androidx.room.Room
import com.elsawy.ahmed.news.data.provider.PreferencesHelper
import com.elsawy.budgetmanager.Repositories.ActionRepository
import com.elsawy.budgetmanager.Repositories.ActionRepositoryImpl
import com.elsawy.budgetmanager.data.local.ActionDao
import com.elsawy.budgetmanager.data.local.ActionDatabase
import com.elsawy.budgetmanager.ui.Main.MainActivityViewModel
import com.elsawy.budgetmanager.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideActionDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(context, ActionDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideMainActivityViewModel(
        repository: ActionRepository,
        sharedPreferences: PreferencesHelper,
    ) = MainActivityViewModel(repository, sharedPreferences)

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): PreferencesHelper {
        PreferencesHelper.init(context)
        return PreferencesHelper
    }

    @Singleton
    @Provides
    fun provideActionDao(
        database: ActionDatabase,
    ) = database.actionDao()

    @Singleton
    @Provides
    fun provideActionRepository(
        dao: ActionDao,
    ) = ActionRepositoryImpl(dao) as ActionRepository

}