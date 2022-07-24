package beprogressive.uniclient.data.remote

import beprogressive.common.model.UserItem
import beprogressive.common.model.UserItemDao
import beprogressive.uniclient.data.ClientUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RemoteDataCollector(
    private val gitHubDataSource: RemoteDataSource,
    private val dailyMotionDataSource: RemoteDataSource,
    private val usersDao: UserItemDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataCollector {

    override suspend fun collectUsers() {
        withContext(ioDispatcher) {

            launch {
                gitHubDataSource.collectUsers().collectLatest {
                    usersDao.upsertItem(it)
                }
            }

            launch {
                dailyMotionDataSource.collectUsers().collectLatest {
                    usersDao.upsertItem(it)
                }
            }
        }
    }

    override suspend fun auth(type: UserItem.ApiKey, response: String): ClientUser? {
        return when (type) {
            UserItem.ApiKey.GitHub -> {
                gitHubDataSource.auth(response).let {
                    if (it != null)
                        return getClientUser(type, it)
                    else
                        null
                }
            }

            UserItem.ApiKey.DailyMotion -> return null
        }
    }

    override fun getClientUser(type: UserItem.ApiKey, token: String): ClientUser? {
        return when (type) {
            UserItem.ApiKey.GitHub -> gitHubDataSource.getClientUser(token)
            UserItem.ApiKey.DailyMotion -> dailyMotionDataSource.getClientUser(token)
        }
    }
}