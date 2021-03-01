package com.printful.foodie.service

import com.printful.foodie.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {

    //https://raw.githubusercontent.com/LemonTimee/printfulTestData/master/food.json

    //get data from internet via Retrofit and RxJava
    @GET("LemonTimee/printfulTestData/master/food.json")
    fun getFood(): Single<List<Food>>

}