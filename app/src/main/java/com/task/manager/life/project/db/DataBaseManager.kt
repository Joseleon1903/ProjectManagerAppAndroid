package com.task.manager.life.project.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DataBaseManager{


    //attribute Database
    val dbName: String ="ProjectManager"

    val tableName : String = "PROJECT"

    val dbversion = 1

    val sqlCreationTable = "CREATE TABLE IF NOT EXISTS PROJECT " +
                            "(" +
                            "  ID            INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "  NAME          TEXT           NOT NULL, " +
                            "  DESCRIPTION   TEXT          NOT NULL, " +
                            "  STATUS        BOOLEAN, " +
                            "  START_DATE    TEXT       NOT NULL, " +
                            "  END_DATE      TEXT        NOT NULL " +
                            ")"

    var sqlDb : SQLiteDatabase? = null

    constructor(contexto : Context){
        val db = DbHelperProject(contexto)
        sqlDb = db.writableDatabase
        Toast.makeText(contexto, "Creation Database success", Toast.LENGTH_LONG).show()
    }


    inner class DbHelperProject(context:Context ): SQLiteOpenHelper(context,dbName , null, dbversion) {

        var contexto : Context = context

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreationTable)
            Toast.makeText(this.contexto, "se creo la base de dato SQL Lite", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS PROJECT")
        }

    }

    fun insert(values: ContentValues): Long{
        val ID = sqlDb!!.insert(tableName, "", values)
        return ID
    }

    fun query(projection: Array<String>, selection : String? , selectionArgs : Array<String>?, orderBy : String?):Cursor{
        val consulta = SQLiteQueryBuilder()
        consulta.tables = tableName
        val cursor = consulta.query(sqlDb, projection, selection, selectionArgs, null,null, orderBy)
        return cursor
    }

    fun delete(selection: String?, selectionArgs: Array<String>?): Int{
        val contador = sqlDb!!.delete(tableName, selection, selectionArgs)
        return  contador
    }

    fun update(values: ContentValues, selection: String?, selectionArgs: Array<String>?): Int{
        val contador = sqlDb!!.update(tableName, values, selection, selectionArgs)
        return contador
    }


}