package com.example.retrofitmvvm.retrofitAll.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitmvvm.retrofitAll.models.*
import com.example.retrofitmvvm.retrofitAll.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Response

open class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    init {
//getProduct suspend function so we call coroutine to access suspend function
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProduct()

        }
    }

    val product: LiveData<ProductResponse>
        get() = repository.products


    fun getPost(userId: Int, id: Int, title: String, body: String) {
        viewModelScope.launch {
            repository.getPost(userId, id, title, body)
        }
    }

    val post: LiveData<PostResponseItem>
        get() = repository.posts

    val imageUpload: LiveData<ImageResponse>
        get() = repository.images


    fun uploadNotesImages(
        file: MultipartBody.Part,
        note_id: String,
        connected_org_id: String
    ) {
        viewModelScope.launch {
            repository.uploadNotesImages(
                file,
                note_id,
                connected_org_id,
            )
        }
    }
}