package com.android.dia

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.android.dia.model.Settings
import com.android.dia.utils.adapter.ViewPagerAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject

class BaseActivity : DaggerAppCompatActivity(), ViewPager.OnPageChangeListener {



    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        view_pager.adapter = viewPagerAdapter
        view_pager.addOnPageChangeListener(this)
        view_pager.currentItem = 2
        implemetIconClickListeners()
    }


    private fun implemetIconClickListeners() {
        ic_add_food.setOnClickListener {
            view_pager.currentItem = 4
        }
        ic_search_food.setOnClickListener {
            view_pager.currentItem = 3
        }
        ic_add_measure.setOnClickListener {
            view_pager.currentItem = 2
        }
        ic_measures.setOnClickListener {
            view_pager.currentItem = 1
        }
        ic_settings.setOnClickListener {
            view_pager.currentItem = 0
        }
    }
    private fun setSelectedIcon(childView: ImageView, fragmentTag: String) {
        ic_add_food.setBackgroundColor(resources.getColor(R.color.white))
        ic_add_food.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
        ic_add_measure.setBackgroundColor(resources.getColor(R.color.white))
        ic_add_measure.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
        ic_measures.setBackgroundColor(resources.getColor(R.color.white))
        ic_measures.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
        ic_search_food.setBackgroundColor(resources.getColor(R.color.white))
        ic_search_food.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
        ic_settings.setBackgroundColor(resources.getColor(R.color.white))
        ic_settings.setColorFilter(resources.getColor(R.color.colorPrimaryDark))
        childView.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        childView.setColorFilter(resources.getColor(R.color.white))
        fragment_tag.text = fragmentTag
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when(position) {
            0 -> setSelectedIcon(ic_settings, "Settings")
            1 -> setSelectedIcon(ic_measures, "Measures")
            2 -> setSelectedIcon(ic_add_measure, "Add Value")
            3 -> setSelectedIcon(ic_search_food, "Foods")
            4 -> setSelectedIcon(ic_add_food, "Add Food")
        }
    }
}
