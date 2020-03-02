package com.example.shoppinglist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import java.lang.Exception

class DBHelper (val context: Context):SQLiteOpenHelper(context, DATA_BASE_NAME,null,DATA_BASE_VERSION){
    companion object{
        internal const val DATA_BASE_NAME="product.db"
        internal const val DATA_BASE_VERSION=1
        internal const val TABLE_NAME="products"
        internal const val COL_ID="id"
        internal const val COL_NAME="name"
        internal const val COL_AMOUNT="amount"
        internal const val COL_PRIORITY="priority"
        internal const val COL_PRIZE="prize"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val  sql=db!!.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME("+
                    "$COL_ID INTEGER PRIMARY KEY NOT NULL, "+
                    "$COL_NAME TEXT NOT NULL, "+
                    "$COL_AMOUNT TEXT NOT NULL, " +
                    "$COL_PRIORITY TEXT NOT NULL, " +
                    "$COL_PRIZE TEXT NOT NULL ) "
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allProducts: MutableList<Product>
        get(){
            val query="SELECT * FROM $TABLE_NAME"
            val products= mutableListOf<Product>()
            val db=this.readableDatabase
            val  cursor=db.rawQuery(query,null)

            if(cursor.moveToFirst()){
                do{
                    val  id=cursor.getInt(cursor.getColumnIndex(COL_ID))
                    val  name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                    val  amount=cursor.getString(cursor.getColumnIndex(COL_AMOUNT))
                    val  priority=cursor.getString(cursor.getColumnIndex(COL_PRIORITY))
                    val  prize=cursor.getString(cursor.getColumnIndex(COL_PRIZE))
                    var product=Product(name,amount,priority,prize,id)
                    products.add(product)
                }while(cursor.moveToNext())
            }
            db.close()
            return  products
        }


    fun addProduct(product: Product):Long {
        val db = this.writableDatabase //or readable
        val value = contentValuesOf()
        value.put(COL_NAME, product.name)
        value.put(COL_AMOUNT, product.amount)
        value.put(COL_PRIORITY, product.priority)
        value.put(COL_PRIZE, "Unknown")
        val result = db.insert(TABLE_NAME, null, value)
        product.id = result.toInt()
        db.close()
        return result
    }

    fun editProduct(product:Product?,name:String,amount:String,priority: String,prize: String){
        if(product is Product) {
            val db=this.writableDatabase
            val  value= contentValuesOf()
            value.put(COL_NAME,name)
            value.put(COL_AMOUNT,amount)
            value.put(COL_PRIORITY,priority)
            value.put(COL_PRIZE,prize)
            product.name=name
            product.amount=amount
            product.priority=priority
            product.prize=prize
            db.update(TABLE_NAME,value, COL_ID + "=?", arrayOf(product.id.toString()))
            db.close()
        }
    }

    fun getProductById(id:Int):Product?{
        val db=this.readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COL_ID=$id"
        val cursor=db.rawQuery(query,null)
        if(cursor.moveToFirst())
        {
            val name=cursor.getString(cursor.getColumnIndex(COL_NAME))
            val amount=cursor.getString(cursor.getColumnIndex(COL_AMOUNT))
            val priority=cursor.getString(cursor.getColumnIndex(COL_PRIORITY))
            val prize=cursor.getString(cursor.getColumnIndex(COL_PRIZE))
            cursor.close()
            db.close()
            return  Product(name,amount,priority,prize,id)
        }
        db.close()
        return null
    }

    fun deleteData(id:Int?):Boolean{
        val qry = "Delete From $TABLE_NAME where $COL_ID= $id"
        val db :SQLiteDatabase = this.writableDatabase
        var result : Boolean = false
        try{
            val cursor:Unit=db.execSQL(qry)
            result=true
        }catch(e:Exception){
            Log.e(ContentValues.TAG, "Error Deleting")
        }
        db.close()
        return result

    }

}