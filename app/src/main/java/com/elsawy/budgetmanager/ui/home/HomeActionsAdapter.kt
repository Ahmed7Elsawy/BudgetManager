package com.elsawy.budgetmanager.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elsawy.budgetmanager.R
import com.elsawy.budgetmanager.data.local.Action
import com.elsawy.budgetmanager.utils.convertTimeStampToDate

class HomeActionsAdapter : RecyclerView.Adapter<HomeActionsAdapter.ViewHolder>() {

    private var actionList: List<Action> = emptyList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.action_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.dateTextView.text = convertTimeStampToDate(actionList[position].date)
        viewHolder.categoryTextView.text = actionList[position].category.name
        viewHolder.descriptionTextView.text = actionList[position].description
        viewHolder.amountTextView.text = actionList[position].amount.toString()
        viewHolder.balanceTextView.text = actionList[position].balance.toString()
    }

    fun updateActionList(actions: List<Action>) {
        actionList = actions
    }

    override fun getItemCount() = actionList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.item_date_textview)
        val categoryTextView: TextView = view.findViewById(R.id.item_category_textview)
        val descriptionTextView: TextView = view.findViewById(R.id.item_description_textview)
        val amountTextView: TextView = view.findViewById(R.id.item_amount_textview)
        val balanceTextView: TextView = view.findViewById(R.id.item_balance_textview)
    }


}