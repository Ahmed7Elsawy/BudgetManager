package com.elsawy.budgetmanager.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.elsawy.budgetmanager.TestCoroutineRule
import com.elsawy.budgetmanager.data.local.Category
import com.elsawy.budgetmanager.repository.FakeActionRepositoryTest
import com.elsawy.budgetmanager.ui.summary.SummaryViewModel
import com.elsawy.budgetmanager.utils.getCalculatedDate
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class SummaryViewModelTest {

   @get:Rule
   var instantTaskExecutorRule = InstantTaskExecutorRule()

   @ExperimentalCoroutinesApi
   @get:Rule
   val testCoroutineRule = TestCoroutineRule()

   @ExperimentalCoroutinesApi
   private val testDispatcher = TestCoroutineDispatcher()

   private lateinit var viewModel: SummaryViewModel

   @Mock
   private lateinit var fakeActionRepository: FakeActionRepositoryTest

   @ExperimentalCoroutinesApi
   @Before
   fun setup() = runBlockingTest {
      fakeActionRepository = FakeActionRepositoryTest()
      viewModel = SummaryViewModel(fakeActionRepository, testDispatcher)
   }

   @ExperimentalCoroutinesApi
   @Test
   fun `getActions in lastWeak`() = runBlockingTest {
      val lastWeakDate = getCalculatedDate(-7)
      val expectedIncomeSum =
         fakeActionRepository.actions.filter { it.date >= lastWeakDate.time && it.category == Category.INCOME }
            .map { it.amount }.sum()
      val expectedPaidSum =
         fakeActionRepository.actions.filter { it.date >= lastWeakDate.time && it.category != Category.INCOME }
            .map { it.amount }.sum()
      val expectedSaved = expectedIncomeSum - expectedPaidSum
      val expectedIncomeActions =
         fakeActionRepository.actions.filter { it.date >= lastWeakDate.time && it.category == Category.INCOME }
      val expectedPaidActions =
         fakeActionRepository.actions.filter { it.date >= lastWeakDate.time && it.category != Category.INCOME }

      viewModel.setDateFilter(1)

      assertThat(viewModel.incomeSum.value).isEqualTo(expectedIncomeSum)
      assertThat(viewModel.paidSum.value).isEqualTo(expectedPaidSum)
      assertThat(viewModel.savedMoney.value).isEqualTo(expectedSaved)
      assertThat(viewModel.incomeActions.value).isEqualTo(expectedIncomeActions)
      assertThat(viewModel.paidActions.value).isEqualTo(expectedPaidActions)

   }

   @ExperimentalCoroutinesApi
   @Test
   fun `getActions in lastMonth`() = runBlockingTest {
      val lastMonthDate = getCalculatedDate(-30)
      val expectedIncomeSum =
         fakeActionRepository.actions.filter { it.date >= lastMonthDate.time && it.category == Category.INCOME }
            .map { it.amount }.sum()
      val expectedPaidSum =
         fakeActionRepository.actions.filter { it.date >= lastMonthDate.time && it.category != Category.INCOME }
            .map { it.amount }.sum()
      val expectedSaved = expectedIncomeSum - expectedPaidSum
      val expectedIncomeActions =
         fakeActionRepository.actions.filter { it.date >= lastMonthDate.time && it.category == Category.INCOME }
      val expectedPaidActions =
         fakeActionRepository.actions.filter { it.date >= lastMonthDate.time && it.category != Category.INCOME }

      viewModel.setDateFilter(2)

      assertThat(viewModel.incomeSum.value).isEqualTo(expectedIncomeSum)
      assertThat(viewModel.paidSum.value).isEqualTo(expectedPaidSum)
      assertThat(viewModel.savedMoney.value).isEqualTo(expectedSaved)
      assertThat(viewModel.incomeActions.value).isEqualTo(expectedIncomeActions)
      assertThat(viewModel.paidActions.value).isEqualTo(expectedPaidActions)
   }

   @ExperimentalCoroutinesApi
   @Test
   fun `getActions in lastYear`() = runBlockingTest {
      val lastYearDate = getCalculatedDate(-365)
      val expectedIncomeSum =
         fakeActionRepository.actions.filter { it.date >= lastYearDate.time && it.category == Category.INCOME }
            .map { it.amount }.sum()
      val expectedPaidSum =
         fakeActionRepository.actions.filter { it.date >= lastYearDate.time && it.category != Category.INCOME }
            .map { it.amount }.sum()
      val expectedSaved = expectedIncomeSum - expectedPaidSum
      val expectedIncomeActions =
         fakeActionRepository.actions.filter { it.date >= lastYearDate.time && it.category == Category.INCOME }
      val expectedPaidActions =
         fakeActionRepository.actions.filter { it.date >= lastYearDate.time && it.category != Category.INCOME }

      viewModel.setDateFilter(3)

      assertThat(viewModel.incomeSum.value).isEqualTo(expectedIncomeSum)
      assertThat(viewModel.paidSum.value).isEqualTo(expectedPaidSum)
      assertThat(viewModel.savedMoney.value).isEqualTo(expectedSaved)
      assertThat(viewModel.incomeActions.value).isEqualTo(expectedIncomeActions)
      assertThat(viewModel.paidActions.value).isEqualTo(expectedPaidActions)
   }


}