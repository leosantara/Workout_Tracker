package id.ac.ukdw.workout_tracker

import id.ac.ukdw.workout_tracker.model.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("lat") lat : String,
        @Query("lon") lon : String,
        @Query("appid") apiKey : String,
    ):Response<CurrentWeather>


}