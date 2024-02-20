package com.example.retrofitmvvm.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitmvvm.ProductAplication
import com.example.retrofitmvvm.databinding.ActivityLoginBinding
import com.example.retrofitmvvm.retrofitAll.viewmodels.ProductViewModel
import com.example.retrofitmvvm.retrofitAll.viewmodels.ProductViewModelFactory
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = (application as ProductAplication).productRepository
        productViewModel = ViewModelProvider(this, ProductViewModelFactory(repository)).get(
            ProductViewModel::class.java
        )
        initListener()
        setObserver()
    }

    private fun setObserver() {
        productViewModel.post.observe(this) { response ->
            Log.e("updated Value", "setObserver: ${response.toString()}")
            Toast.makeText(this, "" + response.toString(), Toast.LENGTH_SHORT).show()
        }

        productViewModel.imageUpload.observe(this) { response ->
            Log.d("upload image", "setObserver: ${response.message} ")
        }
    }

    private fun initListener() {
        binding.login.setOnClickListener {
            productViewModel.getPost(
                1, 1, binding.etTitle.text.toString(), binding.etBody.text.toString()
            )
        }

        binding.upload.setOnClickListener {
            getImage()
        }
    }


    fun getImage() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeType = arrayOf("images/*")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
            startActivityForResult(it, 101)
        }
    }

    private var selectedImageUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK) {
            when (requestCode) {
                101 -> {
                    selectedImageUri = data?.data

                    val requestBody = RequestBody.create(MediaType.parse("image/*"), File(selectedImageUri.toString()))
                    val part = MultipartBody.Part.createFormData(
                        "file",
                        "test.jpg",
                        requestBody
                    )
                    productViewModel.uploadNotesImages(
                        part, "1","1"
                    )
                }
            }
        }
    }
}
