package com.android.dia.ui.foods

import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.android.dia.R
import com.android.dia.utils.adapter.FoodAdapter
import com.android.dia.viewmodel_factories.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.foods_fragment.*
import javax.inject.Inject

class FoodsFragment : DaggerFragment() {

    companion object {
        fun newInstance() = FoodsFragment()
    }

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: FoodsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.foods_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, providerFactory).get(FoodsViewModel::class.java)
        viewModel.retrieveFoods()
        viewModel.retrieved.observe(viewLifecycleOwner, Observer {retrieved ->
            if(retrieved) {
                food_recycler_view.adapter = object : FoodAdapter(viewModel.foodsList) {
                    override fun deleteMeasure() {
                        viewModel.deleteFood(viewModel.foodsList[clickedItemPosition].id)
                        viewModel.foodsList.removeAt(clickedItemPosition)
                        food_recycler_view.adapter?.notifyDataSetChanged()
                    }

                }
                food_recycler_view.layoutManager = LinearLayoutManager(context)
                refresh_layout.isRefreshing = false
            }
        })
        viewModel.filter.observe(viewLifecycleOwner, Observer {
            search_edit_text.text.clear()
            filter()
        })
        filterSelected()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh_layout.setOnRefreshListener {
            viewModel.retrieveFoods()
            search_edit_text.text.clear()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun filterSelected() {
        position_text_view.setOnClickListener {
            position_btn.background = context?.getDrawable(R.drawable.selected_type)
            name_btn.setBackgroundColor(context?.getColor(R.color.white)!!)
            viewModel.positionSelected()
        }

        food_name_text_view.setOnClickListener {
            name_btn.background = context?.getDrawable(R.drawable.selected_type)
            position_btn.setBackgroundColor(context?.getColor(R.color.white)!!)
            viewModel.nameSelected()
        }
    }

    private fun filter() {
        search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filter(s.toString())
                food_recycler_view.adapter = object : FoodAdapter(viewModel.filterList) {
                    override fun deleteMeasure() {
                        viewModel.deleteFood(viewModel.filterList[clickedItemPosition].id)
                        viewModel.filterList.removeAt(clickedItemPosition)
                        food_recycler_view.adapter?.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
