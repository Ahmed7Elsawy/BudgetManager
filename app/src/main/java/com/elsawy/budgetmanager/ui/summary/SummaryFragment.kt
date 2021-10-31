package com.elsawy.budgetmanager.ui.summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Category
import com.elsawy.budgetmanager.ui.Main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private val summaryViewModel: SummaryViewModel by viewModels()
    private lateinit var incomeTextView: TextView
    private lateinit var paidUpTextView: TextView
    private lateinit var savedMoneyTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)

        incomeTextView = view.findViewById(R.id.income_textview)
        paidUpTextView = view.findViewById(R.id.paid_up_textview)
        savedMoneyTextView = view.findViewById(R.id.saved_textview)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        summaryViewModel.getActionsByCategory(Category.INCOME)
//        summaryViewModel.allActions.observe(viewLifecycleOwner){ actions->
//            actions.forEach{
//                Log.d("Summary",it.toString())
//            }
//        }

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

    }

}