package com.example.retrofitmvvm.retrofitAll.retrofit


import com.example.retrofitmvvm.retrofitAll.models.ImageResponse
import com.example.retrofitmvvm.retrofitAll.models.PostResponseItem
import com.example.retrofitmvvm.retrofitAll.models.ProductResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    /*for api response plugin json to kotlin class (jk logo)*/
     @GET("/products")
     suspend fun getData(/*@Query("id") id: Int*/): Response<ProductResponse>



    @FormUrlEncoded
    @POST("/posts")
    suspend fun postMethod(
        @Header("udid") udid: String?,
        @Header("Authorization") accessToken: String?,
        @Field("userId") userId: Int,
        @Field("id") id: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Response<PostResponseItem>



    @Multipart
    @POST("other/upload")
    fun uploadNotesImages(
        @Header("udid") udid: String?,
        @Header("Authorization") accessToken: String?,
        @Part file: MultipartBody.Part?,
        @Query("note_id") note_id: String?,
        @Query("connected_org_id") connected_org_id: String?
    ): Response<ImageResponse>

}