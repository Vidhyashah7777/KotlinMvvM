package com.example.retrofitmvvm.roomDatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.retrofitmvvm.retrofitAll.models.Product
import com.example.retrofitmvvm.roomDatabase.dao.DaoProduct

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): DaoProduct

    companion object {
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context, ProductDatabase::class.java, "product_database"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}