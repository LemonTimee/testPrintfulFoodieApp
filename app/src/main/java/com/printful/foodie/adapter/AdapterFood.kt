package com.printful.foodie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.printful.foodie.R
import com.printful.foodie.databinding.FoodRecyclerRowBinding
import com.printful.foodie.model.Food
import com.printful.foodie.view.FragmentFoodListDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*


class AdapterFood(val foodList: ArrayList<Food>): RecyclerView.Adapter<AdapterFood.FoodViewHolder>(),FoodClickListener {

    class FoodViewHolder(var view: FoodRecyclerRowBinding): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FoodRecyclerRowBinding>(inflater,R.layout.food_recycler_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.view.food = foodList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    //clear list and add all food to the list. And notify adapter.
    fun foodListRefresher(newFoodList: List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    //go to whichever food was clicked
    override fun foodClick(view: View) {
        val uuid = view.textViewRowBesinUUID.text.toString().toIntOrNull()

        uuid?.let {
            val action = FragmentFoodListDirections.actionFragmentFoodListToFragmentFoodDetail(it)
            Navigation.findNavController(view).navigate(action)
        }
    }
}