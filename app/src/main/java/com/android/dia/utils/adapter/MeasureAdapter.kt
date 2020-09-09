package com.android.dia.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.dia.R
import com.android.dia.model.Measure
import com.android.dia.model.TypeOfDay
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.measure_item_layout.view.*
import kotlinx.android.synthetic.main.measure_item_layout.view.delete_btn
import kotlinx.android.synthetic.main.measure_item_layout.view.khe_text_view

abstract class MeasureAdapter(val measures: MutableList<Measure>) : RecyclerView.Adapter<MeasureAdapter.MeasureViewHolder>() {

    var clickedItemPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.measure_item_layout, parent, false)
        return MeasureViewHolder(view)
    }

    override fun getItemCount(): Int {
        return measures.size
    }

    override fun onBindViewHolder(holder: MeasureViewHolder, position: Int) {
        holder.setMeasureDetail(measures[position])
        holder.itemView.delete_btn.setOnClickListener {
            clickedItemPosition = position
            deleteMeasure()
        }
    }

    abstract fun deleteMeasure()

    class MeasureViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun setMeasureDetail(measure: Measure) {
            itemView.measure_text_view.text = measure.measure.toString()
            itemView.khe_text_view.text = measure.khe.toString()
            itemView.date_text_view.text = measure.date
            itemView.time_text_view.text = measure.time
            itemView.unit_text_view.text = measure.units.toString()
            itemView.correction_text_view.text = measure.correction.toString()
            itemView.comment_text_view.text = measure.comment
            setTypeOfDay(measure.typeOfDay)
        }

        private fun setTypeOfDay(typeOfDay: TypeOfDay) {
            when(typeOfDay) {
                TypeOfDay.WORK -> itemView.type_of_day_image_view.setImageResource(R.drawable.work)
                TypeOfDay.SPORT -> itemView.type_of_day_image_view.setImageResource(R.drawable.sport)
                TypeOfDay.RELAX -> itemView.type_of_day_image_view.setImageResource(R.drawable.relax)
            }
        }
    }
}