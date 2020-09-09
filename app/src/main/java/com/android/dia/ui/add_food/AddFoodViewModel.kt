package com.android.dia.ui.add_food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dia.model.Food
import com.android.dia.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class AddFoodViewModel @Inject constructor(val firestore: FirebaseFirestore) : ViewModel() {

    private val _uploaded = MutableLiveData<Boolean>()
    val uploaded: LiveData<Boolean> get() = _uploaded

    private val _failed = MutableLiveData<Boolean>()
    val failed: LiveData<Boolean> get() = _failed

    init {
        _failed.value = false
        _uploaded.value = false
    }

    fun uploadFood(food: Food) {

        if(food.name.isEmpty() || food.position.isEmpty() || food.khe.isEmpty()) {
            _failed.value = true
            _failed.value = false
            return
        }
        if(food.comment.isEmpty()) {
            food.comment = "Comment not available"
        }
        food.id = System.currentTimeMillis().toString()
        firestore.collection(Constants.FOOD_PATH).document(food.id).set(food).addOnSuccessListener {
            _uploaded.value = true
            _uploaded.value = false
        }
    }
}
