package com.android.dia.views

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class PoppinsBoldTextView : TextView {
    constructor(context: Context) : super(context) {
        val face = Typeface.createFromAsset(context.assets, "Poppins-Bold.ttf")
        this.typeface = face
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val face = Typeface.createFromAsset(context.assets, "Poppins-Bold.ttf")
        this.typeface = face
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        val face = Typeface.createFromAsset(context.assets, "Poppins-Bold.ttf")
        this.typeface = face
    }

    fun getString(): String {
        return text.toString().trim().toUpperCase().replace("\n", " ")
    }
}