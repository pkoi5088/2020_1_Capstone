package com.example.capstonedesign

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_gift.*;
import kotlinx.android.synthetic.main.activity_gift.recyclerView
import kotlinx.android.synthetic.main.activity_gift.toolbar
import kotlinx.android.synthetic.main.activity_shop.*
import kotlinx.android.synthetic.main.card.view.*
import kotlinx.android.synthetic.main.card.view.subjectView

class GiftActivity : AppCompatActivity() {
    val dataList = mutableListOf<MutableMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift)
        /*custom toolbar*/
        setSupportActionBar(toolbar)
        val ab: ActionBar = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)
        /*---------------------------------------*/
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter()
        updateRecyclerView()
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

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView
        val timeTextView: TextView
        val deleteButton: Button

        init {
            titleTextView = itemView.subjectView
            timeTextView = itemView.timeTextView
            deleteButton = itemView.deleteButton
        }
    }

    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(this@GiftActivity).inflate(R.layout.card, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.timeTextView.text = dataList[position].get("time").toString()
            val gift = dataList[position].get("gift")?.toInt()
            holder.titleTextView.text = gift?.let { getGname(it) }
            holder.deleteButton.setOnClickListener {
                removeData(dataList[position].get("id").toString())
                updateRecyclerView();
            }
        }
    }

    fun updateRecyclerView() {
        dataList.clear()
        dataList.addAll(readAllData())
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun readAllData(): MutableList<MutableMap<String, String>> {
        val dbHelper = PostDbHelper(applicationContext)
        val resultList = mutableListOf<MutableMap<String, String>>()
        val cursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM post", null)
        if (cursor.moveToFirst()) {
            do {
                val map = mutableMapOf<String, String>()
                map["id"] = cursor.getInt(cursor.getColumnIndex("id")).toString()
                map["gift"] = cursor.getInt(cursor.getColumnIndex("gift")).toString()
                map["time"] = cursor.getString(cursor.getColumnIndex("time")).toString()
                resultList.add(map)
            } while (cursor.moveToNext())
        }
        return resultList
    }

    fun removeData(id: String) {
        val dbHelper = PostDbHelper(applicationContext)
        val sql = "DELETE FROM post where id = ${id}"
        dbHelper.writableDatabase.execSQL(sql)
    }

    fun getGname(id: Int): String {
        val sql = "SELECT * from gift where g_id = $id"
        val dbHelper = PostDbHelper(applicationContext)
        val cursor = dbHelper.readableDatabase.rawQuery(sql, null);
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("g_name"));
    }
}