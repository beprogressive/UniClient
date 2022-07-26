package beprogressive.uniclient.data.local

import androidx.lifecycle.LiveData
import beprogressive.common.model.UserItem
import beprogressive.common.model.UserItemDao
import beprogressive.uniclient.data.ClientUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LocalDataSourceImpl internal constructor(
    private val dataStorage: DataStorage,
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
        dataStorage.saveAccessToken(accessToken)
    }

    override suspend fun saveClientUser(clientUser: ClientUser) {
        dataStorage.saveClientUser(clientUser)
    }

    override suspend fun getSavedClientUser() = dataStorage.savedClientUser

    override suspend fun clearSavedClientUser() {
        dataStorage.clearClientUser()
    }
}