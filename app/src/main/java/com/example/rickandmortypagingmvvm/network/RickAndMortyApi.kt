package com.example.rickandmortypagingmvvm.network

import com.example.rickandmortypagingmvvm.data.RickAndMorty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("api/character")
    suspend fun getCharacters(
        @Query("page") page: Int

    ): Response<RickAndMorty>

    @GET("api/character")
    suspend fun getSearchCharacters(
        @Query("page") page: Int,
        @Query("name")  characterName: String

    ): Response<RickAndMorty>



}