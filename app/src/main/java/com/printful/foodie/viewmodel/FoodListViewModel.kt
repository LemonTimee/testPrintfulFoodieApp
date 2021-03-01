package com.printful.foodie.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.printful.foodie.model.Food
import com.printful.foodie.service.FoodAPIService
import com.printful.foodie.service.FoodDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application): BaseViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val foodLoading = MutableLiveData<Boolean>()
    val foodError = MutableLiveData<Boolean>()
    private val foodAPIService = FoodAPIService()
    private val disposable = CompositeDisposable() //disposable for memory management

    //get data from the internet
    fun getDataFromInternet(){

        foodLoading.value = true

        disposable.add(
            foodAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        saveInSqlite(t)
                        //Toast.makeText(getApplication(),"Food coming from internet",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        foodError.value = true
                        foodLoading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    //write data to local database and controling thread with coroutineScope
    private fun saveInSqlite(foodList: List<Food>){

        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList = dao.insertAll(*foodList.toTypedArray())

            var i = 0
            while (i < foodList.size){
                foodList[i].uuid = uuidList[i].toInt()
                i += 1
            }
            showFood(foodList)
        }
    }

    //handling live data
    private fun showFood(foodList: List<Food>){
        foods.value = foodList
        foodError.value = false
        foodLoading.value = false
    }


}