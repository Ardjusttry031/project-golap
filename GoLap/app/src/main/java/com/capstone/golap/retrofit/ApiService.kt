package com.capstone.golap.retrofit

import com.capstone.golap.response.DetailResponse
import com.capstone.golap.response.LoginResponse
import com.capstone.golap.response.MatchedLaptopsItem
import com.capstone.golap.response.PredictionResponse
import com.capstone.golap.response.RecomResponse
import com.capstone.golap.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
        @POST("register")
        suspend fun postSignup(@Body body: Map<String, String>): Response<RegisterResponse>

        @POST("login")
        suspend fun postLogin(@Body body: Map<String, String>): Response<LoginResponse>

    @POST("recommendation-by-price")
    suspend fun getRecommendation(
        @Field("price") price: Int
    ) :Response<List<RecomResponse>>

    @GET("items")
    suspend fun getList(
    ) :Response<List<DetailResponse>>

        @GET("laptops/{id}")
        suspend fun getLaptopDetail(
            @Path("id") laptopId: String
        ): Response<DetailResponse>

        @POST("prediction/predict")
        suspend fun getPredictions(
            @Body specifications: MatchedLaptopsItem
        ): Response<PredictionResponse>
    }
