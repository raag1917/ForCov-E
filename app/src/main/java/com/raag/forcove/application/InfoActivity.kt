package com.raag.forcove.application


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.raag.forcove.R
import com.raag.forcove.databinding.ActivityInfoBinding
import com.shuhart.stepview.StepView

class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding
    private val pasos = arrayListOf("Reportes de salud a un clic", "Muestra el código QR", "Tus datos seguros", "Noticias")
    private val descripcion = arrayListOf("Reporta tu estado de salud antes de ir a clases presenciales",
        "Tu reporte generará un código QR que se guardará en tu celular, muéstralo a nuestro personal al ingreso a la sede para garantizar que realizaste el reporte correspondiente y que tu estado de salud es optimo para ingresar a las clases presenciales.",
        "Los datos que suministras para el reporte diario de síntomas estarán seguros y solo serán utilizados para hacer seguimiento epidemiológico en caso necesario.",
        "Te mantendremos actualizado con las noticias recientes sobre el avance y manejo de la pandemia y la aplicación de los protocolos de bioseguridad en Grupo Formarte.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clic.setAnimation(R.raw.arrow)
        binding.imgIntro.playAnimation()
        binding.imgIntro.repeatCount = 10
        binding.imgIntro.loop(false)

        binding.imgIntro.setAnimation(R.raw.logo)
        binding.imgIntro.playAnimation()

        binding.stepView.state
            .stepsNumber(4)
            .animationType(StepView.ANIMATION_ALL)
            .animationDuration(resources.getInteger(android.R.integer.config_shortAnimTime))
            .commit()

        binding.stepView.setOnStepClickListener { position ->

            binding.stepView.go(position, true)
            when(position){
                0 -> {
                    binding.imgIntro.setAnimation(R.raw.report)
                    binding.imgIntro.playAnimation()
                    binding.clic.visibility = View.GONE
                    binding.imgIntro.repeatCount = 5
                    binding.tvTitulo.text = pasos[position]
                    binding.tvDescripcion.text = descripcion[position]
                }

                1 -> {
                    binding.imgIntro.setAnimation(R.raw.codigo)
                    binding.imgIntro.playAnimation()
                    binding.imgIntro.repeatCount = 5
                    binding.clic.visibility = View.GONE
                    binding.tvTitulo.text = pasos[position]
                    binding.tvDescripcion.text = descripcion[position]
                }

                2 -> {
                    binding.imgIntro.setAnimation(R.raw.datos)
                    binding.imgIntro.playAnimation()
                    binding.imgIntro.repeatCount = 5
                    binding.clic.visibility = View.GONE
                    binding.tvTitulo.text = pasos[position]
                    binding.tvDescripcion.text = descripcion[position]
                }

                3 -> {
                    binding.imgIntro.setAnimation(R.raw.news)
                    binding.imgIntro.playAnimation()
                    binding.clic.visibility = View.GONE
                    binding.imgIntro.repeatCount = 5
                    binding.tvTitulo.text = pasos[position]
                    binding.tvDescripcion.text = descripcion[position]
                }
            }
        }
    }
}