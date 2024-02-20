package com.example.retrofitmvvm.retrofitAll.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.retrofitmvvm.retrofitAll.models.ImageResponse
import com.example.retrofitmvvm.retrofitAll.models.PostResponseItem
import com.example.retrofitmvvm.retrofitAll.models.Product
import com.example.retrofitmvvm.retrofitAll.models.ProductResponse
import com.example.retrofitmvvm.retrofitAll.retrofit.ApiInterface
import com.example.retrofitmvvm.roomDatabase.database.ProductDatabase
import com.example.retrofitmvvm.utils.NetworkConnection
import okhttp3.MultipartBody


/*private val productDatabase: ProductDatabase  (database ma data store krvana hoi to)*/
class ProductRepository(
    private val apiInterface: ApiInterface,
    private val productDatabase: ProductDatabase,
    private val context: Context
) {
    private val productLiveData = MutableLiveData<ProductResponse>()
    val products: LiveData<ProductResponse>
        get() = productLiveData

    private val postLiveData = MutableLiveData<PostResponseItem>()
    val posts: LiveData<PostResponseItem>
        get() = postLiveData

    private val postImageData = MutableLiveData<ImageResponse>()
    val images: LiveData<ImageResponse>
        get() = postImageData

    /*get request*/

    suspend fun getProduct() {
        if (NetworkConnection.isInternetAvailable(context)) {
            val result = apiInterface.getData()

            if (result != null && result.body() != null) {
                productDatabase.productDao()
                    .insertProduct(result.body()!!.products)  // database insert
                productLiveData.postValue(result.body())
            }
        } else {
//            internet not available get data from database
            val products = productDatabase.productDao().getAllProducts()
            val productList = ProductResponse(1, products, 1, 1)
            productLiveData.postValue(productList)
        }

    }

    suspend fun getPost(userId: Int, id: Int, title: String, body: String) {
        if (NetworkConnection.isInternetAvailable(context)) {
            val result = apiInterface.postMethod("", "", userId, id, title, body)
            if (result != null && result.body() != null) {
                postLiveData.postValue(result.body())
            }
        }
    }

    fun uploadNotesImages(
        file: MultipartBody.Part,
        note_id: String, connected_org_id: String,
    ) {
        val call = apiInterface.uploadNotesImages(
            "Utility.udid()",
            "UtilsPref.getAppPrefString(PrefConstant.KEY_AUTH_TOKEN)",
            file,
            note_id,
            connected_org_id
        )
        if (call != null && call.body() != null) {
            postImageData.postValue(call.body())
        }
    }

}