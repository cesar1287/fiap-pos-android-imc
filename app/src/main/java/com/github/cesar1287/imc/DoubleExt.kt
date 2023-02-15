package com.github.cesar1287.imc

fun Double.formatImcResult(digits: Int) = String.format("%.${digits}f", this)