package com.elsawy.budgetmanager.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.elsawy.budgetmanager.R
import com.evrencoskun.tableview.TableView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

   private val homeViewModel: HomeViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View? {
      val view = inflater.inflate(R.layout.fragment_home, container, false)

      val tableView =
         view.findViewById<TableView>(R.id.content_container)
      val adapter = MyTableViewAdapter()
      tableView.setAdapter(adapter)

      homeViewModel.allActions.observe(viewLifecycleOwner) { actions ->
         adapter.setAllItems(
            getColumnHeaders(),
            actionsToRowHeaders(actions),
            actionsToCells(actions))
      }

      return view
   }

}
