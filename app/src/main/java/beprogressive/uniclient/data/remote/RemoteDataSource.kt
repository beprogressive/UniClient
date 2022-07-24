package beprogressive.uniclient.data.remote

import beprogressive.common.model.UserItem
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun collectUsers(): Flow<UserItem>

    suspend fun auth(challengeResponse: String)
}