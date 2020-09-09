package com.android.dia.ui.add_measure

import android.annotation.SuppressLint
import android.text.format.Time
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dia.model.Measure
import com.android.dia.model.Settings
import com.android.dia.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddMeasureViewModel @Inject constructor(val firebaseFirestore: FirebaseFirestore, val settings: Settings): ViewModel() {

    private val _uploaded = MutableLiveData<Boolean>()
    val uploaded: LiveData<Boolean> get()= _uploaded

    init {
        _uploaded.value = false
    }

    fun submit(measure: Measure) {
        firebaseFirestore.collection(Constants.MEASURE_PATH).document(measure.id).set(measure).addOnSuccessListener {
            _uploaded.value = true
            _uploaded.value = false
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCorrespondApidraFactor(): Int {
        val time = SimpleDateFormat("HH").format(Calendar.getInstance().time).toInt()
        if(time in 7..11) {
            return settings.morningFactor
        } else if(time in 12..19) {
            return settings.afternoonFactor
        } else {
            return settings.nightFactor
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun getCorrespondCorrecturFactor(): Int {
        val time = SimpleDateFormat("HH").format(Calendar.getInstance().time).toInt()
        if(time in 21..23) {
            return Constants.CORRECT_FACTOR_NIGHT
        }
        return Constants.CORRECT_FACTOR_MORNING
    }

    fun getUnits(khe: Int): String {
        val factor = getCorrespondApidraFactor()
        return (khe / factor).toString()
    }

    fun getCorrection(measure: Int): String {
        val correction = getCorrespondCorrecturFactor()
        if(measure > 130 || measure <= 70)
            return ((measure - Constants.STABIL_MEASURE) / correction).toString()
        return  "0"
    }
}
