package com.example.challengechapter5binar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapter5binar.model.GetAllGhibliFilmsResponseItem
import com.example.challengechapter5binar.networking.ApiGhibliFilmClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelGhibliFilm : ViewModel() {
    private var liveDataGhibliFilm : MutableLiveData<List<GetAllGhibliFilmsResponseItem>> = MutableLiveData()
    fun getLiveGhibliFilmObserver() : MutableLiveData<List<GetAllGhibliFilmsResponseItem>>{
        return liveDataGhibliFilm
    }
    fun makeApiGhibliFilm(){
        ApiGhibliFilmClient.instance.getAllFilms()
            .enqueue(object : Callback<List<GetAllGhibliFilmsResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetAllGhibliFilmsResponseItem>>,
                    response: Response<List<GetAllGhibliFilmsResponseItem>>
                ) {
                    if(response.isSuccessful){
                        liveDataGhibliFilm.postValue(response.body())
                    }else{
                        liveDataGhibliFilm.postValue(null)
                    }
                }

                override fun onFailure(
                    call: Call<List<GetAllGhibliFilmsResponseItem>>,
                    t: Throwable
                ) {
                    liveDataGhibliFilm.postValue(null)
                }

            })
    }
}