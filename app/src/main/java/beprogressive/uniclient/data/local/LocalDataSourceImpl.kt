package beprogressive.uniclient.data.local

import androidx.datastore.core.DataStore
import androidx.lifecycle.LiveData
import beprogressive.common.model.UserItem
import beprogressive.common.model.UserItemDao
import beprogressive.common.model.toApiKey
import beprogressive.uniclient.data.ClientUser
import com.gmail.beprogressive.it.uniclient.ProtoSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class LocalDataSourceImpl internal constructor(
    private val settings: DataStore<ProtoSettings>,
    private val usersDao: UserItemDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {

    override fun observeUsers(): LiveData<List<UserItem>> {
        return usersDao.observeUsers()
    }

    override fun observeUser(userId: String): LiveData<UserItem?> {
        return usersDao.observeUserById(userId)
    }

    override suspend fun switchUserFavorite(userItem: UserItem) {
        usersDao.switchUserItemFavorite(userItem)
    }

    //    private val saved get() = settings.data.take(1)

    override suspend fun saveAccessToken(accessToken: String) {
        settings.updateData {
            it.toBuilder().setAccessToken(accessToken).build()
        }
    }

    override suspend fun saveClientUser(clientUser: ClientUser) {
        settings.updateData {
            it.toBuilder()
                .setAccessToken(clientUser.accessToken)
                .setUserName(clientUser.userName)
                .setApiType(clientUser.apiType.name)
                .build()
        }
    }

    override suspend fun getSavedAccessToken(): Flow<String> = settings.data.transform { flowCollector ->
        emit(flowCollector.accessToken)
    }

    override suspend fun getSavedClientUser(): Flow<ClientUser> = settings.data.transform { flowCollector ->
        val clientUser = ClientUser.setUser(flowCollector.userName, flowCollector.accessToken, flowCollector.apiType.toApiKey())
        emit(clientUser)
    }
}