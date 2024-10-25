package com.example.foodbook.service

import com.example.foodbook.model.Food
import retrofit2.http.GET

interface FoodAPI {

    //BASEURL -> https://raw.githubusercontent.com/
    //ENDPOINT -> atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    suspend fun getFoodList() : List<Food>
}