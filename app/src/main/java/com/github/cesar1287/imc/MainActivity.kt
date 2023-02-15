package com.github.cesar1287.imc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.github.cesar1287.imc.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.text.DecimalFormat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private val totalDecimalNumberWeight: Int = 1
    private val totalDecimalNumberHeight: Int = 2

    private var textWatcherWeight: TextWatcher? = null
    private var textWatcherHeight: TextWatcher? = null

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btCalcular.setOnClickListener {
            calculate()
        }

        textWatcherWeight = binding.etPeso.doAfterTextChanged {
            formatDigitsWeight(it)
        }

        textWatcherHeight = binding.etAltura.doAfterTextChanged {
            formatDigitsHeight(it)
        }
    }

    private fun getTotalDecimalNumberWeight(): String {
        val decimalNumber = StringBuilder()
        for (i in 1..totalDecimalNumberWeight) {
            decimalNumber.append("0")
        }
        return decimalNumber.toString()
    }

    private fun getTotalDecimalNumberHeight(): String {
        val decimalNumber = StringBuilder()
        for (i in 1..totalDecimalNumberHeight) {
            decimalNumber.append("0")
        }
        return decimalNumber.toString()
    }


    private fun formatDigitsWeight(
        editable: Editable?
    ) {
        binding.etPeso.removeTextChangedListener(textWatcherWeight)
        val cleanString = editable.toString().trim().replace(" ", "")
        val number = 10.toDouble().pow(totalDecimalNumberWeight.toDouble())
        val parsed = when (cleanString) {
            "" -> BigDecimal(0)
            "null" -> BigDecimal(0)
            else -> BigDecimal(cleanString.replace("\\D+".toRegex(), ""))
                .setScale(totalDecimalNumberWeight, BigDecimal.ROUND_FLOOR)
                .divide(BigDecimal(number.toInt()),
                    BigDecimal.ROUND_FLOOR)
        }
        val dfnd = DecimalFormat("#,##0.${getTotalDecimalNumberWeight()}")
        val formatted = dfnd.format(parsed)
        binding.etPeso.setText(formatted.replace(',', '.'))
        binding.etPeso.setSelection(formatted.length)
        binding.etPeso.addTextChangedListener(textWatcherWeight)
    }

    private fun formatDigitsHeight(
        editable: Editable?
    ) {
        binding.etAltura.removeTextChangedListener(textWatcherHeight)
        val cleanString = editable.toString().trim().replace(" ", "")
        val number = 10.toDouble().pow(totalDecimalNumberHeight.toDouble())
        val parsed = when (cleanString) {
            "" -> BigDecimal(0)
            "null" -> BigDecimal(0)
            else -> BigDecimal(cleanString.replace("\\D+".toRegex(), ""))
                .setScale(totalDecimalNumberHeight, BigDecimal.ROUND_FLOOR)
                .divide(BigDecimal(number.toInt()),
                    BigDecimal.ROUND_FLOOR)
        }
        val dfnd = DecimalFormat("#,##0.${getTotalDecimalNumberHeight()}")
        val formatted = dfnd.format(parsed)
        binding.etAltura.setText(formatted.replace(',', '.'))
        binding.etAltura.setSelection(formatted.length)
        binding.etAltura.addTextChangedListener(textWatcherHeight)
    }


    private fun calculate() {
        val weight = binding.etPeso.valueDouble()
        val height = binding.etAltura.valueDouble()
        val imc = weight / (height * height)
        when {
            imc < 18.5 -> 
                imcConfig(imc, R.drawable.masc_abaixo, R.string.magreza)
            imc >= 18.5 && imc < 24.9 -> 
                imcConfig(imc, R.drawable.masc_ideal, R.string.peso_normal)
            imc >= 24.9 && imc < 30 ->  
                imcConfig(imc, R.drawable.masc_sobre, R.string.sobre_peso)
            imc >= 30 && imc < 35 -> 
                imcConfig(imc, R.drawable.masc_obeso, R.string.obesidade_grau_i)
            imc >= 35 && imc < 40 ->
                imcConfig(imc, R.drawable.masc_extremo_obeso, R.string.obesidade_grau_ii)
            imc >= 40 ->
                imcConfig(imc, R.drawable.masc_extremo_obeso, R.string.obesidade_grau_iii)
        }
    }

    private fun imcConfig(imc: Double, drawableId: Int, stringId: Int) {
        with(binding) {
            tvIMC.text = getString(R.string.imc_result, imc.formatImcResult(2))

            ivIMCStatus.isVisible = true
            ivIMCStatus.setImageDrawable(
                ContextCompat.getDrawable(this@MainActivity, drawableId)
            )

            tvIMCStatus.text = getString(stringId)
        }
    }

}