package com.example.retrofitmvvm

import android.app.Application
import com.example.retrofitmvvm.retrofitAll.repository.ProductRepository
import com.example.retrofitmvvm.retrofitAll.retrofit.ApiInterface
import com.example.retrofitmvvm.retrofitAll.retrofit.RetrofitInstance
import com.example.retrofitmvvm.roomDatabase.database.ProductDatabase

class ProductAplication :Application() {
    lateinit var productRepository: ProductRepository

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val apiInterface =  RetrofitInstance.getInstance().create(ApiInterface::class.java)
        val database = ProductDatabase.getInstance(applicationContext)
        productRepository = ProductRepository(apiInterface, database, applicationContext)
    }
}