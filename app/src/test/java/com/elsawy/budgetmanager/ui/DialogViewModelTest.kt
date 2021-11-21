package com.elsawy.budgetmanager.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.elsawy.budgetmanager.TestCoroutineRule
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.elsawy.budgetmanager.repository.FakeActionRepositoryTest
import com.elsawy.budgetmanager.ui.main.DialogState
import com.elsawy.budgetmanager.ui.main.DialogViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DialogViewModelTest {

   @get:Rule
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   @ExperimentalCoroutinesApi
   @get:Rule
   val testCoroutineRule = TestCoroutineRule()

   private lateinit var viewModel: DialogViewModel
   private lateinit var fakeActionRepository: FakeActionRepositoryTest

   @Before
   fun setup() {
      fakeActionRepository = FakeActionRepositoryTest()
      viewModel = DialogViewModel(fakeActionRepository)
   }

   @ExperimentalCoroutinesApi
   @Test
   fun `onSaveClicked() with right action return DialogState DISMISS, true categoryState and amountState `() = runBlockingTest {

      val expectedAction = Action(12333222222L, Category.INCOME, "DES", 1000.0, 1000.0)
      viewModel.date.value.time = 12333222222L
      viewModel.category.value = Category.INCOME
      viewModel.description.value = "DES"
      viewModel.amount.value = 1000.0

      viewModel.onSaveClicked()

      val resultAction = fakeActionRepository.allActions.test {
         assertThat(awaitItem()).contains(expectedAction)
         cancelAndIgnoreRemainingEvents()
      }
      assertThat(viewModel.dialogState.value).isEqualTo(DialogState.DISMISS)
      assertThat(viewModel.categoryState.value).isEqualTo(false)
      assertThat(viewModel.amountState.value).isEqualTo(false)
   }

   @Test
   fun `onSaveClicked() with invalid Category action return true categoryState and false amountState `() {

      viewModel.date.value.time = 123332222222L
      viewModel.description.value = "DES"
      viewModel.amount.value = 1000.0

      viewModel.onSaveClicked()

      assertThat(viewModel.categoryState.value).isEqualTo(true)
      assertThat(viewModel.amountState.value).isEqualTo(false)

   }

   @Test
   fun `onSaveClicked() with invalid Amount action return false categoryState and true amountState `() {

      viewModel.date.value.time = 123332222222L
      viewModel.category.value = Category.INCOME
      viewModel.description.value = "DES"
//      viewModel.amount.value = 1000.0

      viewModel.onSaveClicked()

      assertThat(viewModel.categoryState.value).isEqualTo(false)
      assertThat(viewModel.amountState.value).isEqualTo(true)
   }

   @Test
   fun `onSaveClicked() with invalid Amount and category return true categoryState and true amountState`() {

      viewModel.date.value.time = 123332222222L
//      viewModel.category.value = Category.INCOME
      viewModel.description.value = "DES"
//      viewModel.amount.value = 1000.0

      viewModel.onSaveClicked()

      assertThat(viewModel.categoryState.value).isEqualTo(true)
      assertThat(viewModel.amountState.value).isEqualTo(true)
   }

}