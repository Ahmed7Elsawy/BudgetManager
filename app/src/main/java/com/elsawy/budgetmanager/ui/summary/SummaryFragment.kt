package com.elsawy.budgetmanager.ui.summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
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
        summaryViewModel.getIncome(date).observe(viewLifecycleOwner){ income->
                Log.d("Summary",income.toString())
        }

    }

}