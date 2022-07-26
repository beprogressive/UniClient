package beprogressive.uniclient.data.remote.github

import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.ClientUser
import beprogressive.uniclient.data.remote.RemoteDataSource
import beprogressive.uniclient.data.remote.github.pojo.GitHubUserItem
import beprogressive.uniclient.data.remote.github.retrofit.GHRetrofitServices
import beprogressive.uniclient.log
import com.google.gson.GsonBuilder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GitHubDataSource : RemoteDataSource {

    /**
     * A native method that is implemented by the 'uniclient' native library,
     * which is packaged with this application.
     */
    private external fun getClientId(): String
    private external fun getClientSecret(): String

    private const val REDIRECT_URI = "app://com.gmail.beprogressive.it.uniclient/github"
    private const val BASE_URL = "https://api.github.com/"
    private const val AUTH_URL = "https://github.com/login/oauth/"
    const val SCOPES = ""

    fun getAuthUrl(): String {
        return AUTH_URL + "authorize?client_id=${getClientId()}&redirect_uri=$REDIRECT_URI"
    }

    private val retrofitService: GHRetrofitServices = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(GHRetrofitServices::class.java)

    override suspend fun collectUsers(): Flow<UserItem> = callbackFlow {

        val userListCall = retrofitService.getUserList()

        val callback = object : retrofit2.Callback<List<GitHubUserItem>> {
            override fun onResponse(
                call: Call<List<GitHubUserItem>>,
                response: retrofit2.Response<List<GitHubUserItem>>
            ) {
                val users = response.body() ?: return
                for (i in users.indices) {
                    val user = users[i]
                    val userItem =
                        UserItem(user.nodeId, user.login, user.avatarUrl, UserItem.ApiKey.GitHub)

                    trySendBlocking(userItem)
                }
            }

            override fun onFailure(call: Call<List<GitHubUserItem>>, t: Throwable) {
                t.printStackTrace()
            }
        }

        userListCall.enqueue(callback)

        awaitClose { userListCall.cancel() }
    }

    override fun auth(challengeResponse: String): String? {

        val code = challengeResponse.replace("code=", "")

        log("auth code: $code")

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val authService: GHRetrofitServices = Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(GHRetrofitServices::class.java)

        val authCall = authService.getAccessToken(
            clientId = getClientId(),
            clientSecret = getClientSecret(),
            code = code,
            redirectUri = REDIRECT_URI
        )

        return authCall.execute().body()?.accessToken
    }

    override fun getClientUser(token: String): ClientUser? {
        val response = retrofitService.getClientUser("token $token").execute()
        response.body()?.let {
            log("onResponse user.avatarUrl: ${it.avatarUrl}")
            return ClientUser.setUser(it.login, token, it.avatarUrl, UserItem.ApiKey.GitHub)
        }
        return null
    }
}