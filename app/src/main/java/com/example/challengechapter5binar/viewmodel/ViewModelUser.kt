package com.example.challengechapter5binar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengechapter5binar.model.GetAllUserResponseItem
import com.example.challengechapter5binar.networking.ApiUserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelUser : ViewModel() {
    var liveDataUser : MutableLiveData<List<GetAllUserResponseItem>> = MutableLiveData()
    fun getLiveUserObserver() : MutableLiveData<List<GetAllUserResponseItem>>{
        return liveDataUser
    }
    fun setLiveDataUserFromApi(){
        ApiUserClient.instance.getAllUsers()
            .enqueue(object : Callback<List<GetAllUserResponseItem>> {
                override fun onResponse(
                    call: Call<List<GetAllUserResponseItem>>,
                    response: Response<List<GetAllUserResponseItem>>
                ) {
                    if(response.isSuccessful){
                        liveDataUser.postValue(response.body())
                    }else{
                        liveDataUser.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                    liveDataUser.postValue(null)
                }

            })
    }
}