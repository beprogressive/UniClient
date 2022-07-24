package beprogressive.uniclient.data.remote.github.retrofit

import beprogressive.uniclient.data.remote.github.pojo.GitHubAuthResponse
import beprogressive.uniclient.data.remote.github.pojo.GitHubUserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

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
}