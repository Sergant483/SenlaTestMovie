package com.example.senlatestmovie.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.senlatestmovie.data.database.dao.MovieDao
import com.example.senlatestmovie.data.database.entity.MovieEntity

private const val DB_VERSION = 1
private const val DB_NAME = "app.db"

@Database(entities = [MovieEntity::class], version = DB_VERSION)
abstract class AppDataBase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        fun newInstance(context: Context): AppDataBase =
            Room.databaseBuilder(context, AppDataBase::class.java, DB_NAME).build()
    }
}