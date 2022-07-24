package beprogressive.uniclient.data

import android.net.Uri
import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.local.LocalDataSource
import beprogressive.uniclient.data.remote.DataCollector
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val remoteDataCollector: DataCollector,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): AuthRepository {

    override suspend fun auth(response: Uri) {
        withContext(ioDispatcher) {

            val apiKey = if (response.path?.contains("github") == true)
                UserItem.ApiKey.GitHub
            else
                UserItem.ApiKey.DailyMotion

            remoteDataCollector.auth(apiKey, response.query.toString())?.let {
                localDataSource.saveClientUser(it)
            }
        }
    }

    override suspend fun getSavedAccessToken(): Flow<String> {
        return localDataSource.getSavedAccessToken()
    }
}