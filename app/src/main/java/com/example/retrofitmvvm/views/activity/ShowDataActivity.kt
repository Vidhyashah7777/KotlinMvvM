package com.example.retrofitmvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitmvvm.ProductAplication
import com.example.retrofitmvvm.R
import com.example.retrofitmvvm.databinding.ActivityShowDataBinding
import com.example.retrofitmvvm.retrofitAll.models.Product
import com.example.retrofitmvvm.retrofitAll.viewmodels.ProductViewModel
import com.example.retrofitmvvm.retrofitAll.viewmodels.ProductViewModelFactory
import com.example.retrofitmvvm.views.adapter.ProductAdapter

class ShowDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowDataBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productList: List<Product>
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as ProductAplication).productRepository
        productViewModel = ViewModelProvider(this, ProductViewModelFactory(repository)).get(
            ProductViewModel::class.java
        )
        productViewModel.product.observe(this, Observer {
            Log.d("Get product", it.products.toString())
            Toast.makeText(this, "get product" + it.products.size, Toast.LENGTH_SHORT).show()
            productList = it.products
            binding.rvShowData.layoutManager = LinearLayoutManager(this)
            adapter = ProductAdapter(productList)
            binding.rvShowData.adapter = adapter
        })
    }
}