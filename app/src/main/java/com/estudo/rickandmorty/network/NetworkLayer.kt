package com.estudo.rickandmorty.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.estudo.rickandmorty.SimpleMortyApplication
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkLayer {

    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val retrofit: Retrofit = Retrofit.Builder()
        .client(getLoggingHttpClient())
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val rickAndMortyServices: RickAndMortyServices by lazy {
        retrofit.create(RickAndMortyServices::class.java)
    }

    val apiClient = ApiClient(rickAndMortyServices)

    private fun getLoggingHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        builder.addInterceptor(ChuckerInterceptor.Builder(SimpleMortyApplication.context)
            .collector(ChuckerCollector(SimpleMortyApplication.context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
        )

        return builder.build()
    }
}