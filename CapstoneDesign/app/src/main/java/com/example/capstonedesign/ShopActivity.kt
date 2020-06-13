package com.example.capstonedesign

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.scard.view.*


class ShopActivity : AppCompatActivity()  {
    val dataList = mutableListOf<MutableMap<String,String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        /*custom toolbar*/
        setSupportActionBar(toolbar)
        val ab:ActionBar = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)
        /*---------------------------------------*/
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter()
        updateRecyclerView()
        moneyView.text = getMoney().toString() + "원"
    }

    //custom toolbar - backbutton
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
                LayoutInflater.from(this@ShopActivity).inflate(R.layout.scard,parent,false)
            )
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.titleTextView.text = dataList[position].get("g_name").toString()
            holder.priceTextView.text = dataList[position].get("price").toString() + "원"
            holder.buyButton.setOnClickListener{
                val haveM = getMoney()
                val id = dataList[position].get("g_id")?.toInt()
                val price = dataList[position].get("price")?.toInt()
                if(haveM >= price!!){
                    if (id != null) {
                        buyGift(id,price)
                    }
                }else{
                    Toast.makeText(this@ShopActivity,"돈이 부족합니다.", Toast.LENGTH_SHORT).show()
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

    fun buyGift(g: Int, price: Int ){
        val sql = "INSERT INTO post (gift) values(${g})"
        val dbHelper = PostDbHelper(applicationContext)
        dbHelper.writableDatabase.execSQL(sql)
        minusMoney(price)
        moneyView.text = getMoney().toString() + "원"
    }

    private fun readAllData() : MutableList<MutableMap<String,String>>{
        val dbHelper = PostDbHelper(applicationContext)
        val resultList = mutableListOf<MutableMap<String,String>>()
        val cursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM gift",null)
        if(cursor.moveToFirst()){
            do{
                val map = mutableMapOf<String,String>()
                map["g_id"] = cursor.getInt(cursor.getColumnIndex("g_id")).toString()
                map["g_name"] = cursor.getString(cursor.getColumnIndex("g_name"))
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
}