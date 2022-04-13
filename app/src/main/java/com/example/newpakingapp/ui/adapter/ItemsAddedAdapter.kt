package com.example.newpakingapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newpakingapp.R
import com.example.newpakingapp.data.model.OrderItemsDetails
import kotlinx.android.synthetic.main.product_item_row.view.*

class ItemsAddedAdapter constructor(private val context: Context, private val items:ArrayList<OrderItemsDetails>) :
RecyclerView.Adapter<ItemsAddedAdapter.MyItemsViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item_row, parent, false)
        return MyItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyItemsViewHolder, position: Int) {
        val itemOrder = items[position]
        holder.itemName.text = itemOrder.name
        holder.itemQuantity.text = itemOrder.quantity.toString()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyItemsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val itemName = itemView.item_row_item_name!!
        val itemQuantity = itemView.item_row_item_qty!!
    }
}