package beprogressive.uniclient.data

import android.net.Uri
import beprogressive.common.model.UserItem
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getClientUser(apiKey: UserItem.ApiKey, token: String)
    suspend fun getSavedClientUser(): Flow<ClientUser?>
    suspend fun auth(response: Uri)
    suspend fun logOut()
}