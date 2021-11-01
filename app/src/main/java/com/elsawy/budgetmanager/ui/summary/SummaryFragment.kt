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
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.data.local.Category
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import android.icu.number.Precision.currency

import com.github.mikephil.charting.utils.ViewPortHandler




@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private val summaryViewModel: SummaryViewModel by viewModels()
    private lateinit var incomeTextView: TextView
    private lateinit var paidUpTextView: TextView
    private lateinit var savedMoneyTextView: TextView
    private lateinit var paidPieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)

        incomeTextView = view.findViewById(R.id.income_textview)
        paidUpTextView = view.findViewById(R.id.paid_up_textview)
        savedMoneyTextView = view.findViewById(R.id.saved_textview)

        paidPieChart = view.findViewById(R.id.paid_pie_chart)
        initPieChart()
        setDataToPieChart(emptyList())

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        summaryViewModel.getActionsByCategory(Category.INCOME)

        val date = Date(1630717008000)

        summaryViewModel.getIncome(date).observe(viewLifecycleOwner) { income ->
            Log.d("Summary income", income.toString())
            incomeTextView.text = "your income in this time is $income."
        }

        summaryViewModel.getPaidUp(date).observe(viewLifecycleOwner) { paid ->
            Log.d("Summary paid up", paid.toString())
            paidUpTextView.text = "you spent $paid in this time."
        }

        summaryViewModel.getSavedMoney(date).observe(viewLifecycleOwner) { saved ->
            Log.d("Summary savedMoney", saved.toString())
            savedMoneyTextView.text = "you saved $saved in this time."
        }

        summaryViewModel.getPaidActionsInTime(date).observe(viewLifecycleOwner) { actions ->
            setDataToPieChart(actions)
        }


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
        val mortgageAmount = actions.filter { it.category == Category.MORTGAGE }.map { it.amount }.sum()
        val groceriesAmount = actions.filter { it.category == Category.GROCERIES }.map { it.amount }.sum()
        val utilitiesAmount = actions.filter { it.category == Category.UTILITIES }.map { it.amount }.sum()
        val entertainmentAmount = actions.filter { it.category == Category.ENTERTAINMENT }.map { it.amount }.sum()

        paidPieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()

//        dataEntries.add(PieEntry((mortgageAmount * 100 / totalAmount).toFloat(), Category.MORTGAGE.name,"aa"))
//        dataEntries.add(PieEntry((groceriesAmount * 100 / totalAmount).toFloat(), Category.GROCERIES.name, groceriesAmount))
//        dataEntries.add(PieEntry((utilitiesAmount * 100 / totalAmount).toFloat(), Category.UTILITIES.name))
//        dataEntries.add(PieEntry((entertainmentAmount * 100 / totalAmount).toFloat(), Category.ENTERTAINMENT.name))

        dataEntries.add(PieEntry(mortgageAmount.toFloat(), Category.MORTGAGE.name,"aa"))
        dataEntries.add(PieEntry(groceriesAmount.toFloat(), Category.GROCERIES.name, groceriesAmount))
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

}