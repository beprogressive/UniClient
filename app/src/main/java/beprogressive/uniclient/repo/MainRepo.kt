package beprogressive.uniclient.repo

import kotlinx.coroutines.flow.Flow

interface MainRepo {
    suspend fun saveAccessToken(accessToken: String)
    suspend fun getSavedAccessToken(): Flow<String>
}