package com.printful.foodie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.printful.foodie.R
import com.printful.foodie.databinding.FragmentFoodDetailBinding
import com.printful.foodie.viewmodel.FoodDetailViewModel
import kotlinx.android.synthetic.main.fragment_food_detail.*

class FragmentFoodDetail : Fragment() {

    private lateinit var viewModel: FoodDetailViewModel
    private var foodId = 0
    private lateinit var dataBinding: FragmentFoodDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_food_detail,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //checking arguments
        arguments?.let {
            foodId = FragmentFoodDetailArgs.fromBundle(it).foodId
        }

        //initialize viewModel and get data from local database
        viewModel = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.getRoomData(foodId)

        observeLiveData()

        imageButtonDetailBack.setOnClickListener {
            val action = FragmentFoodDetailDirections.actionFragmentFoodDetailToFragmentFoodList()
            Navigation.findNavController(view).navigate(action)
        }
    }

    //observing live data
    private fun observeLiveData() {
        viewModel.foodLiveData.observe(viewLifecycleOwner,{
            it?.let {
                dataBinding.selectedFood = it
            }
        })
    }

}