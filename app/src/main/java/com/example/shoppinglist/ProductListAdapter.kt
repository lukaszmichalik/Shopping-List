package com.example.shoppinglist

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_row.view.*
import java.security.AccessControlContext

class ProductListAdapter(val products: MutableList<Product>, val context: Context): RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    val dbHelper = DBHelper(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.one_row,parent,false)
        view.setOnClickListener { view.product_id.setTextColor(Color.GREEN) }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product=products[position]
        holder.productIdTextView.text=product.id.toString()
        holder.productNameTextView.text=product.name
        holder.productAmountTextView.text=product.amount
        holder.productPriorityTextView.text=product.priority
        holder.productPrizeTextView.text=product.prize
        holder.productNameTextView.setOnClickListener(){
            val intent= Intent(context,EditActivity::class.java).apply {
                putExtra("ProductID",products[position].id)
            }
            context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener {
            val productName: String = product.name
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Warning")
                .setMessage("Are You Sure to Delete: $productName ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    if (dbHelper.deleteData(product.id)){
                        products.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,products.size)
                        Toast.makeText(context,"Product $productName Deleted", Toast.LENGTH_SHORT).show()
                    }else
                        Toast.makeText(context,"Error Deleteting", Toast.LENGTH_SHORT).show()

                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->  })
                .show()
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val productView=view
        var productIdTextView=view.findViewById<TextView>(R.id.product_id)
        var productNameTextView=view.findViewById<TextView>(R.id.product_name)
        var productAmountTextView=view.findViewById<TextView>(R.id.amount)
        var productPriorityTextView=view.findViewById<TextView>(R.id.priority)
        var productPrizeTextView=view.findViewById<TextView>(R.id.prize)
        var deleteButton=view.findViewById<Button>(R.id.deleteButton)
    }




}