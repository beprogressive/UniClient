package beprogressive.uniclient.data

import android.net.Uri
import beprogressive.common.model.UserItem
import beprogressive.uniclient.data.local.LocalDataSource
import beprogressive.uniclient.data.remote.DataCollector

class MainRepositoryImpl(
    private val authRepository: AuthRepository,
    private val remoteDataCollector: DataCollector,
    private val localDataSource: LocalDataSource
) : MainRepository {
    override suspend fun getClientUser(apiKey: UserItem.ApiKey, token: String) {
        remoteDataCollector.getClientUser(apiKey, token)
    }

    override suspend fun getSavedClientUser() = localDataSource.getSavedClientUser()

    override suspend fun auth(response: Uri) {
        authRepository.auth(response)
    }

    override suspend fun logOut() {
        localDataSource.clearSavedClientUser()
    }
}