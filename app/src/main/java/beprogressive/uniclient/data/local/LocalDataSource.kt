package beprogressive.uniclient.data.local

import androidx.lifecycle.LiveData
import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.ClientUser
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun observeUsers(): LiveData<List<UserItem>>

    fun observeUser(userId: String): LiveData<UserItem?>

    suspend fun switchUserFavorite(userItem: UserItem)

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveClientUser(clientUser: ClientUser)

    suspend fun getSavedClientUser(): Flow<ClientUser>

    suspend fun clearSavedClientUser()
}