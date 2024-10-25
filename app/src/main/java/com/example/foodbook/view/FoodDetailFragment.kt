package com.example.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.foodbook.databinding.FragmentFoodDetailBinding
import com.example.foodbook.util.downloadImage
import com.example.foodbook.util.placeHolder
import com.example.foodbook.viewmodel.FoodDetailViewModel

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FoodDetailViewModel
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodDetailViewModel::class.java]

        arguments?.let{
            id = FoodDetailFragmentArgs.fromBundle(it).foodId
            viewModel.getFoodDetail(id)
        }

        observeLiveData()

    }

    fun observeLiveData()
    {
        viewModel.foodLiveData.observe(viewLifecycleOwner){
            binding.foodName.text = it.foodName
            binding.foodCalorie.text = it.foodCalorie
            binding.foodCarb.text = it.foodCarb
            binding.foodOil.text = it.foodOil
            binding.foodProtein.text = it.foodProtein
            binding.imageView.downloadImage(it.foodImage, placeHolder(requireContext()))
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}