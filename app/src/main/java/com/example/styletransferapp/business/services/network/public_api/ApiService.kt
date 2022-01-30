package com.example.styletransferapp.business.services.network.public_api

import com.example.styletransferapp.business.services.network.public_api.responses.Cat

import retrofit2.http.GET

interface ApiService {
    @GET("/cat?html=true")
    fun getRandomCatAsJson(): Cat
}