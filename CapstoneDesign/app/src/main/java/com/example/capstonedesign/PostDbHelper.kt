package com.example.capstonedesign

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PostDbHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createSql = CREATE_SQL_VER3
        val createSqlUser = CREATE_SQL_USER
        db?.execSQL(createSql)
        db?.execSQL(createSqlUser)
        db?.execSQL("INSERT INTO user (money) values(0)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        when(oldVersion){
            in 1..2 -> db?.execSQL("ALTER TABLE post ADD COLUMN time TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        }
    }

    companion object{
        const val DATABASE_VERSION = 3
        const val DATABASE_NAME = "post.db"

        const val CREATE_SQL_VER1 = "CREATE TABLE post ("+
                "id INTEGER PRIMARY KEY,"+
                "title TEXT )"

        const val CREATE_SQL_VER2 = "CREATE TABLE post ("+
                "id INTEGER PRIMARY KEY,"+
                "title TEXT )"

        const val CREATE_SQL_VER3 = "CREATE TABLE post ("+
                "id INTEGER PRIMARY KEY,"+
                "title TEXT, " +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP )"

        const val CREATE_SQL_USER = "CREATE TABLE user ("+
                "money INTEGER PRIMARY KEY )"
    }
}