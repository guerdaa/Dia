package com.android.dia.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dia.R
import com.android.dia.model.Food
import kotlinx.android.synthetic.main.food_item_layout.view.*

abstract class FoodAdapter(val foodsList: MutableList<Food>): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    var clickedItemPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_item_layout, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.setFoodDetail(foodsList[position])
        holder.itemView.delete_btn.setOnClickListener {
            clickedItemPosition = position
            deleteMeasure()
        }
    }

    abstract fun deleteMeasure()

    class FoodViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun setFoodDetail(food: Food) {
            view.food_name_text_view.text = food.name
            view.position_text_view.text = food.position
            view.comment_text_view.text = food.comment
            view.khe_text_view.text = food.khe
        }


    }
}