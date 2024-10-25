package com.example.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodbook.adapter.FoodRecyclerAdapter
import com.example.foodbook.databinding.FragmentFoodListBinding
import com.example.foodbook.viewmodel.FoodListViewModel



class FoodListFragment : Fragment() {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FoodListViewModel
    private var adapter = FoodRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodListViewModel::class.java]
        viewModel.refreshData()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility = View.GONE
            binding.errorTextView.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.refreshDataFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData()
    {
        viewModel.foods.observe(viewLifecycleOwner){
            adapter.updateFoodList(it)
            binding.recyclerView.visibility = View.VISIBLE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner)
        {
            if(it)
            {
                binding.errorTextView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
            else
            {
                binding.errorTextView.visibility = View.GONE
            }
        }

        viewModel.foodLoading.observe(viewLifecycleOwner){
            if(it)
            {
                binding.progressBar.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.errorTextView.visibility = View.GONE
            }
            else
            {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}