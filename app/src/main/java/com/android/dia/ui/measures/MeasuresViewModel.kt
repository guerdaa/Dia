package com.android.dia.ui.measures

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dia.model.Measure
import com.android.dia.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class MeasuresViewModel @Inject constructor(val firebaseFirestore: FirebaseFirestore): ViewModel() {

    val measures = mutableListOf<Measure>()
    private val _retrieved = MutableLiveData<Boolean>()
    val retrieved: LiveData<Boolean> get() = _retrieved

    private val _deleted = MutableLiveData<Boolean>()
    val deleted: LiveData<Boolean> get() = _deleted

    init {
        _retrieved.value = false
        _deleted.value = false
    }

    fun retrieveMeasure() {
        measures.clear()
        _retrieved.value = false
        firebaseFirestore.collection(Constants.MEASURE_PATH).get().addOnSuccessListener {querySnapshot ->
            querySnapshot.documents.forEach {doc ->
                val measure = doc.toObject(Measure::class.java)!!
                measures.add(measure)
            }
        }.addOnCompleteListener {
            measures.sortByDescending {
                it.id
            }
            _retrieved.value = true
        }
    }

    fun deleteMeasure(measureId: String) {
        firebaseFirestore.collection(Constants.MEASURE_PATH).document(measureId).delete().addOnCompleteListener {
            _deleted.value = true
            _deleted.value = false
        }
    }
}
