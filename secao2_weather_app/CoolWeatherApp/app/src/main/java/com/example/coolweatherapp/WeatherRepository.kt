package com.example.coolweatherapp

import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class WeatherRepository {

    // ----------------------------------------------------------------
    // Faz a chamada HTTP à API Open-Meteo e devolve um WeatherData.
    // Este metodo é bloqueante — tem de ser chamado num thread de fundo.
    // ----------------------------------------------------------------
    fun obterDadosMeteorologicos(lat: Float, lon: Float): WeatherData {
        val urlString = buildString {
            append("https://api.open-meteo.com/v1/forecast?")
            append("latitude=${lat}&longitude=${lon}&")
            append("current_weather=true&")
            append("hourly=temperature_2m,weathercode,pressure_msl,windspeed_10m")
        }

        val url = URL(urlString)
        url.openStream().use { stream ->
            return Gson().fromJson(
                InputStreamReader(stream, "UTF-8"),
                WeatherData::class.java
            )
        }
    }
}