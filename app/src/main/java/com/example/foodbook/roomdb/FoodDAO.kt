package com.example.foodbook.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodbook.model.Food

@Dao
interface FoodDAO {

    @Insert
    suspend fun insertAll(vararg food : Food) : List<Long> //eklediği besinlerin id'sini long olarak geri veriyor

    @Query("SELECT * FROM Food")
    suspend fun getAll() : List<Food>

    @Query("SELECT * FROM Food WHERE id = :foodId")
    suspend fun getById(foodId : Int) : Food

    @Query("DELETE FROM Food")
    suspend fun deleteAll()
}