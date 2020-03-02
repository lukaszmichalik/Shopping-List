package com.example.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import kotlinx.android.synthetic.main.edit_activity.*

class EditActivity :AppCompatActivity() {
    val dbHelper=DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        val productID=intent.getIntExtra("ProductID",-1)
        val product=dbHelper.getProductById(productID)
        editTextProductName.text.clear()
        editTextProductName.text.insert(0,product?.name?:"Product")
        editTextAmount.text.clear()
        editTextAmount.text.insert(0,product?.amount?:"Amount")
        editPriority.text.clear()
        editPriority.text.insert(0,product?.priority?:"Priority")
        editPrize.text.clear()
        editPrize.text.insert(0,product?.prize?:"Prize")
        buttonSave.setOnClickListener {
            dbHelper.editProduct(product,editTextProductName.text.toString(),editTextAmount.text.toString(),editPriority.text.toString(),editPrize.text.toString())
            finish()
        }

    }
}