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
        setMoney(10000)
        update()

        moneyUp.setOnClickListener {
            addMoney(10000)
        }

        attendButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AttendActivity::class.java)
            startActivity(intent)
        }

        shopButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ShopActivity::class.java)
            startActivity(intent)
        }

        giftButton.setOnClickListener {
            val intent = Intent(this@MainActivity, GiftActivity::class.java)
            startActivity(intent)
        }

        gameButton.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        update()
    }

    fun setMoney(n : Int){
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null)
        cursor.moveToFirst()
        val sql2 = "UPDATE user set money = ${n} WHERE id = 0"
        dbHelper.writableDatabase.execSQL(sql2)
        val sql3 = "UPDATE user set health = 1 WHERE id = 0"
        dbHelper.writableDatabase.execSQL(sql3)
        update()
    }

    fun addMoney(n : Int){
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null)
        cursor.moveToFirst()
        val c = cursor.getInt(cursor.getColumnIndex("money"))
        val sql2 = "UPDATE user set money = ${c+n} WHERE id = 0"
        dbHelper.writableDatabase.execSQL(sql2)
        update()
    }
    
    fun getMoney():Int{
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null)
        cursor.moveToFirst()
        return cursor.getInt(cursor.getColumnIndex("money"))
    }

    fun getHealth():Int{
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null)
        cursor.moveToFirst()
        return cursor.getInt(cursor.getColumnIndex("health"))
    }

    fun getMax():Int{
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null)
        cursor.moveToFirst()
        return cursor.getInt(cursor.getColumnIndex("max"))
    }

    fun update(){
        moneyView.text = getMoney().toString() + "원"
        HeathView.text = getHealth().toString() + " / " + getMax().toString()
    }
}