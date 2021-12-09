package com.elsawy.budgetmanager.ui.summary

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.elsawy.budgetmanager.databinding.FragmentSummaryBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.formatter.PercentFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SummaryFragment : Fragment() {

   private val summaryViewModel: SummaryViewModel by viewModels()
   private var _binding: FragmentSummaryBinding? = null
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      _binding = FragmentSummaryBinding.inflate(inflater, container, false)

      binding.lifecycleOwner = requireActivity()
      binding.summaryVM = summaryViewModel
      initPieChart()

      lifecycleScope.launch {
         launch {
            summaryViewModel.incomeActions.collect { actions ->
               setIncomeToBarChart(actions)
            }
         }
         launch {
            summaryViewModel.paidActions.collect { actions ->
               setDataToPieChart(actions)
            }
         }
      }

      return binding.root
   }

   private fun initPieChart() {
      binding.paidPieChart.setUsePercentValues(true)
      binding.paidPieChart.description.text = ""
      //hollow pie chart
      binding.paidPieChart.isDrawHoleEnabled = false
      binding.paidPieChart.setTouchEnabled(false)
      binding.paidPieChart.setDrawEntryLabels(false)
      //adding padding
      binding.paidPieChart.setExtraOffsets(20f, 0f, 20f, 20f)
      binding.paidPieChart.setUsePercentValues(true)
      binding.paidPieChart.isRotationEnabled = false
      binding.paidPieChart.setDrawEntryLabels(false)
      binding.paidPieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
      binding.paidPieChart.legend.isWordWrapEnabled = true

   }

   private fun setDataToPieChart(actions: List<Action>) {
      val mortgageAmount =
         actions.filter { it.category == Category.MORTGAGE }.map { it.amount }.sum()
      val groceriesAmount =
         actions.filter { it.category == Category.GROCERIES }.map { it.amount }.sum()
      val utilitiesAmount =
         actions.filter { it.category == Category.UTILITIES }.map { it.amount }.sum()
      val entertainmentAmount =
         actions.filter { it.category == Category.ENTERTAINMENT }.map { it.amount }.sum()

      binding.paidPieChart.setUsePercentValues(true)
      val dataEntries = ArrayList<PieEntry>()

      dataEntries.add(PieEntry(mortgageAmount.toFloat(), Category.MORTGAGE.name, "aa"))
      dataEntries.add(PieEntry(groceriesAmount.toFloat(),
         Category.GROCERIES.name,
         groceriesAmount))
      dataEntries.add(PieEntry(utilitiesAmount.toFloat(), Category.UTILITIES.name))
      dataEntries.add(PieEntry(entertainmentAmount.toFloat(), Category.ENTERTAINMENT.name))

      val dataSet = PieDataSet(dataEntries, "")
      val data = PieData(dataSet)

      // In Percentage
      data.setValueFormatter(PercentFormatter())
      dataSet.sliceSpace = 2f
      dataSet.setColors(*ColorTemplate.PASTEL_COLORS) //colors = colors
      binding.paidPieChart.data = data
      data.setValueTextSize(15f)
      data.setValueTextColor(Color.WHITE)//requireContext().resources.getColor(R.color.white))
      binding.paidPieChart.setExtraOffsets(20f, 0f, 5f, 5f)
      binding.paidPieChart.animateY(1400, Easing.EaseInOutQuad)

      //create hole in center
      binding.paidPieChart.holeRadius = 60f//58f
      binding.paidPieChart.transparentCircleRadius = 61f//61f
      binding.paidPieChart.isDrawHoleEnabled = true
      binding.paidPieChart.setHoleColor(Color.WHITE)

      //add text in center
      binding.paidPieChart.setDrawCenterText(true);
      binding.paidPieChart.centerText = "Paid Chart"

      binding.paidPieChart.invalidate()

   }

   private fun setIncomeToBarChart(actions: List<Action>) {
      val entries: ArrayList<BarEntry> = ArrayList()
      val xAxisValues = ArrayList<String>()

         val map = actions.groupBy { summaryViewModel.dateFilterFlow.value.getDateName(it.date) }
            .map { it.key to it.value.sumOf { action -> action.amount } }

         for ((index, pair) in map.withIndex()) {
            entries.add(BarEntry(index.toFloat(), pair.second.toFloat()))
         }
         map.forEach {
            xAxisValues.add(it.first)
         }

      val xAxis = binding.incomeBarChart.xAxis
      xAxis.granularity = 1f
      xAxis.isGranularityEnabled = true
      xAxis.setCenterAxisLabels(true)
      xAxis.setDrawGridLines(false)
      xAxis.textSize = 10f

      xAxis.position = XAxis.XAxisPosition.BOTTOM
      xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

      xAxis.labelCount = actions.size
      xAxis.mAxisMaximum = actions.size.toFloat()
      xAxis.setCenterAxisLabels(false)
      xAxis.setAvoidFirstLastClipping(false)

      val barDataSet = BarDataSet(entries, "")
      barDataSet.setColors(*ColorTemplate.PASTEL_COLORS)//COLORFUL_COLORS)
      barDataSet.valueTextSize = 14f

      val data = BarData(barDataSet)
      binding.incomeBarChart.data = data
      //hide grid lines
      binding.incomeBarChart.axisLeft.setDrawGridLines(false)
      binding.incomeBarChart.xAxis.setDrawGridLines(false)
      binding.incomeBarChart.xAxis.setDrawAxisLine(false)
      //remove right y-axis
      binding.incomeBarChart.axisRight.isEnabled = false
      binding.incomeBarChart.axisLeft.isEnabled = true
      binding.incomeBarChart.axisLeft.axisMinimum= 0f//spaceBottom = 2f
      //remove legend
      binding.incomeBarChart.legend.isEnabled = false
      //remove description label
      binding.incomeBarChart.description.isEnabled = false
      //add animation
      binding.incomeBarChart.animateY(3000)
      //draw chart
      binding.incomeBarChart.invalidate()
   }

}