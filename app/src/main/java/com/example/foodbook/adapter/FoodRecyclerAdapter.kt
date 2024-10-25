package com.example.foodbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.foodbook.databinding.RecyclerRowBinding
import com.example.foodbook.model.Food
import com.example.foodbook.util.downloadImage
import com.example.foodbook.util.placeHolder
import com.example.foodbook.view.FoodListFragmentDirections

class FoodRecyclerAdapter(var foodList : ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() {
    class FoodViewHolder(var binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun updateFoodList(newFoodList:List<Food>)
    {
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.foodNameRow.text = foodList[position].foodName
        holder.binding.foodCalorieRow.text = foodList[position].foodCalorie

        holder.itemView.setOnClickListener {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
        holder.binding.imageViewRow.downloadImage(foodList[position].foodImage, placeHolder(holder.itemView.context))

    }
}