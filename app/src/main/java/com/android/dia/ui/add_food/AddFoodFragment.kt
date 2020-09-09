package com.android.dia.ui.add_food

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.android.dia.R
import com.android.dia.model.Food
import com.android.dia.model.Settings
import com.android.dia.viewmodel_factories.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.add_food_fragment.*
import javax.inject.Inject

class AddFoodFragment : DaggerFragment() {

    @Inject
    lateinit var settings: Settings

    companion object {
        fun newInstance() = AddFoodFragment()
    }

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var viewModel: AddFoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_food_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, providerFactory).get(AddFoodViewModel::class.java)
        viewModel.uploaded.observe(viewLifecycleOwner, Observer { uploaded ->
            if(uploaded) {
                Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.failed.observe(viewLifecycleOwner, Observer { failed ->
            if(failed) {
                Toast.makeText(context, "Check your input", Toast.LENGTH_SHORT).show()
            }
        })
        submit_btn.setOnClickListener {
            submit()
        }
    }
    private fun submit() {
        val food = Food()
        food.position = position_edit_text.getString()
        food.name = food_name_edit_text.getString()
        food.comment = comment_edit_text.getString()
        food.khe = carbohydrates_edit_text.getString()
         //url must be filled
        viewModel.uploadFood(food)
    }


}
