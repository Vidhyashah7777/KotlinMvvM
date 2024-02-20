package com.example.retrofitmvvm.roomDatabase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitmvvm.retrofitAll.models.Product

@Dao
interface DaoProduct {

    @Insert
    suspend fun insertProduct(product: List<Product>)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<Product>

}