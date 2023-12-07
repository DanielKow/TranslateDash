package me.daniel.translatedash.api

import me.daniel.translatedash.data.DictionaryEntry
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DictionaryApi {
    @Headers(
        "Accept: application/json",
        "X-Secret: 7aa2a045359269f9cf5120714da28a4b7d467048d384fc288cb92f40cd25fbb6"
    )
    @GET("v1/dictionary")
    suspend fun getTranslation(
        @Query("q") word: String,
        @Query("l") dictionary: String,
        @Query("in") sourceLanguage: String
    ): List<DictionaryEntry>
}