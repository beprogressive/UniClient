package beprogressive.uniclient.data.local

import beprogressive.uniclient.data.ClientUser
import kotlinx.coroutines.flow.Flow

interface DataStorage {
    val savedClientUser: Flow<ClientUser>

    suspend fun saveAccessToken(token: String)

    suspend fun saveClientUser(clientUser: ClientUser)
}