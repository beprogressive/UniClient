package beprogressive.uniclient.data.local

import androidx.datastore.core.DataStore
import beprogressive.common.model.toApiKey
import beprogressive.uniclient.data.ClientUser
import com.gmail.beprogressive.it.uniclient.ProtoSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class DataStorageImpl(private val settings: DataStore<ProtoSettings>) : DataStorage {
    override val savedClientUser: Flow<ClientUser> = settings.data.transform { flowCollector ->
        val clientUser = ClientUser.setUser(
            flowCollector.userName,
            flowCollector.accessToken,
            flowCollector.userImage,
            flowCollector.apiType.toApiKey()
        )
        emit(clientUser)
    }

    override suspend fun saveAccessToken(token: String) {
        settings.updateData {
            it.toBuilder().setAccessToken(token).build()
        }
    }

    override suspend fun saveClientUser(clientUser: ClientUser) {
        settings.updateData {
            it.toBuilder()
                .setAccessToken(clientUser.accessToken)
                .setUserName(clientUser.userName)
                .setUserImage(clientUser.userImage)
                .setApiType(clientUser.apiType.name)
                .build()
        }
    }

    override suspend fun clearClientUser() {
        settings.updateData {
            it.toBuilder()
                .clearUserName()
                .clearAccessToken()
                .clearUserImage()
                .clearApiType()
                .build()
        }
    }
}