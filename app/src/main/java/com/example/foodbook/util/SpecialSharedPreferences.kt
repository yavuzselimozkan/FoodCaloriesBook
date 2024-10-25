package com.example.foodbook.util

import android.content.Context
import android.content.SharedPreferences
import kotlin.concurrent.Volatile

class SpecialSharedPreferences{

    companion object{
        private const val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile
        private var instance : SpecialSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context : Context) = instance?:  synchronized(lock)
        {
            instance?: createSpecialSharedPreferences(context).also {
                instance = it
            }
        }

        private fun createSpecialSharedPreferences(context : Context) : SpecialSharedPreferences
        {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }

    fun saveTime(time: Long)
    {
        sharedPreferences?.edit()?.putLong(TIME,time)?.apply()
    }

    fun getTime() = sharedPreferences?.getLong(TIME,0)
}