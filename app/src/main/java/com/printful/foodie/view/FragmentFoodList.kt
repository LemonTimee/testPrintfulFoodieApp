package com.printful.foodie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.printful.foodie.R
import com.printful.foodie.adapter.AdapterFood
import com.printful.foodie.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FragmentFoodList : Fragment() {

    private lateinit var viewModel: FoodListViewModel
    private val adapterFood = AdapterFood(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize viewModel and get data from internet
        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.getDataFromInternet()

        //handling adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapterFood

        //swipe the screen and refresh the data
        swipeRefreshLayout.setOnRefreshListener {
            progressBar.visibility = View.GONE
            textViewFoodListError.visibility = View.GONE
            recyclerView.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false

            viewModel.getDataFromInternet()
        }

        observeLiveData()
    }

    //observing live data
    private fun observeLiveData() {
        viewModel.foods.observe(viewLifecycleOwner, {
            it?.let {
                recyclerView.visibility = View.VISIBLE
                adapterFood.foodListRefresher(it)
            }
        })

        viewModel.foodError.observe(viewLifecycleOwner, {
            it?.let {
                if (it){
                    textViewFoodListError.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                else{
                    textViewFoodListError.visibility = View.GONE
                }
            }
        })

        viewModel.foodLoading.observe(viewLifecycleOwner, {
            it?.let {
                if (it){
                    recyclerView.visibility = View.GONE
                    textViewFoodListError.visibility = View.GONE
                    progressBar.visibility = View.GONE
                }
                else{
                    progressBar.visibility = View.GONE
                }
            }
        })
    }

}