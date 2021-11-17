package com.elsawy.budgetmanager.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ActionDaoAndroidTest {

   @get:Rule
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   @ExperimentalCoroutinesApi
   @get:Rule
   val testCoroutineRule = TestCoroutineRule()

   private lateinit var actionDao: ActionDao
   private lateinit var database: ActionDatabase

   @Before
   fun createDb() {
      val context = ApplicationProvider.getApplicationContext<Context>()
      database = Room.inMemoryDatabaseBuilder(
         context, ActionDatabase::class.java).build()
      actionDao = database.actionDao()
   }

   @After
   @Throws(IOException::class)
   fun closeDb() {
      database.close()
   }

   @ExperimentalCoroutinesApi
   @Test
   fun insertOneAction() = runBlockingTest {

      val action = Action(125809920L, Category.INCOME, "desc", 100.0, 100.0, 1)

      actionDao.insertAction(action)

      actionDao.getAllActions().test {
         assertThat(awaitItem()).contains(action)
         cancelAndIgnoreRemainingEvents()
      }
   }

   @ExperimentalCoroutinesApi
   @Test
   fun insertManyActions() = runBlockingTest {

      val action1 = Action(1258099420L, Category.INCOME, "desc", 100.0, 100.0, 1)
      val action2 = Action(1258343920L, Category.ENTERTAINMENT, "desc", 10.0, 90.0, 2)
      val action3 = Action(1258092240L, Category.GROCERIES, "desc", 20.0, 80.0, 3)

      actionDao.insertAction(action1)
      actionDao.insertAction(action2)
      actionDao.insertAction(action3)

      actionDao.getAllActions().test {
         assertThat(awaitItem()).containsExactly(action1,action2,action3)
         cancelAndIgnoreRemainingEvents()
      }
   }

}

