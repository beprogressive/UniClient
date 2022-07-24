package beprogressive.uniclient.data.local

import androidx.lifecycle.LiveData
import beprogressive.common.model.UserItem
import beprogressive.common.model.UserItemDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LocalDataSourceImpl internal constructor(
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
}