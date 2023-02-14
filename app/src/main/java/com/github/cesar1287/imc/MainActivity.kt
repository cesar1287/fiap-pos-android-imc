package com.github.cesar1287.imc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.cesar1287.imc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btCalcular.setOnClickListener {
            calculate()
        }
    }

    private fun calculate() {
        val weight = binding.etPeso.text.toString().toDouble()
        val height = binding.etAltura.text.toString().toDouble()
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
            tvIMC.text = "Seu IMC é: ${imc}"

            ivIMCStatus.setImageDrawable(
                ContextCompat.getDrawable(this@MainActivity, drawableId)
            )

            tvIMCStatus.text = getString(stringId)
        }
    }

}