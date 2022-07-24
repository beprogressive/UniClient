package beprogressive.uniclient.data.remote.github.retrofit

import beprogressive.uniclient.data.remote.github.pojo.GitHubAuthResponse
import beprogressive.uniclient.data.remote.github.pojo.GitHubClientUser
import beprogressive.uniclient.data.remote.github.pojo.GitHubClientUserResponse
import beprogressive.uniclient.data.remote.github.pojo.GitHubUserItem
import retrofit2.Call
import retrofit2.http.*

interface GHRetrofitServices {

    @Headers("Accept: application/json")
    @POST("access_token")
    fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Query("redirect_uri") redirectUri: String,
    ): Call<GitHubAuthResponse>

    @GET("users")
    fun getUserList(): Call<List<GitHubUserItem>>

    @Headers("Accept: application/json")
    @GET("user")
    fun getClientUser(@Header("Authorization") token: String): Call<GitHubClientUser>
}