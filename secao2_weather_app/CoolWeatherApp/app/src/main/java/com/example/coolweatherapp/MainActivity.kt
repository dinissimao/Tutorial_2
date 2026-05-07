package com.example.coolweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    // Variável que controla se é dia ou noite
    private var dia = true

    // Cliente de localização GPS
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // ViewModel — criado e gerido pelo sistema Android
    // Sobrevive a rotações do ecrã
    private val viewModel: WeatherViewModel by viewModels()

    // Launcher para pedir permissão de localização
    private val pedirPermissaoLocalizacao = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissoes ->
        val concedida = permissoes[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissoes[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (concedida) {
            obterLocalizacaoECarregar()
        } else {
            Toast.makeText(
                this,
                "Permissão negada. A usar Lisboa por defeito.",
                Toast.LENGTH_LONG
            ).show()
            viewModel.carregarDadosMeteo(38.076f, -9.12f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        // Aplicar tema antes do super.onCreate()
        aplicarTema()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Aplicar fundo ao container
        aplicarFundo()

        // Inicializar cliente GPS
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Observar o LiveData do ViewModel
        observarViewModel()

        // Obter localização GPS ou usar Lisboa por defeito
        if (temPermissaoLocalizacao()) {
            obterLocalizacaoECarregar()
        } else {
            pedirPermissaoLocalizacao.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        // Botão UPDATE — pede ao ViewModel para carregar novos dados
        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val lat = findViewById<EditText>(R.id.editLatitude)
                .text.toString().toFloatOrNull() ?: 38.076f
            val lon = findViewById<EditText>(R.id.editLongitude)
                .text.toString().toFloatOrNull() ?: -9.12f

            viewModel.carregarDadosMeteo(lat, lon)
        }
    }

    // ----------------------------------------------------------------
    // Observa os LiveData do ViewModel e actualiza a UI em conformidade.
    // A View não sabe como os dados são obtidos — só reage às mudanças.
    // ----------------------------------------------------------------
    private fun observarViewModel() {

        // Observar dados meteorológicos
        viewModel.dadosMeteo.observe(this) { dados ->
            actualizarUI(dados)
        }

        // Observar estado de carregamento
        viewModel.aCarregar.observe(this) { aCarregar ->
            // Mostrar ou esconder ProgressBar conforme o estado
            val progressBar = findViewById<View>(R.id.progressBar)
            progressBar.visibility = if (aCarregar) View.VISIBLE else View.GONE
        }

        // Observar erros
        viewModel.erro.observe(this) { erro ->
            erro?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ----------------------------------------------------------------
    // Verifica se a permissão de localização já foi concedida
    // ----------------------------------------------------------------
    private fun temPermissaoLocalizacao(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    // ----------------------------------------------------------------
    // Obtém a última localização conhecida e pede dados ao ViewModel
    // ----------------------------------------------------------------
    @SuppressLint("MissingPermission")
    private fun obterLocalizacaoECarregar() {
        fusedLocationClient.lastLocation.addOnSuccessListener { localizacao ->
            if (localizacao != null) {
                val lat = localizacao.latitude.toFloat()
                val lon = localizacao.longitude.toFloat()

                // Mostrar coordenadas GPS nos campos
                findViewById<EditText>(R.id.editLatitude).setText(lat.toString())
                findViewById<EditText>(R.id.editLongitude).setText(lon.toString())

                // Pedir dados ao ViewModel (não à API directamente)
                viewModel.carregarDadosMeteo(lat, lon)
            } else {
                Toast.makeText(this, "GPS sem sinal. A usar Lisboa.", Toast.LENGTH_SHORT).show()
                viewModel.carregarDadosMeteo(38.076f, -9.12f)
            }
        }
    }

    // ----------------------------------------------------------------
    // Aplica o tema correcto conforme orientação e hora do dia
    // ----------------------------------------------------------------
    private fun aplicarTema() {
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                if (dia) setTheme(R.style.Theme_Day_Land)
                else     setTheme(R.style.Theme_Night_Land)
            }
            else -> {
                if (dia) setTheme(R.style.Theme_Day)
                else     setTheme(R.style.Theme_Night)
            }
        }
    }

    // ----------------------------------------------------------------
    // Aplica o fundo directamente ao ConstraintLayout principal
    // ----------------------------------------------------------------
    private fun aplicarFundo() {
        val container = findViewById<ConstraintLayout>(R.id.container)
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                if (dia) container.setBackgroundResource(R.drawable.sunny_bg_land)
                else     container.setBackgroundResource(R.drawable.night_bg_land)
            }
            else -> {
                if (dia) container.setBackgroundResource(R.drawable.sunny_bg)
                else     container.setBackgroundResource(R.drawable.night_bg)
            }
        }
    }

    // ----------------------------------------------------------------
    // Actualiza a interface com os dados recebidos do ViewModel
    // ----------------------------------------------------------------
    private fun actualizarUI(dados: WeatherData) {
        val weatherImage   = findViewById<ImageView>(R.id.weatherImage)
        val pressureValue  = findViewById<TextView>(R.id.pressureValue)
        val windDirValue   = findViewById<TextView>(R.id.windDirectionValue)
        val windSpeedValue = findViewById<TextView>(R.id.windSpeedValue)
        val tempValue      = findViewById<TextView>(R.id.temperatureValue)
        val timeValue      = findViewById<TextView>(R.id.timeValue)

        pressureValue.text  = dados.hourly.pressure_msl.getOrNull(12)?.toString() + " hPa"
        windDirValue.text   = dados.current_weather.winddirection.toString() + "°"
        windSpeedValue.text = dados.current_weather.windspeed.toString() + " km/h"
        tempValue.text      = dados.current_weather.temperature.toString() + " °C"
        timeValue.text      = dados.current_weather.time

        val mapaMeteo  = getWeatherCodeMap()
        val entradaWMO = mapaMeteo[dados.current_weather.weathercode]

        val nomeImagem = when (entradaWMO) {
            WMO_WeatherCode.CLEAR_SKY,
            WMO_WeatherCode.MAINLY_CLEAR,
            WMO_WeatherCode.PARTLY_CLOUDY ->
                if (dia) entradaWMO.image + "day"
                else     entradaWMO.image + "night"
            else -> entradaWMO?.image
        }

        val resId = resources.getIdentifier(nomeImagem, "drawable", packageName)
        if (resId != 0) {
            weatherImage.setImageDrawable(getDrawable(resId))
        } else {
            weatherImage.setImageResource(R.drawable.fog)
        }
    }
}




