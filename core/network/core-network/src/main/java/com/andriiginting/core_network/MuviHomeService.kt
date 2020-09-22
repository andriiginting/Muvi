package com.andriiginting.core_network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET
import retrofit2.http.Query

interface MuviHomeService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Single<MovieResponse>
}

@Parcelize
data class MovieResponse(
    @SerializedName("results") var resultsIntent: List<MovieItem> = mutableListOf()
) : BaseResponse(), Parcelable

@Parcelize
data class MovieItem(
    @SerializedName("id") var id: String = "",
    @SerializedName("movie_id") var movieId: String = "",
    @SerializedName("original_title") var title: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    @SerializedName("overview") var overview: String = "",
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("release_date") var releaseDate: String = ""
) : Parcelable