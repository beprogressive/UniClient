package beprogressive.uniclient.data

import android.net.Uri
import androidx.lifecycle.LiveData
import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.local.LocalDataSource
import beprogressive.uniclient.data.remote.DataCollector
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepositoryImpl(
    private val remoteDataSource: DataCollector,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UsersRepository {

    override suspend fun collectUsers() {
        remoteDataSource.collectUsers()
    }

    override fun observeUsers(): LiveData<List<UserItem>> {
        return localDataSource.observeUsers()
    }

    override fun observeUser(userId: String): LiveData<UserItem?> {
        return localDataSource.observeUser(userId)
    }

    override suspend fun auth(response: Uri) {
        withContext(ioDispatcher) {

            val apiKey = if (response.path?.contains("github") == true)
                UserItem.ApiKey.GitHub
            else
                UserItem.ApiKey.DailyMotion

            remoteDataSource.auth(apiKey, response.query.toString())
        }
    }

    override suspend fun switchUserFavorite(userItem: UserItem) {
        withContext(ioDispatcher) {
            localDataSource.switchUserFavorite(userItem)
        }
    }
}