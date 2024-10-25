package com.example.foodbook.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodbook.model.Food
import com.example.foodbook.roomdb.FoodDatabase
import com.example.foodbook.service.FoodAPIService
import com.example.foodbook.util.SpecialSharedPreferences
import kotlinx.coroutines.launch

class FoodDetailViewModel(application : Application) : AndroidViewModel(application) {

    var foodLiveData = MutableLiveData<Food>()

    fun getFoodDetail(id : Int)
    {
        viewModelScope.launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            foodLiveData.value = dao.getById(id)
        }
    }

}