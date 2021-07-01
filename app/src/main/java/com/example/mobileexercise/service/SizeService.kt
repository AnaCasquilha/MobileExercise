package com.example.mobileexercise.service

import com.example.mobileexercise.models.SizeModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SizeService {
    @GET("services/rest/")
    fun getSize(
        @Query("method") method: String?,
        @Query("api_key") api_key: String?,
        @Query("photo_id") photo_id: String?,
        @Query("format") format: String?,
        @Query("nojsoncallback") nojsoncallback: Int
    ): Call<SizeModel>
}