package com.example.capstonedesign

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.acard.view.attendView
import kotlinx.android.synthetic.main.acard.view.subjectView
import kotlinx.android.synthetic.main.activity_attend.recyclerView

class AttendActivity :AppCompatActivity() {
    val dataList = mutableListOf<MutableMap<String,String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attend)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter()
        updateRecyclerView()
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val subjectView : TextView
        val attendView : TextView

        init{
            subjectView = itemView.subjectView
            attendView = itemView.attendView
        }
    }

    inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
            return MyViewHolder(
                LayoutInflater.from(this@AttendActivity).inflate(R.layout.acard,parent,false)
            )
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.subjectView.text = dataList[position].get("subject").toString()
            holder.attendView.text = dataList[position].get("attend").toString()
        }
    }

    fun updateRecyclerView(){
        dataList.clear()
        dataList.addAll(readAllData())
        recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun readAllData() : MutableList<MutableMap<String,String>> {
        val resultList = mutableListOf<MutableMap<String, String>>()
        var i = 0
        do {
            i++
            val map = mutableMapOf<String, String>()
            map["subject"] = "창업캡스톤디자인1_01"
            map["attend"] = "${i}주차 출석 : 출석"
            resultList.add(map)
        } while (i!=13)
        do {
            i++
            val map = mutableMapOf<String, String>()
            map["subject"] = "창업캡스톤디자인1_01"
            map["attend"] = "${i}주차 출석 : 미처리"
            resultList.add(map)
        } while (i!=15)
        return resultList
    }
}