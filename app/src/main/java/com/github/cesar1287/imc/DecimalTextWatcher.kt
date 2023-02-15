package com.github.cesar1287.imc

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow

class DecimalTextWatcher(
    editText: EditText,
    private val totalDecimalNumber: Int = 2
) : TextWatcher {

    private val editTextWeakReference: WeakReference<EditText> = WeakReference(editText)

    init {
        formatDigits(editTextWeakReference.get()?.text)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable) {
        formatDigits(editable)
    }

    private fun getTotalDecimalNumber(): String {
        val decimalNumber = StringBuilder()
        for (i in 1..totalDecimalNumber) {
            decimalNumber.append("0")
        }
        return decimalNumber.toString()
    }

    private fun formatDigits(editable: Editable?) {
        val editText = editTextWeakReference.get() ?: return
        val cleanString = editable.toString().trim().replace(" ", "")
        editText.removeTextChangedListener(this)

        val number = 10.toDouble().pow(totalDecimalNumber.toDouble())
        val parsed = when (cleanString) {
            "" -> BigDecimal(0)
            "null" -> BigDecimal(0)
            else -> BigDecimal(cleanString.replace("\\D+".toRegex(), ""))
                .setScale(totalDecimalNumber, RoundingMode.FLOOR)
                .divide(BigDecimal(number.toInt()),
                    RoundingMode.FLOOR)
        }
        val dfnd = DecimalFormat("#,##0.${getTotalDecimalNumber()}")
        val formatted = dfnd.format(parsed)
        editText.setText(formatted.replace(',', '.'))
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }
}