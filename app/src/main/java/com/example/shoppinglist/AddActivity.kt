package com.example.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import kotlinx.android.synthetic.main.add_activity.*

class AddActivity :AppCompatActivity() {
    val dbHelper=DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)
        val productID=intent.getIntExtra("ProductID",-1)
        val product=dbHelper.getProductById(productID)
        //   addTextProductName.text.clear()
        //   addTextProductName.text.insert(0,product?.name?:"Product")
        //   addTextAmount.text.clear()
        //    addTextAmount.text.insert(0,product?.amount?:"Amount")
        //   addPriority.text.clear()
        //    addPriority.text.insert(0,product?.priority?:"Priority")
        buttonAdd.setOnClickListener {
            dbHelper.addProduct(Product(addTextProductName.text.toString(),addTextAmount.text.toString(),addPriority.text.toString(),"unknown"))
            addTextProductName.text.clear()
            addTextAmount.text.clear()
            addPriority.text.clear()
            finish()
        }
        cancelButton.setOnClickListener {
            finish()
        }


    }
}