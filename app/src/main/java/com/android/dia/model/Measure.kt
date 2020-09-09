package com.android.dia.model

import com.android.dia.utils.Constants

class Measure constructor(var id: String, var measure: Int, var khe: Int, var units: Int, var correction: Int, var date: String, var time: String, var comment: String, var typeOfDay: TypeOfDay){
    constructor(): this("", 0, 0, 0, 0, "", "", Constants.COMMENT_NOT_AVAILABLE, TypeOfDay.RELAX)
}