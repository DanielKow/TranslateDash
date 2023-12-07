package me.daniel.translatedash.api
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RandomWordsApi {

    @Headers(
        "Accept: application/json"
    )
    @GET("word")
    suspend fun getWords(@Query("number") numberOfWords: Int) : List<String>
}