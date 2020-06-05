package com.example.capstonedesign

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.moneyView
import kotlinx.android.synthetic.main.activity_shop.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moneyView.text = getMoney().toString() + "원"

        moneyUp.setOnClickListener {
            addMoney(1000)
        }

        shopButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ShopActivity::class.java)
            startActivity(intent)
        }

        giftButton.setOnClickListener {
            val intent = Intent(this@MainActivity, GiftActivity::class.java)
            startActivity(intent)
        }
    }

    fun addMoney(n : Int){
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null);
        cursor.moveToFirst()
        val c = cursor.getInt(cursor.getColumnIndex("money"))
        val sql2 = "UPDATE user set money = ${c+n} WHERE id = 0"
        dbHelper.writableDatabase.execSQL(sql2)
        moneyView.text = getMoney().toString() + "원"
    }
    
    fun getMoney():Int{
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null);
        cursor.moveToFirst()
        return cursor.getInt(cursor.getColumnIndex("money"));
    }
}