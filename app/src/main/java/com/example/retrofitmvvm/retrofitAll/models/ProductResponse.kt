package com.example.retrofitmvvm.retrofitAll.models

data class ProductResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)