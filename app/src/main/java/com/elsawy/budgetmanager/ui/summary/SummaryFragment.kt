package com.elsawy.budgetmanager.ui.summary

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.PercentFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SummaryFragment : Fragment() {

   private val summaryViewModel: SummaryViewModel by viewModels()
   private lateinit var incomeTextView: TextView
   private lateinit var paidUpTextView: TextView
   private lateinit var savedMoneyTextView: TextView
   private lateinit var paidPieChart: PieChart
   private lateinit var incomeBarChart: BarChart

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View? {
      val view = inflater.inflate(R.layout.fragment_summary, container, false)

      incomeTextView = view.findViewById(R.id.income_textview)
      paidUpTextView = view.findViewById(R.id.paid_up_textview)
      savedMoneyTextView = view.findViewById(R.id.saved_textview)

      paidPieChart = view.findViewById(R.id.paid_pie_chart)
      incomeBarChart = view.findViewById(R.id.income_bar_chart)

      initPieChart()
      setDataToPieChart(emptyList())
      setIncomeToBarChart(emptyList())

      return view
   }

   override fun onActivityCreated(savedInstanceState: Bundle?) {
      super.onActivityCreated(savedInstanceState)

      val date = Date(1630717008000)

      summaryViewModel.getAllActionsInTime(date)

      lifecycleScope.launch {

         launch {
            summaryViewModel.incomeSum.collect { income ->
               Log.d("Summary income var", income.toString())
               incomeTextView.text = "your income in this time is $income."
            }
         }
         launch {
            summaryViewModel.incomeActions.collect { actions ->
               setIncomeToBarChart(actions)
            }
         }
         launch {
            summaryViewModel.paidSum.collect { paid ->
               Log.d("Summary paid up", paid.toString())
               paidUpTextView.text = "you spent $paid in this time."
            }
         }
         launch {
            summaryViewModel.savedMoney.collect { saved ->
               Log.d("Summary savedMoney", saved.toString())
               savedMoneyTextView.text = "you saved $saved in this time."
            }
         }
         launch {
            summaryViewModel.paidActions.collect { actions ->
               setDataToPieChart(actions)
            }
         }
      }
//      summaryViewModel.getIncome(date).observe(viewLifecycleOwner) { income ->
//         Log.d("Summary income", income.toString())
//         incomeTextView.text = "your income in this time is $income."
//      }

//      summaryViewModel.getPaidUp(date).observe(viewLifecycleOwner) { paid ->
//         Log.d("Summary paid up", paid.toString())
//         paidUpTextView.text = "you spent $paid in this time."
//      }

//         summaryViewModel.getSavedMoney(date).observe(viewLifecycleOwner) { saved ->
//            Log.d("Summary savedMoney", saved.toString())
//            savedMoneyTextView.text = "you saved $saved in this time."
//         }

//         summaryViewModel.getPaidActionsInTime(date).observe(viewLifecycleOwner) { actions ->
//            setDataToPieChart(actions)
//         }

   }


   private fun initPieChart() {
      paidPieChart.setUsePercentValues(true)
      paidPieChart.description.text = ""
      //hollow pie chart
      paidPieChart.isDrawHoleEnabled = false
      paidPieChart.setTouchEnabled(false)
      paidPieChart.setDrawEntryLabels(false)
      //adding padding
      paidPieChart.setExtraOffsets(20f, 0f, 20f, 20f)
      paidPieChart.setUsePercentValues(true)
      paidPieChart.isRotationEnabled = false
      paidPieChart.setDrawEntryLabels(false)
      paidPieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
      paidPieChart.legend.isWordWrapEnabled = true

   }

   private fun setDataToPieChart(actions: List<Action>) {
      val totalAmount = actions.map { it.amount }.sum()
      val mortgageAmount =
         actions.filter { it.category == Category.MORTGAGE }.map { it.amount }.sum()
      val groceriesAmount =
         actions.filter { it.category == Category.GROCERIES }.map { it.amount }.sum()
      val utilitiesAmount =
         actions.filter { it.category == Category.UTILITIES }.map { it.amount }.sum()
      val entertainmentAmount =
         actions.filter { it.category == Category.ENTERTAINMENT }.map { it.amount }.sum()

      paidPieChart.setUsePercentValues(true)
      val dataEntries = ArrayList<PieEntry>()

      dataEntries.add(PieEntry(mortgageAmount.toFloat(), Category.MORTGAGE.name, "aa"))
      dataEntries.add(PieEntry(groceriesAmount.toFloat(),
         Category.GROCERIES.name,
         groceriesAmount))
      dataEntries.add(PieEntry(utilitiesAmount.toFloat(), Category.UTILITIES.name))
      dataEntries.add(PieEntry(entertainmentAmount.toFloat(), Category.ENTERTAINMENT.name))

      val colors: ArrayList<Int> = ArrayList()
      colors.add(Color.parseColor("#4DD0E1"))
      colors.add(Color.parseColor("#FFF176"))
      colors.add(Color.parseColor("#FF8A65"))
      colors.add(Color.parseColor("#FF5722"))

      val dataSet = PieDataSet(dataEntries, "")
      val data = PieData(dataSet)

      // In Percentage
      data.setValueFormatter(PercentFormatter())
      dataSet.sliceSpace = 3f
      dataSet.colors = colors
      paidPieChart.data = data
      data.setValueTextSize(15f)
      paidPieChart.setExtraOffsets(5f, 10f, 5f, 5f)
      paidPieChart.animateY(1400, Easing.EaseInOutQuad)

      //create hole in center
      paidPieChart.holeRadius = 58f
      paidPieChart.transparentCircleRadius = 61f
      paidPieChart.isDrawHoleEnabled = true
      paidPieChart.setHoleColor(Color.WHITE)

      //add text in center
      paidPieChart.setDrawCenterText(true);
      paidPieChart.centerText = "Paid Chart"

      paidPieChart.invalidate()

   }

   private fun setIncomeToBarChart(actions: List<Action>) {
      val entries: ArrayList<BarEntry> = ArrayList()
      for ((index, action) in actions.withIndex()) {
         entries.add(BarEntry(index.toFloat(), action.amount.toFloat()))
      }

      val labels = ArrayList<String>()
      actions.forEach {
         labels.add(it.date.toString())
      }

      val barDataSet = BarDataSet(entries, "")
      barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)


      val data = BarData(barDataSet)
      incomeBarChart.data = data
      //hide grid lines
      incomeBarChart.axisLeft.setDrawGridLines(false)
      incomeBarChart.xAxis.setDrawGridLines(false)
      incomeBarChart.xAxis.setDrawAxisLine(false)
      //remove right y-axis
      incomeBarChart.axisRight.isEnabled = false
      //remove legend
      incomeBarChart.legend.isEnabled = false
      //remove description label
      incomeBarChart.description.isEnabled = false
      //add animation
      incomeBarChart.animateY(3000)
      //draw chart
      incomeBarChart.invalidate()
   }

}