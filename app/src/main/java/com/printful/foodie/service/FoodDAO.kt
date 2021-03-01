package com.printful.foodie.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.printful.foodie.model.Food

@Dao
interface FoodDAO {

    //Using local database with room
    @Insert
    suspend fun insertAll(vararg food: Food): List<Long>

    @Query("SELECT * FROM food WHERE uuid = :foodId")
    suspend fun getFood(foodId: Int): Food

    @Query("DELETE FROM food")
    suspend fun deleteAllFood()

}