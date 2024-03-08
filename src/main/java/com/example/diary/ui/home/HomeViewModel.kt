package com.example.diary.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.diary.data.Item
import com.example.diary.data.ItemsRepository
import com.example.diary.network.ApiObject
import com.example.diary.network.WEATHER
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

//ViewModel for HomeScreen
@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(
    itemsRepository: ItemsRepository
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        itemsRepository.getAllItemsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState(0, 0, 0))
        private set

    init {
        getWeatherInformation()
    }

    private fun getWeatherInformation() {
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val formatted = current.format(formatter)

        val base_time: Int = 1100
        val base_date = formatted.toInt()
        val nx = "60"
        val ny = "127"

        //Weather information
        val call = ApiObject.retrofitService.getWeather(
            10, 1, "JSON", base_date, base_time, nx, ny
        )
        call.enqueue(object : retrofit2.Callback<WEATHER>{
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                if (response.isSuccessful){
                    val weatherList = response.body()!!.response.body.items.item
                    for (item in weatherList) {
                        if (item.category == "TMP") {
                            weatherUiState.temp = item.fcstValue.toInt()
                        } else if (item.category == "PTY") {
                            weatherUiState.rain = item.fcstValue.toInt()
                        } else if (item.category == "SKY") {
                            weatherUiState.sky = item.fcstValue.toInt()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                weatherUiState.rain = -1
            }
        })
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val itemList: List<Item> = listOf()
)

data class WeatherUiState(
    var temp: Int = 0,
    var rain: Int = 0,
    var sky: Int = 0
)




