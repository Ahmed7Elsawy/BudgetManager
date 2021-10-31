package com.elsawy.budgetmanager.ui.home

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.ui.Main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var homeActionsAdapter: HomeActionsAdapter
    private lateinit var tableHeader: LinearLayout
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tableHeader = view.findViewById(R.id.table_header)
        recyclerView = view.findViewById(R.id.home_recycler_view)
        recyclerView.itemAnimator = DefaultItemAnimator()
        homeActionsAdapter = HomeActionsAdapter()
        recyclerView.adapter = homeActionsAdapter

        homeViewModel.allActions.observe(viewLifecycleOwner) { actions ->
            homeActionsAdapter.updateActionList(actions)
            if (actions == null || actions.isEmpty()) {
                tableHeader.visibility = View.GONE
            } else {
                tableHeader.visibility = View.VISIBLE
            }
        }

        return view
    }

}