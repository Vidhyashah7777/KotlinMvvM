package com.example.retrofitmvvm.retrofitAll.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/*to store data in database*/

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productID: Int,
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)

