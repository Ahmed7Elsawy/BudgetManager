package com.elsawy.budgetmanager.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.elsawy.budgetmanager.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

   private val homeViewModel: HomeViewModel by viewModels()

   private var _binding: FragmentHomeBinding? = null
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      _binding = FragmentHomeBinding.inflate(inflater, container, false)

      val adapter = MyTableViewAdapter()
      binding.tableView.setAdapter(adapter)

      homeViewModel.allActions.observe(viewLifecycleOwner) { actions ->
         adapter.setAllItems(
            getColumnHeaders(),
            actionsToRowHeaders(actions),
            actionsToCells(actions))
      }

      return binding.root
   }

}
