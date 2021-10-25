package com.elsawy.budgetmanager.ui.summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Category
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    @Inject
    lateinit var summaryViewModel: SummaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        summaryViewModel.getActionsByCategory(Category.INCOME)
        summaryViewModel.allActions.observe(viewLifecycleOwner){ actions->
            actions.forEach{
                Log.d("Summary",it.toString())
            }
        }

    }

}