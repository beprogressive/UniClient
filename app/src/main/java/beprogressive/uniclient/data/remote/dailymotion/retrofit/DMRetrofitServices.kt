package beprogressive.uniclient.data.remote.dailymotion.retrofit

import beprogressive.uniclient.data.remote.dailymotion.pojo.DailyMotionUsersResponse
import retrofit2.Call
import retrofit2.http.GET

interface DMRetrofitServices {

    @GET("users")
    fun getUserList(): Call<DailyMotionUsersResponse>
}