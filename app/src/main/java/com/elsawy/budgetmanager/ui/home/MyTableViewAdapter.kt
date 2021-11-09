package com.elsawy.budgetmanager.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import android.widget.TextView
import android.widget.LinearLayout
import com.elsawy.budgetmanager.R
import com.evrencoskun.tableview.adapter.AbstractTableAdapter

class MyTableViewAdapter : AbstractTableAdapter<ColumnHeader?, RowHeader?, Cell?>() {

   internal inner class MyCellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
      val cell_container: LinearLayout
      val cell_textview: TextView

      init {
         cell_container = itemView.findViewById(R.id.cell_container)
         cell_textview = itemView.findViewById(R.id.cell_data)
      }
   }

   override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
      // Get cell xml layout
      val layout: View = LayoutInflater.from(parent.context)
         .inflate(R.layout.table_view_cell_layout, parent, false)
      // Create a Custom ViewHolder for a Cell item.
      return MyCellViewHolder(layout)
   }

   override fun onBindCellViewHolder(
      holder: AbstractViewHolder,
      cellItemModel: Cell?,
      columnPosition: Int,
      rowPosition: Int,
   )  {
      val cell = cellItemModel as Cell

      // Get the holder to update cell item text
      val viewHolder = holder as MyCellViewHolder
      viewHolder.cell_textview.text = cell.data.toString()

      // If your TableView should have auto resize for cells & columns.
      // Then you should consider the below lines. Otherwise, you can ignore them.

      // It is necessary to remeasure itself.
      viewHolder.cell_container.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
      viewHolder.cell_textview.requestLayout()
   }

   internal inner class MyColumnHeaderViewHolder(itemView: View) :
      AbstractViewHolder(itemView) {
      val column_header_container: LinearLayout
      val column_textview: TextView

      init {
         column_header_container = itemView.findViewById(R.id.column_header_container)
         column_textview = itemView.findViewById(R.id.column_header_textView)
      }
   }

   override fun onCreateColumnHeaderViewHolder(
      parent: ViewGroup,
      viewType: Int,
   ): AbstractViewHolder {

      // Get Column Header xml Layout
      val layout: View = LayoutInflater.from(parent.context)
         .inflate(R.layout.table_view_column_header_layout, parent, false)

      // Create a ColumnHeader ViewHolder
      return MyColumnHeaderViewHolder(layout)
   }

   override fun onBindColumnHeaderViewHolder(
      holder: AbstractViewHolder,
      columnHeaderItemModel: ColumnHeader?,
      columnPosition: Int,
   )  {
      val columnHeader = columnHeaderItemModel as ColumnHeader

      // Get the holder to update cell item text
      val columnHeaderViewHolder = holder as MyColumnHeaderViewHolder
      columnHeaderViewHolder.column_textview.text = columnHeader.data.toString()

      // If your TableView should have auto resize for cells & columns.
      // Then you should consider the below lines. Otherwise, you can ignore them.

      // It is necessary to remeasure itself.
      columnHeaderViewHolder.column_header_container.layoutParams.width =
         LinearLayout.LayoutParams.WRAP_CONTENT
//      columnHeaderViewHolder.column_header_textview.requestLayout()
      columnHeaderViewHolder.column_textview.requestLayout()
   }

   internal inner class MyRowHeaderViewHolder(itemView: View) :
      AbstractViewHolder(itemView) {
      val row_textview: TextView

      init {
         row_textview = itemView.findViewById(R.id.row_header_textView)
      }
   }

   override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {

      // Get Row Header xml Layout
      val layout: View = LayoutInflater.from(parent.context)
         .inflate(R.layout.table_view_row_header_layout, parent, false)

      // Create a Row Header ViewHolder
      return MyRowHeaderViewHolder(layout)
   }

   override fun onBindRowHeaderViewHolder(
      holder: AbstractViewHolder,
      rowHeaderItemModel: RowHeader?,
      rowPosition: Int,
   ) {
      val rowHeader = rowHeaderItemModel as RowHeader

      // Get the holder to update row header item text
      val rowHeaderViewHolder = holder as MyRowHeaderViewHolder
      rowHeaderViewHolder.row_textview.text = rowHeader.data.toString()
   }

   override fun onCreateCornerView(parent: ViewGroup): View {
      // Get Corner xml layout
      return LayoutInflater.from(parent.context)
         .inflate(R.layout.table_view_corner_layout, parent, false)
   }

   override fun getColumnHeaderItemViewType(columnPosition: Int): Int {
      // The unique ID for this type of column header item
      // If you have different items for Cell View by X (Column) position,
      // then you should fill this method to be able create different
      // type of ColumnViewHolder on "onCreateColumnViewHolder"
      return 0
   }

   override fun getRowHeaderItemViewType(rowPosition: Int): Int {
      // The unique ID for this type of row header item
      // If you have different items for Row Header View by Y (Row) position,
      // then you should fill this method to be able create different
      // type of RowHeaderViewHolder on "onCreateRowHeaderViewHolder"
      return 0
   }

   override fun getCellItemViewType(columnPosition: Int): Int {
      // The unique ID for this type of cell item
      // If you have different items for Cell View by X (Column) position,
      // then you should fill this method to be able create different
      // type of CellViewHolder on "onCreateCellViewHolder"
      return 0
   }

}