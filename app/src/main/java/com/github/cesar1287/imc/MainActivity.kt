package com.github.cesar1287.imc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.github.cesar1287.imc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btCalcular.setOnClickListener {
                calculate()
            }

            etPeso.addTextChangedListener(DecimalTextWatcher(etPeso, 1))

            etAltura.addTextChangedListener(DecimalTextWatcher(etAltura, 2))
        }
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