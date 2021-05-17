package com.example.proyectomoviles

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Clase para crear la tabla y administrarla
 */
class AdminSQLite(context: Context,
                            name: String,
                            factory: SQLiteDatabase.CursorFactory?,
                            version: Int)
    : SQLiteOpenHelper(context,name,factory,version)
{
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table puntos(lat double primary key, long double)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}