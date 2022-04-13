package com.example.newpakingapp.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newpakingapp.R
import com.example.newpakingapp.data.model.OrderHeaderModule
import com.example.newpakingapp.ui.adapter.listener.OnOrderNumberClicked
import kotlinx.android.synthetic.main.order_number_row.view.*

class OrderNumberAdapter constructor(private val context:Context, private val orderNumbers:List<OrderHeaderModule>,
                                     private val onOrderNumberClicked: OnOrderNumberClicked)
    :RecyclerView.Adapter<OrderNumberAdapter.MyOrderNumberViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderNumberViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_number_row, parent, false)
        return MyOrderNumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOrderNumberViewHolder, position: Int) {
        val orderNumber = orderNumbers[position].orderNumber.toString()
        holder.orderNumber.text = orderNumber
        holder.orderNumberCount.text = position.toString()

        holder.orderNumberRow.setOnClickListener {
            onOrderNumberClicked.onOrderClicked(orderNumber)
        }
    }

    override fun getItemCount(): Int {
        return orderNumbers.size
    }

    class MyOrderNumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val orderNumber = itemView.order_number_txt!!
        val orderNumberCount = itemView.order_number_count!!
        val orderNumberRow = itemView.order_number_row_linear!!

    }
}