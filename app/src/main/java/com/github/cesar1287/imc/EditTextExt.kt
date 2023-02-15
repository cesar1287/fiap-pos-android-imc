package com.github.cesar1287.imc

import android.widget.EditText

fun EditText.valueDouble(): Double {
    return this.text.toString().toDouble()
}

fun EditText.value(): String {
    return this.text.toString()
}