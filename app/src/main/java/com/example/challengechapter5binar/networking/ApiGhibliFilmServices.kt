package com.example.challengechapter5binar.networking

import com.example.challengechapter5binar.model.GetAllGhibliFilmsResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiGhibliFilmServices {
    @GET("films")
    fun getAllFilms() : Call<List<GetAllGhibliFilmsResponseItem>>
}