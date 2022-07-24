package beprogressive.uniclient.data.remote

import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.ClientUser
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun collectUsers(): Flow<UserItem>

    fun auth(challengeResponse: String): String?

    fun getClientUser(token: String): ClientUser?
}