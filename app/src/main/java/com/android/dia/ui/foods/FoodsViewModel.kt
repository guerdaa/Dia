package com.android.dia.ui.foods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dia.model.Food
import com.android.dia.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FoodsViewModel @Inject constructor(val firestore: FirebaseFirestore): ViewModel() {

    private val _retrieved = MutableLiveData<Boolean>()
    val retrieved: LiveData<Boolean> get() = _retrieved

    private val _filter = MutableLiveData<String>()
    val filter: LiveData<String> get() = _filter

    private val _deleted = MutableLiveData<Boolean>()
    val deleted: LiveData<Boolean> get() = _deleted

    val foodsList = mutableListOf<Food>()

    val filterList = mutableListOf<Food>()


    init {
        _deleted.value = false
        _retrieved.value = false
        _filter.value = Constants.POSITION_FILTER
    }

    fun retrieveFoods() {
        foodsList.clear()
        firestore.collection(Constants.FOOD_PATH).get().addOnSuccessListener {querySnapshot ->
            querySnapshot.documents.forEach {doc ->
                val food = doc.toObject(Food::class.java)!!
                foodsList.add(food)
            }
        }.addOnCompleteListener {
            _retrieved.value = true
        }
    }

    fun positionSelected() {
        _filter.value = Constants.POSITION_FILTER
    }

    fun nameSelected() {
        _filter.value = Constants.NAME_FILTER
    }

    fun filter(sequence: String) {
        filterList.clear()
        when(_filter.value) {
            Constants.NAME_FILTER -> {
                foodsList.forEach{food ->
                    if(food.name.contains(sequence, true))
                        filterList.add(food)
                }
            }
            Constants.POSITION_FILTER -> {
                foodsList.forEach { food ->
                    if(food.position.contains(sequence, true))
                        filterList.add(food)
                }
            }
        }
    }

    fun deleteFood(foodId: String) {
        firestore.collection(Constants.FOOD_PATH).document(foodId).delete().addOnSuccessListener {
            _deleted.value = true
            _deleted.value = false
        }
    }

}
