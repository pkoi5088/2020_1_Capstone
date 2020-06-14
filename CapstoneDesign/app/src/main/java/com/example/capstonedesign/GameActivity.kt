package com.example.capstonedesign

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.scard.view.*

class GameActivity : AppCompatActivity() {
    val dataList = mutableListOf<MutableMap<String,String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter()
        updateRecyclerView()
        update()
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titleTextView : TextView
        val priceTextView : TextView
        val buyButton : Button

        init{
            titleTextView = itemView.subjectView
            priceTextView = itemView.attendView
            buyButton = itemView.buyButton
        }
    }

    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(this@GameActivity).inflate(R.layout.scard,parent,false)
            )
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.titleTextView.text = dataList[position].get("name").toString()
            holder.priceTextView.text = dataList[position].get("price").toString() + "원"
            holder.buyButton.setOnClickListener{
                val haveM = getMoney()
                val id = dataList[position].get("id")?.toInt()
                val price = dataList[position].get("price")?.toInt()
                if(haveM >= price!!){
                    if(getHealth()>=getMax()){
                        Toast.makeText(this@GameActivity,"체력이 최대입니다.",Toast.LENGTH_SHORT).show()
                    }else if (id != null) {
                        buyItem(id,price)
                    }
                }else{
                    Toast.makeText(this@GameActivity,"돈이 부족합니다.",Toast.LENGTH_SHORT).show()
                }
                updateRecyclerView();
            }
        }
    }

    fun updateRecyclerView(){
        dataList.clear()
        dataList.addAll(readAllData())
        recyclerView.adapter?.notifyDataSetChanged()
    }

    fun buyItem(g: Int, price: Int ){
        val dbHelper = PostDbHelper(applicationContext)
        minusMoney(price)
        when(g){
            0 -> {
                val health = getHealth()
                val sql = "UPDATE user SET health = ${health+1} where health = ${health}"
                dbHelper.writableDatabase.execSQL(sql)
            }
        }
        update()
    }

    private fun readAllData() : MutableList<MutableMap<String,String>>{
        val dbHelper = PostDbHelper(applicationContext)
        val resultList = mutableListOf<MutableMap<String,String>>()
        val cursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM game",null)
        if(cursor.moveToFirst()){
            do{
                val map = mutableMapOf<String,String>()
                map["id"] = cursor.getInt(cursor.getColumnIndex("id")).toString()
                map["name"] = cursor.getString(cursor.getColumnIndex("name"))
                map["price"] = cursor.getInt(cursor.getColumnIndex("price")).toString()
                resultList.add(map)
            }while(cursor.moveToNext())
        }
        return resultList
    }

    fun getMoney() : Int{
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null);
        cursor.moveToFirst()
        return cursor.getInt(cursor.getColumnIndex("money"));
    }

    fun minusMoney(n : Int){
        val sql = "SELECT * from user"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql,null);
        cursor.moveToFirst()
        val c = cursor.getInt(cursor.getColumnIndex("money"))
        val sql2 = "UPDATE user SET money = ${c-n} WHERE money = ${c}"
        dbHelper.writableDatabase.execSQL(sql2)
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

    fun update() {
        moneyView.text = getMoney().toString() + "원"
    }
}