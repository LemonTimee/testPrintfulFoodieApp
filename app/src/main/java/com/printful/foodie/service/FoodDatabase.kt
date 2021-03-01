package com.printful.foodie.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.printful.foodie.model.Food

//database table and version
@Database(entities = arrayOf(Food::class),version = 1)
abstract class FoodDatabase: RoomDatabase() {

    abstract fun foodDao(): FoodDAO

    //using singleton to prevent thread from generating different data
    companion object{

        @Volatile private var instance: FoodDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FoodDatabase::class.java,"fooddatabase").build()

    }


}