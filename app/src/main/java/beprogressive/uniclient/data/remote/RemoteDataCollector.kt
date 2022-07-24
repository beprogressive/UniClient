package beprogressive.uniclient.data.remote

import beprogressive.common.model.UserItem
import beprogressive.common.model.UserItemDao
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

    override suspend fun auth(type: UserItem.ApiKey, response: String) {
        when (type) {
            UserItem.ApiKey.GitHub -> gitHubDataSource.auth(response)
            UserItem.ApiKey.DailyMotion -> dailyMotionDataSource.auth(response)
        }
    }
}