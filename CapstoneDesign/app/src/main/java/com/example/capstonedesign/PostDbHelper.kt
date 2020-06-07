package com.example.capstonedesign

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PostDbHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createSql = CREATE_SQL_VER3
        val createSqlUser = CREATE_SQL_USER
        val createSqlGift = CREATE_SQL_GIFT
        val createSqlGame = CREATE_SQL_GAME
        db?.execSQL(createSql)
        db?.execSQL(createSqlUser)
        db?.execSQL(createSqlGift)
        db?.execSQL(createSqlGame)
        db?.execSQL("INSERT INTO user (id,money,health,max) values(0,0,1,10)")
        db?.execSQL("INSERT INTO gift (g_name,price) values('아메리카노',3000)")
        db?.execSQL("INSERT INTO gift (g_name,price) values('조리퐁 라떼',4000)")
        db?.execSQL("INSERT INTO gift (g_name,price) values('초코라떼',2500)")
        db?.execSQL("INSERT INTO game (id,name,price) values(0,'먹이주기',500)")
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
                "id INTEGER PRIMARY KEY, gift INTEGER )"

        const val CREATE_SQL_VER2 = "CREATE TABLE post ("+
                "id INTEGER PRIMARY KEY, gift INTEGER )"

        const val CREATE_SQL_VER3 = "CREATE TABLE post ("+
                "id INTEGER PRIMARY KEY, gift INTEGER, "+
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP )"

        const val CREATE_SQL_USER = "CREATE TABLE user ("+
                "id INTEGER PRIMARY KEY, money INTEGER, health INTEGER, max INTEGER )"

        const val CREATE_SQL_GIFT = "CREATE TABLE gift ("+
                "g_id INTEGER PRIMARY KEY, "+
                "g_name TEXT, "+
                "price INTEGER )"

        const val CREATE_SQL_GAME = "CREATE TABLE game ("+
                "id INTEGER PRIMARY KEY, "+
                "name TEXT, "+
                "price INTEGER )"
    }
}