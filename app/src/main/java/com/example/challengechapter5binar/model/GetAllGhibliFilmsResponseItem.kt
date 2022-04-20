package com.example.challengechapter5binar.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAllGhibliFilmsResponseItem(
    @SerializedName("description")
    val description: String,
    @SerializedName("director")
    val director: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("locations")
    val locations: List<String>,
    @SerializedName("movie_banner")
    val movieBanner: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("original_title_romanised")
    val originalTitleRomanised: String,
    @SerializedName("people")
    val people: List<String>,
    @SerializedName("producer")
    val producer: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("rt_score")
    val rtScore: String,
    @SerializedName("running_time")
    val runningTime: String,
    @SerializedName("species")
    val species: List<String>,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("vehicles")
    val vehicles: List<String>
) : Parcelable