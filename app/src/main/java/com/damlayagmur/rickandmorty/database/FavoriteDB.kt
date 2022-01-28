package com.damlayagmur.rickandmorty.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FavoriteDB(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                CHARACTER_ID_COL + " TEXT)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addFav(characterId: Int) {
        val values = ContentValues()
        values.put(CHARACTER_ID_COL, characterId)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun checkDB(characterId: Int): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE $CHARACTER_ID_COL=$characterId",
            null
        )
    }

    fun deleteFav(characterId: Int) {
        val db = this.readableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME WHERE $CHARACTER_ID_COL=$characterId")
    }


    companion object {
        private val DATABASE_NAME = "FavoriteDB"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "favorite_table"
        val ID_COL = "id"
        val CHARACTER_ID_COL = "character_id"
    }
}