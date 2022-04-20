package com.example.challengechapter5binar.networking

import com.example.challengechapter5binar.model.GetAllUserResponseItem
import com.example.challengechapter5binar.model.PostNewUser
import com.example.challengechapter5binar.model.RequestUser
import retrofit2.Call
import retrofit2.http.*

interface ApiUserServices {
    @GET("datauserlogin")
    fun getAllUsers() : Call<List<GetAllUserResponseItem>>
    @POST("datauserlogin")
    fun postDataUser(@Body reqUser : RequestUser) : Call<PostNewUser>
    @PUT("datauserlogin/{id}")
    fun updateUser(
        @Path("id") id : String,
        @Body request : RequestUser
    ) : Call<List<GetAllUserResponseItem>>
}