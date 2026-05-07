package com.example.coolweatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel : ViewModel() {

    // Repositório — responsável por aceder à API
    private val repositorio = WeatherRepository()

    // ----------------------------------------------------------------
    // LiveData privado (mutável) — só o ViewModel pode alterar
    // LiveData público (só leitura) — a View apenas observa
    // Este padrão evita que a View altere o estado directamente
    // ----------------------------------------------------------------

    // Dados meteorológicos
    private val _dadosMeteo = MutableLiveData<WeatherData>()
    val dadosMeteo: LiveData<WeatherData> = _dadosMeteo

    // Estado de carregamento (true = a carregar, false = pronto)
    private val _aCarregar = MutableLiveData<Boolean>()
    val aCarregar: LiveData<Boolean> = _aCarregar

    // Mensagens de erro
    private val _erro = MutableLiveData<String?>()
    val erro: LiveData<String?> = _erro

    // ----------------------------------------------------------------
    // Metodo chamado pela View para pedir dados meteorológicos.
    // Usa viewModelScope + Dispatchers.IO para executar em background.
    // viewModelScope cancela automaticamente a coroutine se o ViewModel
    // for destruído — evita memory leaks.
    // ----------------------------------------------------------------
    fun carregarDadosMeteo(lat: Float, lon: Float) {
        _aCarregar.value = true
        _erro.value = null

        viewModelScope.launch {
            try {
                // Dispatchers.IO executa em thread de rede (não bloqueia o UI)
                val dados = withContext(Dispatchers.IO) {
                    repositorio.obterDadosMeteorologicos(lat, lon)
                }
                // De volta ao UI thread — actualizar o LiveData
                _dadosMeteo.value = dados
            } catch (e: Exception) {
                _erro.value = "Erro ao obter dados: ${e.message}"
            } finally {
                _aCarregar.value = false
            }
        }
    }
}