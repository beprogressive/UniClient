package beprogressive.uniclient.data

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun auth(response: Uri)

    suspend fun getSavedClientUser(): Flow<ClientUser>
}