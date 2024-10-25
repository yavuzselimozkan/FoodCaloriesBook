package com.example.foodbook.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodbook.model.Food
import com.example.foodbook.roomdb.FoodDatabase
import com.example.foodbook.service.FoodAPIService
import com.example.foodbook.util.SpecialSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodListViewModel(application: Application) : AndroidViewModel(application) {

    val foods = MutableLiveData<List<Food>>()
    val foodLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<Boolean>()

    private val specialSharedPreferences = SpecialSharedPreferences(getApplication())
    private val foodApiService = FoodAPIService()
    private val updateTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refreshData()
    {
        val savedTime = specialSharedPreferences.getTime()

        if(savedTime != null && savedTime != 0L && System.nanoTime() - savedTime < updateTime)
        {
            //roomadan veri alınacak
            getDataFromRoom()
        }
        else
        {
            getDataFromInternet()
        }
    }

    private fun getDataFromRoom()
    {
        foodLoading.value = true

        viewModelScope.launch(Dispatchers.IO){
            val dao = FoodDatabase(getApplication()).foodDao()
            val foodList = dao.getAll()
            withContext(Dispatchers.Main){
                showFoods(foodList)
                Toast.makeText(getApplication(),"Besinleri room'dan aldık!",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDataFromInternet()
    {
        foodLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val foodList = foodApiService.getData()
            withContext(Dispatchers.Main)
            {
                foodLoading.value = false
                //room'a kaydedeceğiz
                saveToRoom(foodList)
                Toast.makeText(getApplication(),"Besinleri internetten aldık!",Toast.LENGTH_LONG).show()
            }
        }
    }
    fun refreshDataFromInternet()
    {
        return getDataFromInternet()
    }

    private fun showFoods(foodList : List<Food>)
    {
        foods.value = foodList
        foodLoading.value = false
        errorMessage.value = false
    }

    private fun saveToRoom(foodList : List<Food>)
    {
        viewModelScope.launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAll()
            val idList = dao.insertAll(*foodList.toTypedArray())
            var i = 0
            while(i < foodList.size)
            {
                foodList[i].id = idList[i].toInt()
                i = i + 1
            }
            showFoods(foodList)
        }

        specialSharedPreferences.saveTime(System.nanoTime())
    }
}