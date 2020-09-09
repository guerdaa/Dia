package com.android.dia.utils.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.android.dia.R
import com.android.dia.ui.add_food.AddFoodFragment
import com.android.dia.ui.add_measure.AddMeasureFragment
import com.android.dia.ui.foods.FoodsFragment
import com.android.dia.ui.measures.MeasuresFragment
import com.android.dia.ui.settings.SettingsFragment
import com.android.dia.utils.Constants


class ViewPagerAdapter (private val view: View, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = arrayListOf(SettingsFragment.newInstance(), MeasuresFragment.newInstance(), AddMeasureFragment.newInstance(), FoodsFragment.newInstance(), AddFoodFragment.newInstance())

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return fragments[0]
            }
            1 -> {
                return fragments[1]
            }
            2 -> {
                return fragments[2]
            }
            3 -> {
                return fragments[3]
            }
            else -> {
                return fragments[4]
            }
        }
    }

    override fun getCount(): Int {
        return Constants.FRAGMENTS_COUNT
    }

}