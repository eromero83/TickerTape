package com.ericromero.tickertreat.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * API service for Gemini's public APIs
 */
interface GeminiService {
    companion object {
        const val API_VERSION = "v1"
    }

    @GET("$API_VERSION/symbols")
    fun getSymbols(): Call<ResponseBody>

    @GET("$API_VERSION/symbols/details/{symbol}")
    fun getSymbolDetails(@Path("symbol") symbol: String): Call<ResponseBody>

    @GET("$API_VERSION/pubticker/{symbol}")
    fun getTicker(@Path("symbol") symbol: String): Call<ResponseBody>
}