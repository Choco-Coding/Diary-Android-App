package com.example.diary.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val SERVICE_KEY = ""

private val BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object ApiObject {
    val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}

interface WeatherApiService {
    @GET("getVilageFcst?serviceKey=$SERVICE_KEY")
    fun getWeather(
        @Query("numOfRows") num_of_rows: Int,
        @Query("pageNo") page_no: Int,
        @Query("dataType") data_type: String,
        @Query("base_date") base_date : Int,
        @Query("base_time") base_time : Int,
        @Query("nx") nx : String,
        @Query("ny") ny : String
    ): Call<WEATHER>
}

data class WEATHER (
    val response: RESPONSE
)
data class RESPONSE (
    val header: HEADER,
    val body: BODY
)
data class HEADER(
    val resultCode: Int,
    val resultMsg: String
)
data class BODY(
    val dataType: String,
    val items: ITEMS
)
data class ITEMS(
    val item: List<ITEM>
)
data class ITEM(
    val category: String,
    val fcstValue: String
)

