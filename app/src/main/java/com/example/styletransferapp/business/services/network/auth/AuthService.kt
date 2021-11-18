package com.example.styletransferapp.business.services.network.auth
import com.example.styletransferapp.business.services.network.GenericResponse
import com.example.styletransferapp.business.services.network.auth.responses.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path


interface AuthService {

    @POST("/register/{username}")
    fun registerUser(
        @Path("username") username: String,
        @Body user: User
    ): Call<GenericResponse<Any?>>

    @POST("/login/{username}")
    fun loginUser(
        @Path("username") username: String,
        @Body user: User,
    ): Call<GenericResponse<User>>

    @POST("/logout/{username}")
    fun logoutUser(
        @Path("username") username: String
    ): Call<GenericResponse<Any?>>
}

