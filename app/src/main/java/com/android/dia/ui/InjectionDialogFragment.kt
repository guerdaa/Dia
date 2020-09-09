package com.android.dia.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.dia.R
import com.android.dia.utils.Constants
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_injection_dialog.view.*
import javax.inject.Inject

class InjectionDialogFragment(private val units: String, private val correction: String) : DaggerDialogFragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    private var placeOfInjection = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_injection_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        placeOfInjection = sharedPreferences.getInt(Constants.INJECTION_REF, 0)
        view.units_text_view.text = units
        view.correction_text_view.text = correction
        view.injection_image_view.setImageResource(retrievePlaceOfInjection(placeOfInjection))
        view.submit_btn.setOnClickListener {
            submit()
        }
        view.next_btn.setOnClickListener {
            setNext()
        }
        view.reload_btn.setOnClickListener {
            reload()
        }
    }

    private fun retrievePlaceOfInjection(placeOfInject: Int): Int {
        when (placeOfInject) {
            Constants.ABD_LEFT_DOWN -> return R.drawable.abs_left_down
            Constants.ABD_LEFT_UP -> return R.drawable.abs_left_up
            Constants.ABD_LEFT_MIDDLE -> return R.drawable.abs_left_middle
            Constants.ABD_RIGHT_UP -> return R.drawable.abs_right_up
            Constants.ABD_RIGHT_DOWN -> return R.drawable.abs_right_down
            Constants.ABD_RIGHT_MIDDLE -> return R.drawable.abs_right_middle

            Constants.LEFT_ARM_CORNER_DOWN -> return R.drawable.ic_left_corner_down
            Constants.LEFT_ARM_CORNER_UP -> return R.drawable.ic_left_corner_up
            Constants.LEFT_ARM_MIDDLE_DOWN -> return R.drawable.ic_left_middle_down
            Constants.LEFT_ARM_MIDDLE_UP -> return R.drawable.ic_left_middle_up

            Constants.RIGHT_ARM_CORNER_DOWN -> return R.drawable.ic_right_corner_down
            Constants.RIGHT_ARM_CORNER_UP -> return R.drawable.ic_right_corner_up
            Constants.RIGHT_ARM_MIDDLE_DOWN -> return R.drawable.ic_right_middle_down
            else -> return R.drawable.ic_right_middle_up
        }
    }

    private fun reload() {
        when(placeOfInjection) {
            in Constants.FIRST_ABD_PLACE..Constants.LAST_ABD_PLACE -> {
                placeOfInjection = Constants.FIRST_ARM_PLACE
                view!!.injection_image_view.setImageResource(retrievePlaceOfInjection(placeOfInjection))
            }

            in Constants.FIRST_ARM_PLACE..Constants.LAST_ARM_PLACE -> {
                placeOfInjection = Constants.FIRST_ABD_PLACE
                view!!.injection_image_view.setImageResource(retrievePlaceOfInjection(placeOfInjection))
            }
        }
    }

    private fun getNext() {
        if(placeOfInjection < Constants.NUMBER_OF_PLACES)
            placeOfInjection++
        else
            placeOfInjection = 0
    }
    private fun setNext() {
        getNext()
        view!!.injection_image_view.setImageResource(retrievePlaceOfInjection(placeOfInjection))
    }


    private fun submit() {
        getNext()
        sharedPreferencesEditor.putInt(Constants.INJECTION_REF, placeOfInjection).commit()
        dismiss()
    }
    companion object {
        fun newInstance(units: String, correction: String): InjectionDialogFragment {
            return InjectionDialogFragment(units, correction)
        }
    }
}
