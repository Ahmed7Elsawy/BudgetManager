package com.elsawy.budgetmanager.utils


import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class DateTest {

   private lateinit var weekFilter: WeekFilter
   private lateinit var monthFilter: MonthFilter
   private lateinit var yearFilter: YearFilter

   @Before
   fun setUp() {
      weekFilter = WeekFilter()
      monthFilter = MonthFilter()
      yearFilter = YearFilter()
   }

   @Test
   fun `convertTimeStampToDate() in the past`() {
      val expected = "23.10.2021"
      val result = convertTimeStampToDate(1635021233000)
      assertThat(result).isEqualTo(expected)
   }

   @Test
   fun `convertTimeStampToDate() in the future`() {
      val expected = "29.07.2023"
      val result = convertTimeStampToDate(1690594033000)
      assertThat(result).isEqualTo(expected)
   }

   @Test
   fun `getDateName() in week return day name`() {
      val expected = "Sat"
      val result = weekFilter.getDateName(1690594033000)

      assertThat(result).isEqualTo(expected)
   }

   @Test
   fun `getDate() in week`() {
      val milliSecondInWeek = 604800000L
      val expected = Date(System.currentTimeMillis() - milliSecondInWeek)
      val result = weekFilter.getDate()
      assertThat(result).isAtLeast(expected)
   }

   @Test
   fun `getDateName() in Month return day-month`() {
      val expected = "29-7"
      val result = monthFilter.getDateName(1690594033000)

      assertThat(result).isEqualTo(expected)
   }

   @Test
   fun `getDate() in Month`() {
      val milliSecondInMonth = 2592000000L
      val expected = Date(System.currentTimeMillis() - milliSecondInMonth)
      val result = monthFilter.getDate()
      assertThat(result).isEqualTo(expected)
   }

   @Test
   fun `getDateName() in year return month-year `() {
      val expected = "7-2023"
      val result = yearFilter.getDateName(1690594033000)

      assertThat(result).isEqualTo(expected)
   }

   @Test
   fun `getDate() in year`() {
      val milliSecondInYear = 31536000000L
      val expected = Date(System.currentTimeMillis() - milliSecondInYear)
      val result = yearFilter.getDate()
      assertThat(result).isEqualTo(expected)
   }

}