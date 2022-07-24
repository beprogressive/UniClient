package beprogressive.uniclient.data.remote.dailymotion

import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.ClientUser
import beprogressive.uniclient.data.remote.RemoteDataSource
import beprogressive.uniclient.data.remote.dailymotion.pojo.DailyMotionUsersResponse
import beprogressive.uniclient.data.remote.dailymotion.retrofit.DMRetrofitServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DailyMotionDataSource : RemoteDataSource {

    private const val BASE_URL = "https://api.dailymotion.com/"

    private val retrofitService: DMRetrofitServices = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(DMRetrofitServices::class.java)

    override suspend fun collectUsers(): Flow<UserItem> = callbackFlow {

        val userListCall = retrofitService.getUserList()

        val callback = object : retrofit2.Callback<DailyMotionUsersResponse> {
            override fun onResponse(
                call: Call<DailyMotionUsersResponse>,
                response: retrofit2.Response<DailyMotionUsersResponse>
            ) {
                val data = response.body() ?: return
                val users = data.list
                for (i in users.indices) {
                    val user = users[i]
                    val userItem =
                        UserItem(user.id, user.screenname, "", UserItem.ApiKey.DailyMotion)

                    trySendBlocking(userItem)
                }
            }

            override fun onFailure(call: Call<DailyMotionUsersResponse>, t: Throwable) {
                t.printStackTrace()
            }
        }

        userListCall.enqueue(callback)

        awaitClose { userListCall.cancel() }
    }

    override fun auth(challengeResponse: String): String? {
        return null
    }

    override fun getClientUser(token: String): ClientUser? {
       return null
    }


}