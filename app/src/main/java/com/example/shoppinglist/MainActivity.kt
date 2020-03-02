package com.example.shoppinglist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.one_row.*

class MainActivity : AppCompatActivity() {

    val dbHelper = DBHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // product_name.setOnClickListener {
       //     val intent =Intent(this,EditActivity::class.java)
       //     startActivity(intent)
       // }


        updateData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.add_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent=Intent(this,AddActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        updateData()    }

    fun updateData(){
        list_of_products.layoutManager= LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        list_of_products.adapter=ProductListAdapter(dbHelper.allProducts,this)
        //adapter.notifyDataSetChanged()


    }


}
