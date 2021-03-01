package com.printful.foodie.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.printful.foodie.model.Food
import com.printful.foodie.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodDetailViewModel(application: Application): BaseViewModel(application) {

    val foodLiveData = MutableLiveData<Food>()

    //get data from local database and controlling thread with coroutineScope
    fun getRoomData(uuid: Int){
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food
        }
    }

}