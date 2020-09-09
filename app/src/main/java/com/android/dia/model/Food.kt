package com.android.dia.model

import com.android.dia.utils.Constants

class Food constructor(var id: String, var name: String, var url: String, var position: String, var comment: String, var khe: String){
    constructor(): this("", "", "", "", Constants.COMMENT_NOT_AVAILABLE, "")
}